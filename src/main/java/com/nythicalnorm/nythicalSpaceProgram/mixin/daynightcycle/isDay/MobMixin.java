package com.nythicalnorm.nythicalSpaceProgram.mixin.daynightcycle.isDay;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mob.class)
public class MobMixin {
    @Redirect(method = "isSunBurnTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isDay()Z"))
    public boolean isDay(Level instance) {
        return true;
    }
}
