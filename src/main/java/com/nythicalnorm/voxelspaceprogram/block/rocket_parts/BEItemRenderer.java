package com.nythicalnorm.voxelspaceprogram.block.rocket_parts;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nythicalnorm.voxelspaceprogram.Item.RocketryBlockItem;
import com.nythicalnorm.voxelspaceprogram.block.NSPBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BEItemRenderer extends BlockEntityWithoutLevelRenderer {
    public BEItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (pStack.getItem() instanceof RocketryBlockItem blockItem) {
            if (blockItem.getBlock() == NSPBlocks.THREE_KEROLOX.get()) {

            }
        }
    }
}
