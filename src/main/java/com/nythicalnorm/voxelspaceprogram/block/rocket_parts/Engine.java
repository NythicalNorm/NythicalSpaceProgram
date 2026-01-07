package com.nythicalnorm.voxelspaceprogram.block.rocket_parts;

import com.nythicalnorm.voxelspaceprogram.block.NSPBlocks;
import com.nythicalnorm.voxelspaceprogram.block.rocket_parts.entity.EngineEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class Engine extends MultiblockRocketry {
    public Engine(Properties pProperties) {
        super(pProperties, 3, 48, 38);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EngineEntity(pPos, pState);
    }

    @Override
    public Block getBoundingBlock() {
        return NSPBlocks.BOUNDING_BLOCK.get();
    }
}
