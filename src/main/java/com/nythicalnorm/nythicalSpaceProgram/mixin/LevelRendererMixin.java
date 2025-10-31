package com.nythicalnorm.nythicalSpaceProgram.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.PlanetShineRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    public void renderSky(PoseStack pPoseStack, Matrix4f pProjectionMatrix, float pPartialTick, Camera pCamera, boolean pIsFoggy, Runnable pSkyFogSetup, CallbackInfo ci) {
        LevelRenderer mylvl = (LevelRenderer) (Object) this;
        Minecraft mc = Minecraft.getInstance();

        if (mc.level.effects().skyType() == DimensionSpecialEffects.SkyType.NORMAL) {
            pSkyFogSetup.run();
            if (!pIsFoggy) {
                FogType fogtype = pCamera.getFluidInCamera();
                if (fogtype != FogType.POWDER_SNOW && fogtype != FogType.LAVA && !nythicalspaceprogram$doesMobEffectBlockSky(pCamera)) {
                    PlanetShineRenderer.renderSkylight(mc, mylvl, pPoseStack, pProjectionMatrix, pPartialTick, pCamera);
                }
            }
            ci.cancel();
        }
    }

    @Unique
    private boolean nythicalspaceprogram$doesMobEffectBlockSky(Camera pCamera) {
        Entity entity = pCamera.getEntity();
        if (!(entity instanceof LivingEntity livingentity)) {
            return false;
        } else {
            return livingentity.hasEffect(MobEffects.BLINDNESS) || livingentity.hasEffect(MobEffects.DARKNESS);
        }
    }
}
