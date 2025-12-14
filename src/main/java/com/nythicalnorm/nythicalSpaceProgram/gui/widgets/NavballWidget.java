package com.nythicalnorm.nythicalSpaceProgram.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class NavballWidget extends AbstractWidget {
    private static final ResourceLocation NAVBALL_GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(NythicalSpaceProgram.MODID,
            "textures/gui/navballwidget.png");

    public NavballWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, NAVBALL_GUI_TEXTURE);

        pGuiGraphics.blit(NAVBALL_GUI_TEXTURE, getX() - 48, getY() - 86,0,0,94,86);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
