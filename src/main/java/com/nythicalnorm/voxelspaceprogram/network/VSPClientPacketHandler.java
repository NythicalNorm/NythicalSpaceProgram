package com.nythicalnorm.voxelspaceprogram.network;

import com.nythicalnorm.voxelspaceprogram.block.gse.screen.VehicleAssemblerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class VSPClientPacketHandler {
    public static void handleMenuPacket(Component[] clientboundAssemblerProblems) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.containerMenu instanceof VehicleAssemblerMenu vehicleAssemblerMenu) {
            vehicleAssemblerMenu.setProblems(clientboundAssemblerProblems);
        }
    }
}
