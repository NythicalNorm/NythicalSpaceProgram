package com.nythicalnorm.nythicalSpaceProgram.block.manufacturing.entity;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.block.NSPBlocks;
import com.nythicalnorm.nythicalSpaceProgram.block.gse.entity.VehicleAssemblerEntity;
import com.nythicalnorm.nythicalSpaceProgram.block.gse.entity.PlatformAssemblyEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NSPBlockEntities {
     public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
             .create(ForgeRegistries.BLOCK_ENTITY_TYPES, NythicalSpaceProgram.MODID);

     public static final RegistryObject<BlockEntityType<MagnetizerEntity>> MAGNETIZER_BE =
             BLOCK_ENTITIES.register("magnetizer_be", () ->
                     BlockEntityType.Builder.of(MagnetizerEntity::new, NSPBlocks.MAGNETIZER.get()).build(null));

    public static final RegistryObject<BlockEntityType<CryogenicAirSeparatorEntity>> CRYOGENIC_AIR_SEPARATOR_BE =
            BLOCK_ENTITIES.register("cryogenic_air_separator_be", () ->
                    BlockEntityType.Builder.of(CryogenicAirSeparatorEntity::new, NSPBlocks.CRYOGENIC_AIR_SEPARATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<VehicleAssemblerEntity>> VEHICLE_ASSEMBLER_BE =
            BLOCK_ENTITIES.register("vehicle_assembler_be", () ->
                    BlockEntityType.Builder.of(VehicleAssemblerEntity::new, NSPBlocks.VEHICLE_ASSEMBLER.get()).build(null));

    public static final RegistryObject<BlockEntityType<PlatformAssemblyEntity>> VEHICLE_ASSEMBLY_PLATFORM_BE =
            BLOCK_ENTITIES.register("vehicle_assembly_platform_be", () ->
                    BlockEntityType.Builder.of(PlatformAssemblyEntity::new, NSPBlocks.VEHICLE_ASSEMBLY_PLATFORM.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
     }
}
