package com.nythicalnorm.voxelspaceprogram.block.gse.screen;

import com.nythicalnorm.voxelspaceprogram.block.NSPBlocks;
import com.nythicalnorm.voxelspaceprogram.gui.NSPMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class VehicleAssemblerMenu extends AbstractContainerMenu {
    private final BlockEntity vehicleAssemblerBE;

    public VehicleAssemblerMenu(int pContainerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(pContainerId, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public VehicleAssemblerMenu(int pContainerId, BlockEntity blockEntity) {
        super(NSPMenuTypes.VEHICLE_ASSEMBLER_MENU.get(), pContainerId);

        vehicleAssemblerBE = blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        if (vehicleAssemblerBE.getLevel() != null) {
            return stillValid(ContainerLevelAccess.create(vehicleAssemblerBE.getLevel(), vehicleAssemblerBE.getBlockPos()),
                    pPlayer, NSPBlocks.VEHICLE_ASSEMBLER.get());
        }
        return false;
    }
}
