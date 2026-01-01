package com.nythicalnorm.nythicalSpaceProgram.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockUtil {
    public static BlockEntity getBlockEntityAroundMeHorizontal(BlockPos pos, Class<?> blockEntityType, Level level) {
        for (int x = -1; x <= 1; x ++) {
            for (int z = -1; z <= 1; z ++) {
                BlockPos searchPos = new BlockPos(pos.getX()+x, pos.getY(), pos.getZ()+z);
                BlockEntity entity = level.getBlockEntity(searchPos);
                if (blockEntityType.isInstance(entity)) {
                    return entity;
                }
            }
        }
        return null;
    }
}
