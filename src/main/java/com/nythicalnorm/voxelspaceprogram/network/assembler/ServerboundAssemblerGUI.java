package com.nythicalnorm.voxelspaceprogram.network.assembler;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public class ServerboundAssemblerGUI {
    protected final BlockPos assemblerPos;

    public enum ButtonType {
        CREATE_ASSEMBLY_AREA
    }

    public ServerboundAssemblerGUI(BlockPos assemblerpos) {
        assemblerPos = assemblerpos;
    }

    public ServerboundAssemblerGUI(FriendlyByteBuf friendlyByteBuf) {
        assemblerPos = friendlyByteBuf.readBlockPos();
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(assemblerPos);
    }
}
