package com.nythicalnorm.voxelspaceprogram.Item;

import com.nythicalnorm.voxelspaceprogram.VoxelSpaceProgram;
import net.minecraft.client.renderer.item.ItemProperties;

public class NSPItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(NSPItems.HANDHELD_PROPELLER.get(), VoxelSpaceProgram.rl( "inuse"),
                (pStack, pLevel, pEntity, pSeed) -> {
            if (pEntity == null) {
                return 0f;
            }
            if (pEntity.getUseItem().getItem() == NSPItems.HANDHELD_PROPELLER.get() && pEntity.isUsingItem()) { //getUseItem().getItem() == NSPItems.HANDHELD_PROPELLER.get()) {
                return 1f;
            }
            else {
                return 0f;
            }
        });
    }
}
