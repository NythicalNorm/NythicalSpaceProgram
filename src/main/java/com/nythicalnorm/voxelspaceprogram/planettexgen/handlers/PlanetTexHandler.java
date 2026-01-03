package com.nythicalnorm.voxelspaceprogram.planettexgen.handlers;

import com.nythicalnorm.voxelspaceprogram.VoxelSpaceProgram;
import com.nythicalnorm.voxelspaceprogram.network.ClientboundPlanetTexturePacket;
import com.nythicalnorm.voxelspaceprogram.network.PacketHandler;
import com.nythicalnorm.voxelspaceprogram.planettexgen.GradientSupplier;
import com.nythicalnorm.voxelspaceprogram.solarsystem.PlanetsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class PlanetTexHandler {
    private static final String modSaveDirPath = "voxelspaceprogram";
    private static final String planetTexDirPath = "planets";

    private static File modDir;
    private static File planettexDir;

    HashMap<String, CompletableFuture<byte[]>> planetTexturesBytes;

    public void loadOrCreateTex(MinecraftServer server, PlanetsProvider planets) {
        Path modSubFolder = server.getWorldPath(LevelResource.ROOT).resolve(modSaveDirPath);
        modDir = new File(modSubFolder.toUri());

        if (!modDir.exists()) {
            boolean wasCreated = modDir.mkdir();
            if (!wasCreated) {
                VoxelSpaceProgram.logError("Mod Directory Creation Failed.");
                return;
            }
        }
        Path planetDir = modSubFolder.resolve(planetTexDirPath);
        planettexDir = new File(planetDir.toUri());

        if (!planettexDir.exists()) {
            boolean wasCreated = planettexDir.mkdir();
            if (!wasCreated) {
                VoxelSpaceProgram.logError("Planet Directory Creation Failed.");
                return;
            }
        }
        RandomSource randomSource = RandomSource.create(server.getLevel(Level.OVERWORLD).getSeed());
        planetTexturesBytes = new HashMap<>();
        ExecutorService texExecutorService = Executors.newSingleThreadExecutor();

        server.getPlayerList().broadcastSystemMessage(Component.translatable("voxelspaceprogram.state.planetgen_start"), true);

        for (String planetaryName : planets.allPlanetsAddresses.keySet()) {
            CompletableFuture<byte[]> planetImgData = CompletableFuture.supplyAsync(
                    new WholePlanetTexGenTask(planetDir, planetaryName, randomSource.nextLong(), GradientSupplier.textureForPlanets.get(planetaryName)), texExecutorService);
            planetTexturesBytes.put(planetaryName, planetImgData);

            planetImgData.thenRun(() -> {
                server.getPlayerList().broadcastSystemMessage(Component.translatable("voxelspaceprogram.state.planetgen_end",
                        planetaryName), true);
            });
        }
    }

    public void sendAllTexToPlayer(UUID uuid) {
        for (Map.Entry<String, CompletableFuture<byte[]>> texfuture : planetTexturesBytes.entrySet()) {
            texfuture.getValue().thenAccept(texBytes -> {
                sendToPlayer(VoxelSpaceProgram.getSolarSystem().get().getServer().getPlayerList().getPlayer(uuid),
                        texfuture.getKey(), texBytes);
            });
        }
    }

    private void sendToPlayer(ServerPlayer player, String planetName, byte[] texture) {
        PacketHandler.sendToPlayer(new ClientboundPlanetTexturePacket(planetName, texture), player);
    }
}
