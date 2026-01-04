package com.nythicalnorm.voxelspaceprogram.block.gse;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

@Deprecated
public class AssemblerBBSearcher {
    //BlockState[][][] alreadySeearchedblockPos;
    boolean isBoundingBoxSearching = false;
    int lastSearchedBlockPosIndex = 0;
    private final BoundingBox assemblyBoundingBox;

    private boolean foundNonAir;
    Level level;

    public AssemblerBBSearcher(BoundingBox assemblyBoundingBox, Level level) {
        this.assemblyBoundingBox = assemblyBoundingBox;
    }

    private void startBoundingBoxAllAirCheck() {
        isBoundingBoxSearching = true;
        lastSearchedBlockPosIndex = 0;
    }

    public boolean lazyGetAllBlocksTick() {
        int totalIndex = assemblyBoundingBox.maxY();

//        for (int index = this.lastSearchedBlockPosIndex; index <= assemblyBoundingBox.maxY(); index++) {
//            BlockPos pos = blockPosFromIndex(index);
//            if (level.getBlockStatesIfLoaded())
//        }
//
//
//            for (int y = this.lastYIndexBlockPositions; y <= assemblyBoundingBox.maxY(); y++) {
//            for (int x = assemblyBoundingBox.minX(); x <= assemblyBoundingBox.maxX(); x++) {
//                for (int z = assemblyBoundingBox.minZ(); z <= assemblyBoundingBox.maxZ(); z++) {
//                    int xIndex = x - assemblyBoundingBox.minX();
//                    int yIndex = y - assemblyBoundingBox.minY();
//                    int zIndex = z - assemblyBoundingBox.minZ();
//                    if (alreadySeearchedblockPos[xIndex][yIndex][zIndex] == null) {
//                        alreadySeearchedblockPos[xIndex][yIndex][zIndex] =
//                                getLevel().getBlockState(new BlockPos(x, y, z));
//                        if (Util.getNanos() - startTime > 80000) {
//                            this.lastSearchedBlockPosIndex = (y * assemblyBoundingBox.maxX() * assemblyBoundingBox.maxZ()) + (x * assemblyBoundingBox.maxZ()) + z;
//                            VoxelSpaceProgram.log("Next");
//                            return false;
//                        }
//                    }
//                }
//            }
//        }
        return true;
    }

    private int indexFromBlockPos(BlockPos pos) {
        return  (pos.getY() * assemblyBoundingBox.getXSpan() * assemblyBoundingBox.getZSpan()) +
                (pos.getX() * assemblyBoundingBox.getZSpan()) + pos.getZ();
    }

    private BlockPos blockPosFromIndex(int index) {
        int y = index / (assemblyBoundingBox.getXSpan() * assemblyBoundingBox.getZSpan());
        int x = (index / assemblyBoundingBox.getZSpan()) % assemblyBoundingBox.getXSpan();
        int z = index % assemblyBoundingBox.getZSpan();

        return new BlockPos(x, y, z);
    }
}
