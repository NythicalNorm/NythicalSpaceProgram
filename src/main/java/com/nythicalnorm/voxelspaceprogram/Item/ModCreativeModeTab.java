package com.nythicalnorm.voxelspaceprogram.Item;

import com.nythicalnorm.voxelspaceprogram.VoxelSpaceProgram;
import com.nythicalnorm.voxelspaceprogram.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VoxelSpaceProgram.MODID);

    public static final RegistryObject<CreativeModeTab> Main_Mod_Tab = CREATIVE_MODE_TABS.register("vsp_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.AMETHYST_SHARD))
                    .title(Component.translatable("creativetab.Main_VSP"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.HANDHELD_PROPELLER.get());
                        output.accept(ModItems.MAGNET_BOOTS.get());

                        output.accept(ModItems.SPACESUIT_HELMET.get());
                        output.accept(ModItems.CREATIVE_SPACESUIT_CHESTPLATE.get());
                        output.accept(ModItems.SPACESUIT_LEGGINGS.get());
                        output.accept(ModItems.SPACESUIT_BOOTS.get());

                        output.accept(ModBlocks.OXYGEN_PROPELLANT_TANK.get());
                        output.accept(ModBlocks.LUNAR_REGOLITH.get());
                    })
                    .build());

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
