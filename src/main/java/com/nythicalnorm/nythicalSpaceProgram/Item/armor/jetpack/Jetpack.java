package com.nythicalnorm.nythicalSpaceProgram.Item.armor.jetpack;

import com.nythicalnorm.nythicalSpaceProgram.Item.armor.ModArmorMaterial;
import com.nythicalnorm.nythicalSpaceProgram.Item.armor.SpacesuitModelModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class Jetpack extends ArmorItem {

    public Jetpack(Properties pProperties) {
        super(ModArmorMaterial.SPACESUIT, Type.CHESTPLATE, pProperties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new SpacesuitModelModifier());
    }
}
