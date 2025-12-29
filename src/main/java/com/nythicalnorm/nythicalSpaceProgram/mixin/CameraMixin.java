package com.nythicalnorm.nythicalSpaceProgram.mixin;

import com.nythicalnorm.nythicalSpaceProgram.gui.screen.PlayerSpacecraftScreen;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Camera.class)
public class CameraMixin {
    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewYRot(F)F"))
    public float getViewYrot(Entity instance, float pPartialTick) {
        if (Minecraft.getInstance().screen instanceof PlayerSpacecraftScreen spacecraftScreen) {
            return spacecraftScreen.getViewYrot();
        }
        return instance.getViewYRot(pPartialTick);
    }

    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewXRot(F)F"))
    public float getViewXrot(Entity instance, float pPartialTick) {
        if (Minecraft.getInstance().screen instanceof PlayerSpacecraftScreen spacecraftScreen) {
            return spacecraftScreen.getViewXrot();
        }
        return instance.getViewXRot(pPartialTick);
    }
}
