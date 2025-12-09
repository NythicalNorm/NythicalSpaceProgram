package com.nythicalnorm.nythicalSpaceProgram.planetshine.map;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class RenderableIcon {
    private final int[] screenPos;
    private final ResourceLocation texture;

    public RenderableIcon(int[] screenPos, ResourceLocation texture) {
        this.screenPos = screenPos;
        this.texture = texture;
    }

    public void render(GuiGraphics graphics, float size) {
        float relativeHeadSize = size/8;

        graphics.blit(texture, (int) (screenPos[0] - relativeHeadSize*0.5f), (int) (screenPos[1] - relativeHeadSize*0.5f), (int) relativeHeadSize,
                (int) relativeHeadSize,(int) relativeHeadSize, (int) relativeHeadSize,  (int) size, (int) size);
    }
}
