package com.nythicalnorm.nythicalSpaceProgram.gui;

import com.nythicalnorm.nythicalSpaceProgram.gui.widgets.NavballWidget;
import com.nythicalnorm.nythicalSpaceProgram.gui.widgets.TimeWarpWidget;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.CelestialStateSupplier;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.map.MapSolarSystem;
import com.nythicalnorm.nythicalSpaceProgram.util.KeyBindings;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerSpacecraftScreen extends MouseLookScreen {
    ItemStack jetpackItem;
    LocalPlayer player;
    CelestialStateSupplier css;
    Options minecraftOptions;
    float initialYLookDir;

    public PlayerSpacecraftScreen(ItemStack spacesuitItem, LocalPlayer player, CelestialStateSupplier css) {
        super(Component.empty());
        this.jetpackItem = spacesuitItem;
        this.player = player;
        this.css = css;
        this.minecraftOptions = Minecraft.getInstance().options;
    }

    @Override
    protected void init() {
        this.addRenderableWidget(new TimeWarpWidget(0,0, width, height, Component.empty()));
        this.addRenderableWidget(new NavballWidget(width/2, height, width, height, Component.empty()));
        minecraftOptions.setCameraType(CameraType.THIRD_PERSON_BACK);
        minecraftOptions.hideGui = true;
        player.setXRot(0f);
        zoomLevel = 4f;

        initialYLookDir = player.getViewYRot(0f);
        cameraYrot = (float) -Math.toRadians(initialYLookDir);
        player.setYBodyRot(initialYLookDir);

        css.getScreenManager().setSpacecraftScreenOpen(true);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (KeyBindings.USE_PLAYER_JETPACK_KEY.matches(pKeyCode, pScanCode)) {
            this.onClose();
            return true;
        } else if (KeyBindings.OPEN_SOLAR_SYSTEM_MAP_KEY.matches(pKeyCode, pScanCode)) {
            Minecraft.getInstance().setScreen(new MapSolarSystem(this));
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    public void onClose() {
        css.getScreenManager().closeSpacecraftScreen();
        super.onClose();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        player.setYBodyRot(initialYLookDir);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public float getViewYrot() {
        return -cameraYrot*57.29577951308232f;
    }

    public float getViewXrot() {
        return -cameraXrot*57.29577951308232f;
    }
}
