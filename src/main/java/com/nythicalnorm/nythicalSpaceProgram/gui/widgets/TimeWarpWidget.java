package com.nythicalnorm.nythicalSpaceProgram.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.orbit.PlanetaryBody;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.networking.ClientTimeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.text.DecimalFormat;

public class TimeWarpWidget extends AbstractWidget {
    private static final ResourceLocation TIME_WARP_TEXTURE = ResourceLocation.fromNamespaceAndPath(NythicalSpaceProgram.MODID,
            "textures/gui/timewarpwidget.png");

    public TimeWarpWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, TIME_WARP_TEXTURE);

        pGuiGraphics.blit(TIME_WARP_TEXTURE, getX(), getY(),0,0,136,34);

        NythicalSpaceProgram.getCelestialStateSupplier().ifPresent(celestialStateSupplier -> {
            Component timeComp = parseTime(ClientTimeHandler.getClientSideSolarSystemTime(), celestialStateSupplier.getPlanets().BUMI);
            pGuiGraphics.drawString(Minecraft.getInstance().font, timeComp,13, 6, 0x00ff2b, false);

            int timeWarpSettingAmount = celestialStateSupplier.getTimeWarpSetting() + 1;
            int ArrowXVal = 15;

            for (int i = 0; i < timeWarpSettingAmount; i++) {
                pGuiGraphics.blit(TIME_WARP_TEXTURE, getX() + ArrowXVal, getY() + 20,15,34,6,9);
                ArrowXVal += 10;
            }
        });
    }

    private Component parseTime(Double currentTime, PlanetaryBody overworldPlanet) {
       double yearTime = (2*Math.PI)/overworldPlanet.getOrbitalElements().MeanAngularMotion;
       //this is not sidereal rotation period the values need to be changed and this calculation also needs to be changed.
       double dayTime = overworldPlanet.getRotationPeriod();
       double hourTime = dayTime/24;
       double minuteTime = hourTime/60;

       int year = (int) Math.floor(currentTime/yearTime) + 1;
       int remainderYear = (int) (currentTime%yearTime);
       int day = (int) Math.floor(remainderYear/dayTime);
       int remainderHour = (int) (remainderYear % dayTime);
       int hour = (int) Math.floor(remainderHour/hourTime);
       int remainderMinute = (int) (remainderHour % hourTime);
       int minute = (int) Math.floor(remainderMinute/minuteTime);

        DecimalFormat twodigits = new DecimalFormat("00");

        return Component.translatable("nythicalspaceprogram.mapscreen.time",
                year, day, twodigits.format(hour), twodigits.format(minute));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
    }
}
