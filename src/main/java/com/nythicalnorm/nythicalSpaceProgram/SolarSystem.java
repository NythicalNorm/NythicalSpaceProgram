package com.nythicalnorm.nythicalSpaceProgram;

import com.nythicalnorm.nythicalSpaceProgram.dimensions.DimensionTeleporter;
import com.nythicalnorm.nythicalSpaceProgram.dimensions.SpaceDimension;
import com.nythicalnorm.nythicalSpaceProgram.network.*;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.Orbit;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.OrbitalElements;
import com.nythicalnorm.nythicalSpaceProgram.planettexgen.handlers.PlanetTexHandler;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.PlanetsProvider;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.EntitySpacecraftBody;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.ServerPlayerSpacecraftBody;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.SpacecraftControlState;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.util.HashMap;
import java.util.Stack;

public class SolarSystem {
    public double currentTime; // time passed since start in seconds
    public double timePassPerSecond;
    //public static double tickTimeStamp;
    private final MinecraftServer server;
    private HashMap<String, ServerPlayerSpacecraftBody> allPlayerOrbitalAddresses;
    private final PlanetsProvider planetsProvider;
    private PlanetTexHandler planetTexHandler;

    public SolarSystem(MinecraftServer server, PlanetsProvider pPlanets) {
        timePassPerSecond = 1;
        allPlayerOrbitalAddresses = new HashMap<>();
        this.server = server;
        this.planetsProvider = pPlanets;

    }

    public MinecraftServer getServer() {
        return server;
    }

    public PlanetsProvider getPlanetsProvider() {
        return planetsProvider;
    }

    public void OnTick() {
        currentTime = currentTime + (timePassPerSecond/20);
        planetsProvider.UpdatePlanets(currentTime);

        PacketHandler.sendToAllClients(new ClientboundSolarSystemTimeUpdate(currentTime,timePassPerSecond));
    }

    public void serverStarted() {
        this.planetTexHandler = new PlanetTexHandler();
        server.execute(() -> planetTexHandler.loadOrCreateTex(server, this.planetsProvider));
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void ChangeTimeWarp(int proposedSetTimeWarpSpeed, ServerPlayer player) {
        if (player == null) {
            return;
        }
        timePassPerSecond = (double) Mth.clamp(proposedSetTimeWarpSpeed, 0, 5000000);
        server.getPlayerList().broadcastSystemMessage(Component.translatable("nythicalspaceprogram.state.settimewarp",
                proposedSetTimeWarpSpeed), true);
        PacketHandler.sendToAllClients(new ClientboundTimeWarpUpdate(true, proposedSetTimeWarpSpeed));
    }

    public void playerJoined(Player entity) {
        // this is not working check before making a saving system
        if (allPlayerOrbitalAddresses.containsKey(entity.getStringUUID())) {
            EntitySpacecraftBody playerSpacecraftBody = allPlayerOrbitalAddresses.get(entity.getStringUUID());
            PacketHandler.sendToPlayer(new ClientboundLoginSolarSystemState(playerSpacecraftBody), (ServerPlayer) entity);
        }
        else {
            if (entity.level().dimension() == SpaceDimension.SPACE_LEVEL_KEY) {
                ServerLevel overworldLevel = server.getLevel(Level.OVERWORLD);
                entity.changeDimension(overworldLevel, new DimensionTeleporter(overworldLevel.getSharedSpawnPos().getCenter()));
            }
            PacketHandler.sendToPlayer(new ClientboundLoginSolarSystemState(true), (ServerPlayer) entity);
        }
        if (planetTexHandler != null) {
            planetTexHandler.sendAllTexToPlayer(entity.getUUID());
        }
    }

    // Called when the player changes SOIs or joins on orbit artificially like the teleport command
    public void playerJoinOrbit(String body, ServerPlayer player, OrbitalElements elements) {
        Stack<String> newAddress = planetsProvider.getPlanetAddress(body);
        String PlayerUUid = player.getStringUUID();
        if (player.level().dimension() != SpaceDimension.SPACE_LEVEL_KEY) {
            player.changeDimension(server.getLevel(SpaceDimension.SPACE_LEVEL_KEY), new DimensionTeleporter(new Vec3(0d, 128d, 0d)));
        }

        if (allPlayerOrbitalAddresses.containsKey(PlayerUUid)) {
            Orbit playerSpacecraftBody = allPlayerOrbitalAddresses.get(PlayerUUid);
            if (playerSpacecraftBody == null) {
                return;
            }
            Stack<String> oldAddress = playerSpacecraftBody.getAddress();

            planetsProvider.playerChangeOrbitalSOIs(PlayerUUid, playerSpacecraftBody, newAddress, elements);

            PacketHandler.sendToPlayer(new ClientboundFocusedOrbitUpdate(player, oldAddress, newAddress, elements), player);
        }
        else  {
            Quaternionf playerRot = new Quaternionf();
            ServerPlayerSpacecraftBody newOrbitalData = new ServerPlayerSpacecraftBody(player, true, true, playerRot, elements);
            planetsProvider.playerJoinedOrbital(PlayerUUid, newAddress, newOrbitalData);
            allPlayerOrbitalAddresses.put(PlayerUUid, newOrbitalData);
            PacketHandler.sendToPlayer(new ClientboundFocusedOrbitUpdate(player, null, newAddress, elements), player);
        }
    }

    public void playerCloned(ServerPlayer player) {
        ServerPlayerSpacecraftBody serverPlayerSpacecraftBody = allPlayerOrbitalAddresses.get(player.getStringUUID());
        if (serverPlayerSpacecraftBody != null) {
            serverPlayerSpacecraftBody.setPlayerEntity(player);
        }

        playerDimChanged(player, player.level().dimension());
    }

    public void handleSpacecraftMove(ServerPlayer player, Stack<String> spacecraftBodyAddress, SpacecraftControlState state) {
       Orbit spacecraft = planetsProvider.getOrbit(spacecraftBodyAddress);
       if (spacecraft == null) {
           return;
       }

       if (spacecraft instanceof EntitySpacecraftBody entitySpacecraftBody) {
           entitySpacecraftBody.processMovement(state);
       }
    }

    public void playerDimChanged(Player entity, ResourceKey<Level> toDimension) {
        if (toDimension != SpaceDimension.SPACE_LEVEL_KEY) {
            ServerPlayerSpacecraftBody serverPlayerSpacecraftBody = allPlayerOrbitalAddresses.get(entity.getStringUUID());

            if (serverPlayerSpacecraftBody != null) {
                serverPlayerSpacecraftBody.removeYourself();
            }
        }
    }

    public void removePlayerFromOrbit(String id) {
        allPlayerOrbitalAddresses.remove(id);
    }
}
