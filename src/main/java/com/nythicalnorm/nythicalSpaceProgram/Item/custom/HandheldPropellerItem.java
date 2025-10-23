package com.nythicalnorm.nythicalSpaceProgram.Item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class HandheldPropellerItem extends Item {
    public HandheldPropellerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {
            pPlayer.getCooldowns().addCooldown(this, 8);
            Vec3 force = pPlayer.getLookAngle().normalize();
            force = force.scale(1);
            pPlayer.setDeltaMovement(pPlayer.getDeltaMovement().add(force));
            ((ServerPlayer)pPlayer).connection.send(new ClientboundSetEntityMotionPacket(pPlayer));

            //pPlayer.sendSystemMessage(Component.literal("X Rot: " + force.x + " Y Rot: " + force.y));
        }
       return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
    // state.is(ModTags.MAGNETIC_METALS);
}
