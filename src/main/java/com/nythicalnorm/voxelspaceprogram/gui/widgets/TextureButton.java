package com.nythicalnorm.voxelspaceprogram.gui.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TextureButton extends AbstractWidget {
    private ResourceLocation texture;
    private int textureU;
    private int textureV;
    private int highlightTextureU;
    private int highlightTextureV;
    private Runnable onClick;

    public TextureButton(ResourceLocation texture, int pX, int pY, int pWidth, int pHeight,  int texU,
                         int texV, int HighlightU, int HighlightV, Runnable clickEvent) {
        super(pX, pY, pWidth, pHeight, Component.empty());

        this.texture = texture;
        this.textureU = texU;
        this.textureV = texV;
        this.highlightTextureU = HighlightU;
        this.highlightTextureV = HighlightV;
        this.onClick = clickEvent;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.blit(texture, getX(), getY(), textureU, textureV, getWidth(), getHeight());
        if (isHovered()) {
            pGuiGraphics.blit(texture, getX() - 1, getY() - 1, highlightTextureU, highlightTextureV, getWidth() + 2, getHeight() + 2);
        }
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        onClick.run();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
