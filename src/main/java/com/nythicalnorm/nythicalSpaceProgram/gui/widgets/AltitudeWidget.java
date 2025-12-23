package com.nythicalnorm.nythicalSpaceProgram.gui.widgets;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AltitudeWidget extends AbstractWidget {
    private static final ResourceLocation Altitude_GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(NythicalSpaceProgram.MODID,
            "textures/gui/altitudewidget.png");

    public AltitudeWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int x = getX() - 46;
        int y = getY();

        pGuiGraphics.blit(Altitude_GUI_TEXTURE, x, y,0,0,92,28);

        pGuiGraphics.blit(Altitude_GUI_TEXTURE, x + 10, y + 15,8,30,5,13);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
