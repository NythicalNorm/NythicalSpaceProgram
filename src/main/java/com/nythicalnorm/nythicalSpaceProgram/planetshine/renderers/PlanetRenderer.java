package com.nythicalnorm.nythicalSpaceProgram.planetshine.renderers;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.orbit.PlanetaryBody;
import com.nythicalnorm.nythicalSpaceProgram.planet.PlanetAtmosphere;
import com.nythicalnorm.nythicalSpaceProgram.orbit.Star;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.generators.QuadSphereModelGenerator;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.shaders.ModShaders;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class PlanetRenderer {
    private static Supplier<ShaderInstance> planetShader;
    private static Uniform sunDirUniform;

    public static void setupShader() {
        planetShader = ModShaders.getPlanetShaderInstance();
        if (planetShader.get() != null) {
            sunDirUniform = planetShader.get().getUniform("SunDirection");
        }
        else {
            NythicalSpaceProgram.logError("Shader not loading");
        }
    }

    //for rendering in the map
    public static void render(PlanetaryBody planet, PoseStack poseStack, Matrix4f projectionMatrix) {
        render(planet, Optional.empty(), poseStack, projectionMatrix, 0, 1.0f, null);
    }

    public static void render(PlanetaryBody planet, Optional<PlanetAtmosphere> currentPlanetAtmosphere, PoseStack poseStack,
                              Matrix4f projectionMatrix, double distance, float currentAlbedo, @Nullable Vector3d differenceVector) {

        if ((differenceVector != null) && (distance < (planet.getRadius() + 320))) {
            return;
        }

        poseStack.pushPose();
        Quaternionf planetRot = planet.getRotation();
        RenderSystem.enableBlend();

        if (currentPlanetAtmosphere.isPresent()) {
                //AtmosphereRenderer.render(obj,atmosphere, poseStack, projectionMatrix, partialTick);
            PlanetAtmosphere bodyAtmos = planet.getAtmoshpere();
            float renderOpacity = (currentAlbedo * (bodyAtmos.getExposureNight() - bodyAtmos.getExposureDay())) + bodyAtmos.getExposureDay();
            RenderSystem.setShaderColor(1.0f,1.0f,1.0f,renderOpacity);
        }

        if (differenceVector != null) {
            SpaceObjRenderer.PerspectiveShift(distance, differenceVector, planetRot,
                    planet.getRadius(), poseStack);
        }

        QuadSphereModelGenerator.getSphereBuffer().bind();
        RenderSystem.setShaderTexture(0, planet.texture);

        Vector3d absoluteDir = planet.getAbsolutePos().normalize();
        Vector3f lightDir = new Vector3f((float) absoluteDir.x,(float) absoluteDir.y,(float) absoluteDir.z);
        lightDir.rotate(planetRot.invert());
        lightDir.normalize();

        sunDirUniform.set(lightDir);
        ShaderInstance shad = planetShader.get();

        if (planet instanceof Star) { // || obj.getBody() == css.getDimPlanet()) {
            shad = GameRenderer.getPositionTexShader();
        }

        QuadSphereModelGenerator.getSphereBuffer().drawWithShader(poseStack.last().pose(), projectionMatrix, shad);
        VertexBuffer.unbind();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        poseStack.popPose();
    }
}
