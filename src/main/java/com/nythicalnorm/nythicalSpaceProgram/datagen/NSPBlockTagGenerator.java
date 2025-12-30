package com.nythicalnorm.nythicalSpaceProgram.datagen;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.block.NSPBlocks;
import com.nythicalnorm.nythicalSpaceProgram.util.NSPTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class NSPBlockTagGenerator extends BlockTagsProvider {
    public NSPBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NythicalSpaceProgram.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(NSPTags.Blocks.MAGNETIC_METALS).add(Blocks.IRON_BLOCK, Blocks.COPPER_BLOCK, Blocks.GOLD_BLOCK, Blocks.REDSTONE_BLOCK);

        this.tag(BlockTags.NEEDS_STONE_TOOL).add(NSPBlocks.CRYOGENIC_AIR_SEPARATOR.get(), NSPBlocks.MAGNETIZER.get(),
                NSPBlocks.MAGNETIZED_IRON_BLOCK.get(), NSPBlocks.CRYOGENIC_AIR_SEPARATOR_PART.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(NSPBlocks.OXYGEN_PROPELLANT_TANK.get(), NSPBlocks.LUNAR_REGOLITH.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(NSPBlocks.OXYGEN_PROPELLANT_TANK.get())
                .add(NSPBlocks.LUNAR_REGOLITH.get())
                .add(NSPBlocks.CRYOGENIC_AIR_SEPARATOR.get())
                .add(NSPBlocks.CRYOGENIC_AIR_SEPARATOR_PART.get())
                .add(NSPBlocks.MAGNETIZER.get())
                .add(NSPBlocks.MAGNETIZED_IRON_BLOCK.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(NSPBlocks.LUNAR_REGOLITH.get());
    }
}
