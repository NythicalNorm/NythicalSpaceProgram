package com.nythicalnorm.nythicalSpaceProgram.datagen;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.block.NSPBlocks;
import com.nythicalnorm.nythicalSpaceProgram.fluid.NSPFluids;
import com.nythicalnorm.nythicalSpaceProgram.block.custom.FootprintedType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static com.nythicalnorm.nythicalSpaceProgram.block.custom.FootprintedRegolith.FOOTPRINTTYPE;

public class NSPBlockStateProvider extends BlockStateProvider {
    public NSPBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NythicalSpaceProgram.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        simpleBlockWithItem(NSPBlocks.OXYGEN_PROPELLANT_TANK.get(), getColumnCubeModel(NSPBlocks.OXYGEN_PROPELLANT_TANK,
                NythicalSpaceProgram.rl( "block/oxygen_propellant_tank_side"),
                NythicalSpaceProgram.rl( "block/oxygen_propellant_tank_end")));

        simpleBlockWithItem(NSPBlocks.MAGNETIZED_IRON_BLOCK.get(), cubeAll(NSPBlocks.MAGNETIZED_IRON_BLOCK.get()));
        SetFootprintBlockState(NSPBlocks.LUNAR_REGOLITH);
        simpleBlockWithItem(NSPBlocks.CRYOGENIC_AIR_SEPARATOR.get(), cubeAll(NSPBlocks.CRYOGENIC_AIR_SEPARATOR.get()));
        modelBlockWithItem(NSPBlocks.MAGNETIZER.get(), new ModelFile.UncheckedModelFile(
                NythicalSpaceProgram.rl( "block/magnetizer")));

        fluidBlock(NSPFluids.LIQUID_OXYGEN.block);
        //simpleBlockWithItem(NSPBlocks.CRYOGENIC_AIR_SEPARATOR_PART.get(), models().getExistingFile(NythicalSpaceProgram.rl( "block/cryogenic_air_separator_part")));
        connectedBlock(NSPBlocks.CRYOGENIC_AIR_SEPARATOR_PART);
    }

    protected void modelBlockWithItem(Block block, ModelFile model) {
        horizontalBlock(block, model);
        simpleBlockItem(block, model);
    }

    protected void SetFootprintBlockState (RegistryObject<Block> block) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            FootprintedType myEnumval = state.getValue(FOOTPRINTTYPE);
            if (myEnumval == FootprintedType.NOFOOTPRINTS) {
               return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("lunar_regolith",
                       NythicalSpaceProgram.rl( "block/lunar_regolith")))};
           }

           else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeTop("lunar_regolith_" + state.getValue(FOOTPRINTTYPE),
                        NythicalSpaceProgram.rl( "block/lunar_regolith"),
                        NythicalSpaceProgram.rl( "block/lunar_regolith_" + state.getValue(FOOTPRINTTYPE))
                ))};
           }
        });

        simpleBlockItem(block.get(), cubeAll(block.get()));
    }

    protected ModelFile getColumnCubeModel(@NotNull RegistryObject<Block> block, ResourceLocation side, ResourceLocation end) {
        assert block.getId() != null;
        return models().withExistingParent(block.getId().getPath(), ModelProvider.BLOCK_FOLDER + "/cube_column_horizontal")
                .texture("side", side)
                .texture("end", end);
    }

    protected void fluidBlock(RegistryObject<LiquidBlock> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    protected void connectedBlock(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), models().getExistingFile(block.getKey().location()));
    }
}
