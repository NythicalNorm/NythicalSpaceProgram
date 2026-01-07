package com.nythicalnorm.voxelspaceprogram.block.rocket_parts.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nythicalnorm.voxelspaceprogram.VoxelSpaceProgram;
import com.nythicalnorm.voxelspaceprogram.block.rocket_parts.entity.EngineEntity;
import com.nythicalnorm.voxelspaceprogram.block.rocket_parts.entity.models.EngineModels;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;

public class EngineEntityRenderer implements BlockEntityRenderer<EngineEntity> {
    public static final Material ENGINE_TEXTURE_LOCATION = new Material(InventoryMenu.BLOCK_ATLAS, VoxelSpaceProgram.rl( "block/three_kerolox"));
    private final ModelPart gimbling;
    private final ModelPart fixed;

    public EngineEntityRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelpart = context.bakeLayer(EngineModels.ThreeKeroloxLayerLoc);
        this.fixed = modelpart.getChild("fixed");
        this.gimbling = this.fixed.getChild("gimbling");
    }

    @Override
    public void render(EngineEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        VertexConsumer vertexconsumer = ENGINE_TEXTURE_LOCATION.buffer(pBuffer, RenderType::entitySolid);
        pPoseStack.translate(0.5f,0.5f,0.5f);
        pPoseStack.mulPose(pBlockEntity.getFacingRot());
        pPoseStack.translate(0f,1.0f,0f);

        fixed.render(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay);
        pPoseStack.popPose();
    }
}
