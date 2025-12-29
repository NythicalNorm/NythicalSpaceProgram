package com.nythicalnorm.nythicalSpaceProgram.mixin.daynightcycle;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.planet.PlanetaryBody;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.planet.PlanetLevelData;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.planet.PlanetLevelDataProvider;
import com.nythicalnorm.nythicalSpaceProgram.util.DayNightCycleHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Level.class)
public class LevelMixin {
    // Still makes no sense why mixining this function affects natural mob spawn as this seems to just
    // linearly increase difficulty and caps out at 3 days...???
    @Inject(method = "getCurrentDifficultyAt", at= @At(value = "HEAD"),cancellable = true)
    public void getCurrentDifficultyAt(BlockPos pPos, CallbackInfoReturnable<DifficultyInstance> cir) {
        Level level = (Level) (Object)this;

        if (!level.isClientSide()) {
            PlanetaryBody plnt = null;
            LazyOptional<PlanetLevelData> plntData = level.getCapability(PlanetLevelDataProvider.PLANET_LEVEL_DATA);
            Optional<Long> currentTime = Optional.empty();
            if (plntData.resolve().isPresent() &&  NythicalSpaceProgram.getSolarSystem().isPresent()) {
                plnt = NythicalSpaceProgram.getSolarSystem().get().getPlanetsProvider().getPlanet(plntData.resolve().get().getPlanetName());
                currentTime = DayNightCycleHandler.getDayTime(pPos, plnt, NythicalSpaceProgram.getSolarSystem().get().getCurrentTime());
            }

            if (currentTime.isPresent()) {
                long i = 0L;
                float f = 0.0F;

                if (level.hasChunkAt(pPos)) {
                    f = level.getMoonBrightness();
                    i = level.getChunkAt(pPos).getInhabitedTime();
                }

                cir.setReturnValue(new DifficultyInstance(level.getDifficulty(), currentTime.get(), i, f));
            }
        }

    }
}
