package com.nythicalnorm.voxelspaceprogram.Item;

import com.nythicalnorm.voxelspaceprogram.block.rocket_parts.BEItemRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class VSPClientExtensions implements IClientItemExtensions {
    private final BEItemRenderer myBEWLR = new BEItemRenderer();

    @Override
    public BEItemRenderer getCustomRenderer() {
        return myBEWLR;
    }
}
