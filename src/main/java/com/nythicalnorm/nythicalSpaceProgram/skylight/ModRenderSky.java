package com.nythicalnorm.nythicalSpaceProgram.skylight;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class ModRenderSky {
    public static void renderSkylight(RenderLevelStageEvent.Stage stage, LevelRenderer levelRenderer, PoseStack poseStack,
                                      Matrix4f projectionMatrix, int renderTick, Camera camera, Frustum frustum)
    {
        // Get necessary rendering objects from the event
        // Get camera position to translate render origin
        Minecraft mc = Minecraft.getInstance();
        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();

        // Set up our own MultiBufferSource
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();

        // Begin rendering logic.
        // Push the current pose so we can perform translations and rotations.
        poseStack.pushPose();
        Matrix4f matrix4f1 = poseStack.last().pose();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(1f, 0f, 0f, 1.0F);

//        if (bufferbuilder.building()) {
//            return;
//        }
//
//        bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
//        bufferbuilder.vertex(matrix4f1, -30f, -100.0F, 30f).color( 1.0f, 0.0f, 0.0f, 1.0f).endVertex();
        // Translate to the correct world position relative to the camera
        poseStack.translate(10.0 - cameraPos.x, 65.0 - cameraPos.y, 10.0 - cameraPos.z);

        // The VertexConsumer is obtained from our buffer using a specific RenderType.
        LevelRenderer.renderLineBox(
                poseStack,
                buffer.getBuffer(RenderType.lines()),
                0.0, 0.0, 0.0,
                1.0, 1.0, 1.0,
                1.0f, 1.0f, 0.0f, 1.0f // RGBA color
        );
        //BufferUploader.drawWithShader(bufferbuilder.end());
        // Ensure all buffered draws are flushed to the screen.
        buffer.endBatch();

        // Pop the pose to revert to the previous state.
        poseStack.popPose();

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);
    }

    private BufferBuilder.RenderedBuffer DosomeVertex(BufferBuilder pBuilder) {
        pBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
        pBuilder.vertex(5d, 4d, 2d).endVertex();
        pBuilder.vertex(10d, 50d, 100d).endVertex();

        return pBuilder.end();
    }
}
