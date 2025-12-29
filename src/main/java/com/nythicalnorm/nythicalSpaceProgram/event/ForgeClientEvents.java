package com.nythicalnorm.nythicalSpaceProgram.event;

import com.nythicalnorm.nythicalSpaceProgram.Item.ModItems;
import com.nythicalnorm.nythicalSpaceProgram.Item.armor.jetpack.Jetpack;
import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.dimensions.SpaceDimension;
import com.nythicalnorm.nythicalSpaceProgram.gui.screen.PlayerSpacecraftScreen;
import com.nythicalnorm.nythicalSpaceProgram.CelestialStateSupplier;
import com.nythicalnorm.nythicalSpaceProgram.gui.screen.MapSolarSystemScreen;
import com.nythicalnorm.nythicalSpaceProgram.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = NythicalSpaceProgram.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void OnKeyInput (InputEvent.Key event) {
        if (KeyBindings.INC_TIME_WARP_KEY.consumeClick()) {
            NythicalSpaceProgram.getCelestialStateSupplier().ifPresent((celestialStateSupplier ->
                    celestialStateSupplier.TryChangeTimeWarp(true)));
        } else if (KeyBindings.DEC_TIME_WARP_KEY.consumeClick()) {
            NythicalSpaceProgram.getCelestialStateSupplier().ifPresent((celestialStateSupplier ->
                    celestialStateSupplier.TryChangeTimeWarp(false)));
        } else if (KeyBindings.OPEN_SOLAR_SYSTEM_MAP_KEY.consumeClick()) {
            NythicalSpaceProgram.getCelestialStateSupplier().ifPresent(celestialStateSupplier -> {
                if (celestialStateSupplier.doRender()) {
                    Minecraft.getInstance().setScreen(new MapSolarSystemScreen(false));
                }
            });
        }
        else if (KeyBindings.USE_PLAYER_JETPACK_KEY.consumeClick()) {
            LocalPlayer player = Minecraft.getInstance().player;
            ItemStack chestplateItem = player.getSlot(102).get();

            if (chestplateItem.getItem() instanceof Jetpack) {
                NythicalSpaceProgram.getCelestialStateSupplier().ifPresent(celestialStateSupplier -> {
                    if (celestialStateSupplier.doRender()) {
                        Minecraft.getInstance().setScreen(new PlayerSpacecraftScreen(chestplateItem, player, celestialStateSupplier));
                        celestialStateSupplier.setControllingBody(celestialStateSupplier.getPlayerOrbit());
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void postPlayerRender(RenderPlayerEvent.Pre event) {
        PlayerModel<AbstractClientPlayer> playerModel = event.getRenderer().getModel();

        if (event.getEntity().getSlot(102).get().is(ModItems.CREATIVE_SPACESUIT_CHESTPLATE.get())) {
            playerModel.leftArm.visible = false;
            playerModel.rightArm.visible = false;
            playerModel.leftSleeve.visible = false;
            playerModel.rightSleeve.visible = false;
        }
    }

    @SubscribeEvent
    public static void clientTickEvent(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            NythicalSpaceProgram.getCelestialStateSupplier().ifPresent(CelestialStateSupplier::tick);
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(ClientPlayerNetworkEvent.Clone event) {
        NythicalSpaceProgram.getCelestialStateSupplier().ifPresent(css -> {
            if (css.getPlayerOrbit() != null) {
                css.getPlayerOrbit().setPlayerEntity(event.getNewPlayer());
            }

            if (event.getNewPlayer().level().dimension() != SpaceDimension.SPACE_LEVEL_KEY) {
                css.getPlayerOrbit().removeYourself();
            }
        });
    }
}
