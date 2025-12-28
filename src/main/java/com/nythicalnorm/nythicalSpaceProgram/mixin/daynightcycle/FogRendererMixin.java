package com.nythicalnorm.nythicalSpaceProgram.mixin.daynightcycle;

import com.nythicalnorm.nythicalSpaceProgram.planetshine.PlanetShine;
import net.minecraft.client.renderer.FogRenderer;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Redirect(method = "setupColor", at = @At(value = "INVOKE", target = "Lorg/joml/Vector3f;dot(Lorg/joml/Vector3fc;)F"))
    private static float setupColor(Vector3f instance, Vector3fc v) {
        Vector3f sunPos = PlanetShine.getSunPosOverworld();
        if (sunPos != null) {
            return -instance.dot(sunPos);
        }
        return instance.dot(v);
    }
}
