package com.nythicalnorm.voxelspaceprogram.mixin.daynightcycle;


import com.nythicalnorm.voxelspaceprogram.VoxelSpaceProgram;
import com.nythicalnorm.voxelspaceprogram.solarsystem.planet.PlanetLevelData;
import com.nythicalnorm.voxelspaceprogram.solarsystem.planet.PlanetLevelDataProvider;
import com.nythicalnorm.voxelspaceprogram.util.DayNightCycleHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(LevelReader.class)
public interface LevelReaderMixin extends BlockAndTintGetter, CollisionGetter, SignalGetter, BiomeManager.NoiseBiomeSource {
    @Shadow
    int getMaxLocalRawBrightness(BlockPos pPos, int brightness);

    @Shadow
    int getSkyDarken();

    /**
     * @author NythicalNorm
     * @reason injecting into default interface method doesn't seem to work so once again overwriting this method to give
     * the correct time for a timezone.
     */
    @Overwrite
    default int getMaxLocalRawBrightness(BlockPos pPos) {
        int DarkenAmount = this.getSkyDarken();
        Optional<Integer> darkLevelFromPlanet = Optional.empty();

        if (this instanceof Level level) {
            if (level.isClientSide) {
                if (VoxelSpaceProgram.getCelestialStateSupplier().isPresent()) {
                    darkLevelFromPlanet = DayNightCycleHandler.getDarknessLightLevel(Optional.of(VoxelSpaceProgram.getCelestialStateSupplier().get().getPlayerOrbit().getSunAngle()), level);
                }
            }
            else {
                LazyOptional<PlanetLevelData> plntData =  level.getCapability(PlanetLevelDataProvider.PLANET_LEVEL_DATA);
               if (plntData.resolve().isPresent() && VoxelSpaceProgram.getSolarSystem().isPresent())
               {
                   darkLevelFromPlanet = DayNightCycleHandler.getDarknessLightLevel(pPos, level);
               }
            }
        }
        else if (this instanceof WorldGenRegion) {
        }
        else {
            VoxelSpaceProgram.log("Where");
        }
        if (darkLevelFromPlanet.isPresent()) {
            DarkenAmount = darkLevelFromPlanet.get();
        }
        return getMaxLocalRawBrightness(pPos, DarkenAmount);
    }
}
