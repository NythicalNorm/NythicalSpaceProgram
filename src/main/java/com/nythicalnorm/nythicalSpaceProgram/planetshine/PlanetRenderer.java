package com.nythicalnorm.nythicalSpaceProgram.planetshine;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PlanetRenderer {
    private static VertexBuffer planetvertex = new VertexBuffer(VertexBuffer.Usage.STATIC);
    //private static final RenderType PLANET = RenderType.create("planet", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 131072, false, true,
    //        RenderType.CompositeState.builder().setLightmapState(new RenderStateShard.LightmapStateShard(false)).setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getPositionTexShader)).createCompositeState(true));

    public static void setupModels(Minecraft mc, PoseStack poseStack) {
        poseStack.pushPose();
        poseStack.setIdentity();

        List<BakedQuad> planetquads = SphereModelGenerator.getsphereQuads(); //planetModel.getQuads(null,null, RandomSource.create(), ModelData.builder().build(), RenderType.solid());
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        for(BakedQuad bakedquad : planetquads) {
            bufferbuilder.putBulkData(poseStack.last(), bakedquad, 1f, 1f, 1f, 10, 10);
        }
        planetvertex.bind();
            planetvertex.upload(((BufferBuilder)bufferbuilder).end());
        VertexBuffer.unbind();

        poseStack.popPose();
    }

    public static void renderPlanet(PoseStack poseStack, Minecraft mc, Camera camera, Matrix4f projectionMatrix, Matrix4f lastMatrix) {
//        if (planetvertex == null) {
//            NythicalSpaceProgram.LOGGER.error("Planet buffer is empty not rendering.");
//            return;
//        }
        //BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        //MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();

        poseStack.pushPose();
        //BufferUploader.draw(planetvertex.end());
        //poseStack.mulPoseMatrix;
        //poseStack.mulPoseMatrix(RenderSystem.getModelViewMatrix());
        //poseStack.mulPoseMatrix(lastMatrix);
        //poseStack.mulPoseMatrix(projectionMatrix);

        poseStack.translate(-5f,1.5f,0f);
        //poseStack.rotateAround(new Quaternionf(3f,3f,0f,0f),10f,0f,0f);
        //poseStack.mulPose(new Quaternionf(0f,0f,0f,0f));

        poseStack.scale(1f,1f, 1f);
        RenderSystem.depthMask(true);

        planetvertex.bind();
        //RenderSystem.setShaderTexture(0, ResourceLocation.parse("textures/item/axolotl_bucket.png"));
        RenderSystem.disableCull();
        //RenderSystem.setShaderColor(1f,1f,1f,1f); //RenderSystem.getModelViewMatrix()
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, ResourceLocation.parse("nythicalspaceprogram:textures/planets/moon_axis.png"));
        //RenderSystem.blendFunc();
        ShaderInstance shad = GameRenderer.getPositionTexShader();

        planetvertex.drawWithShader(poseStack.last().pose(), projectionMatrix, shad);
        VertexBuffer.unbind();

        RenderSystem.enableCull();
        RenderSystem.depthMask(false);
        poseStack.popPose();
    }
}
