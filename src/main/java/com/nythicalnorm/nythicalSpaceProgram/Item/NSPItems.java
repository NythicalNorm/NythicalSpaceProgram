package com.nythicalnorm.nythicalSpaceProgram.Item;

import com.nythicalnorm.nythicalSpaceProgram.Item.armor.jetpack.CreativeJetpack;
import com.nythicalnorm.nythicalSpaceProgram.Item.armor.MagneticBoots;
import com.nythicalnorm.nythicalSpaceProgram.Item.armor.NSPArmorMaterial;
import com.nythicalnorm.nythicalSpaceProgram.Item.custom.HandheldPropellerItem;
import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NSPItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NythicalSpaceProgram.MODID);

    public static final RegistryObject<Item> HANDHELD_PROPELLER = ITEMS.register("handheld_propeller",
            () -> new HandheldPropellerItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MAGNET_BOOTS = ITEMS.register("magnet_boots", () ->
            new MagneticBoots(NSPArmorMaterial.MAGNETIC, ArmorItem.Type.BOOTS,  new Item.Properties()));


    public static final RegistryObject<Item> SPACESUIT_HELMET =  ITEMS.register("spacesuit_helmet", () ->
            new ArmorItem(NSPArmorMaterial.SPACESUIT, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> CREATIVE_SPACESUIT_CHESTPLATE =  ITEMS.register("creative_spacesuit_chestplate", () ->
            new CreativeJetpack(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SPACESUIT_LEGGINGS =  ITEMS.register("spacesuit_leggings", () ->
            new ArmorItem(NSPArmorMaterial.SPACESUIT, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> SPACESUIT_BOOTS =  ITEMS.register("spacesuit_boots", () ->
            new ArmorItem(NSPArmorMaterial.SPACESUIT, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> MAGNETIZED_IRON_INGOT = ITEMS.register("magnetized_iron_ingot", () ->
            new Item(new Item.Properties().fireResistant()));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
