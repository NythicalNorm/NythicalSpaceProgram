package com.nythicalnorm.nythicalSpaceProgram.block.gse.entity;

import com.nythicalnorm.nythicalSpaceProgram.block.gse.screen.VehicleAssemblerMenu;
import com.nythicalnorm.nythicalSpaceProgram.block.manufacturing.entity.NSPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VehicleAssemblerEntity extends BlockEntity implements MenuProvider {
    public VehicleAssemblerEntity( BlockPos pPos, BlockState pBlockState) {
        super(NSPBlockEntities.VEHICLE_ASSEMBLER_BE.get(), pPos, pBlockState);
    }

    List<BlockPos> platformList = new ArrayList<>();

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.nythicalspaceprogram.vehicle_assembler");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new VehicleAssemblerMenu(pContainerId,this);
    }

    public void removePlatformRef(BlockPos blockPos) {
        platformList.remove(blockPos);
    }

    public void addPlatformRef(BlockPos pPos) {
        platformList.add(pPos);
    }
}
