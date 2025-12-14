package com.nythicalnorm.nythicalSpaceProgram.planetshine.map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.gui.MouseLookScreen;
import com.nythicalnorm.nythicalSpaceProgram.orbit.EntityOrbitalBody;
import com.nythicalnorm.nythicalSpaceProgram.orbit.Orbit;
import com.nythicalnorm.nythicalSpaceProgram.orbit.PlanetaryBody;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.CelestialStateSupplier;
import com.nythicalnorm.nythicalSpaceProgram.gui.widgets.TimeWarpWidget;
import com.nythicalnorm.nythicalSpaceProgram.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.*;
import org.lwjgl.glfw.GLFW;

import java.lang.Math;

@OnlyIn(Dist.CLIENT)
public class MapSolarSystem extends MouseLookScreen {
    private CelestialStateSupplier css;
    private Orbit[] FocusableBodies;
    private int currentFocusedBodyIndex;
    private final Screen lastScreen;

    public MapSolarSystem(@Nullable Screen lastScreen) {
        super(Component.empty());
        NythicalSpaceProgram.getCelestialStateSupplier().ifPresent (celestialStateSupplier -> {
            css = celestialStateSupplier;
            celestialStateSupplier.getScreenManager().setMapScreenOpen(true);
        });
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        populateFocusedBodiesList();
        MapRenderer.setScreen(this);
        this.addRenderableWidget(new TimeWarpWidget(0,0, width, height, Component.empty()));
        super.init();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.depthMask(false);
        PoseStack mapPosestack = new PoseStack();
        Matrix4f projectionMatrix = new Matrix4f().setPerspective(70, (float) graphics.guiWidth()/graphics.guiHeight(), 0.0000001f, 100.0f);

        Quaternionf dragCameraRot = new Quaternionf().rotateYXZ(cameraYrot, cameraXrot, 0f); //.mul(yRotQuaternion);
        Vector3d relativeCameraPos = new Vector3d(0d, 0d, zoomLevel * radiusZoomLevel);
        relativeCameraPos.rotate(new Quaterniond(dragCameraRot.x, dragCameraRot.y,dragCameraRot.z,dragCameraRot.w));
        //Vector3d absoluteCameraPos = currentFocusedBody.getAbsolutePos().add(relativeCameraPos);

        mapPosestack.pushPose();
        mapPosestack.mulPose(dragCameraRot.conjugate());

        MapRenderer.renderSkybox(mapPosestack, projectionMatrix);

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        MapRenderer.renderMapObjects(graphics, mapPosestack, projectionMatrix, relativeCameraPos, FocusableBodies[currentFocusedBodyIndex], css);
        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();
        mapPosestack.popPose();

        if (!mapPosestack.clear()) {
            throw new IllegalStateException("popped poses are not closed properly.");
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (KeyBindings.OPEN_SOLAR_SYSTEM_MAP_KEY.matches(pKeyCode, pScanCode)) {
            this.onClose();
            return true;
        }

        else if (GLFW.GLFW_KEY_TAB == pKeyCode){
            changeFocusBody(1);
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public void onClose() {
        super.onClose();
        if (lastScreen != null) {
            Minecraft.getInstance().setScreen(lastScreen);
        }
        css.getScreenManager().closeMapScreen();
    }

    public void changeFocusBody(int additionalIndex) {
        currentFocusedBodyIndex += additionalIndex;
        if (currentFocusedBodyIndex >= FocusableBodies.length) {
            currentFocusedBodyIndex = 0;
        }
        if (FocusableBodies[currentFocusedBodyIndex] instanceof PlanetaryBody) {
            if (css.getCurrentPlanet().isPresent()) {
                if (css.getCurrentPlanet().get().equals(FocusableBodies[currentFocusedBodyIndex])) {
                    Vector3d playerRelativePos = css.getPlayerOrbit().getRelativePos();
                    playerRelativePos.normalize();
                    cameraYrot = (float) Math.atan2(playerRelativePos.x,playerRelativePos.z);
                    cameraXrot = (float) Math.asin(playerRelativePos.y);
                }
            }
            radiusZoomLevel = ((PlanetaryBody) FocusableBodies[currentFocusedBodyIndex]).getRadius();
        } else if (FocusableBodies[currentFocusedBodyIndex] instanceof EntityOrbitalBody) {
            radiusZoomLevel = 1000000;
        }

        MapRenderer.updateMapRenderables(css, FocusableBodies[currentFocusedBodyIndex]);
    }

    private void populateFocusedBodiesList() {
        Orbit currentFocusedBody = null;

        if (css.isOnPlanet()) {
            currentFocusedBody = css.getCurrentPlanet().get();

        } else if (css.getCurrentPlanetSOIin().isPresent() && css.getPlayerOrbit() != null) {
            currentFocusedBody = css.getPlayerOrbit();
        }

        int totalFocusAmount = css.getPlanets().allPlanetsAddresses.size();
        if (currentFocusedBody instanceof EntityOrbitalBody) {
            totalFocusAmount += 1;
        }

        //setting the first element to the desired body and later filling in planets that aren't currentfocusedbody
        int index = 0;

        FocusableBodies = new Orbit[totalFocusAmount];
        FocusableBodies[index] = currentFocusedBody;
        currentFocusedBodyIndex = index;

        for (PlanetaryBody plnt : css.getPlanets().getAllPlanetOrbits()) {
            if (plnt != currentFocusedBody) {
                index++;
                FocusableBodies[index] = plnt;
            }
        }
        changeFocusBody(0);
    }
}
