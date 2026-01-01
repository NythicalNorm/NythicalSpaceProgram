package com.nythicalnorm.nythicalSpaceProgram.block.gse.entity;

import com.nythicalnorm.nythicalSpaceProgram.block.BlockUtil;
import com.nythicalnorm.nythicalSpaceProgram.block.gse.VehicleAssembler;
import com.nythicalnorm.nythicalSpaceProgram.block.manufacturing.entity.NSPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PlatformAssemblyEntity extends BlockEntity {
    private BlockPos vehicleAssemblerHost;

    public PlatformAssemblyEntity(BlockPos pPos, BlockState pBlockState) {
        super(NSPBlockEntities.VEHICLE_ASSEMBLY_PLATFORM_BE.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null) {
            if (!this.level.isClientSide()) {
                findAndUpdateHostAssemblerRef(level, getBlockPos());
            }
        }
    }

    public void setHostBlock(BlockPos blockPos) {
        vehicleAssemblerHost = blockPos;
    }

    public BlockPos getHostBlock() {
        return vehicleAssemblerHost;
    }

    public void removeHostReference() {
        if (vehicleAssemblerHost != null) {
            if (level.getBlockEntity(vehicleAssemblerHost) instanceof VehicleAssemblerEntity vehicleAssemblerEntity) {
                vehicleAssemblerEntity.removePlatformRef(this.getBlockPos());
            }
        }
    }

    private void findAndUpdateHostAssemblerRef(Level pLevel, BlockPos pPos) {
        BlockPos hostBlock = null;
        VehicleAssemblerEntity vehicleAssembler = null;

        if (BlockUtil.getBlockEntityAroundMeHorizontal(pPos, VehicleAssemblerEntity.class, pLevel) instanceof VehicleAssemblerEntity vAsm) {
            vehicleAssembler = vAsm;
            hostBlock = vehicleAssembler.getBlockPos();
        } else if (BlockUtil.getBlockEntityAroundMeHorizontal(pPos, com.nythicalnorm.nythicalSpaceProgram.block.gse.entity.PlatformAssemblyEntity.class, pLevel) instanceof com.nythicalnorm.nythicalSpaceProgram.block.gse.entity.PlatformAssemblyEntity VAPE) {
            if (VAPE.getHostBlock() != null) {
                if (pLevel.getBlockEntity(VAPE.getHostBlock()) instanceof VehicleAssemblerEntity vAsm) {
                    vehicleAssembler = vAsm;
                    hostBlock = vehicleAssembler.getBlockPos();
                }
            }
        }
        if (vehicleAssembler != null) {
            vehicleAssembler.addPlatformRef(pPos);
        }
        setHostBlock(hostBlock);
    }
}
