package com.nythicalnorm.voxelspaceprogram.Item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class RocketryBlockItem extends BlockItem {
    public RocketryBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties.requiredFeatures());
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(VSPClientItemExtensions.INSTANCE);
    }
}
