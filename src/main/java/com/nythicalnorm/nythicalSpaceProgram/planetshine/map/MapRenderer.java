package com.nythicalnorm.nythicalSpaceProgram.planetshine.map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.orbit.ClientPlayerSpacecraftBody;
import com.nythicalnorm.nythicalSpaceProgram.orbit.EntityOrbitalBody;
import com.nythicalnorm.nythicalSpaceProgram.orbit.Orbit;
import com.nythicalnorm.nythicalSpaceProgram.orbit.PlanetaryBody;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.CelestialStateSupplier;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.PlanetShine;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.renderers.RenderableObjects;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.renderers.AtmosphereRenderer;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.renderers.PlanetRenderer;
import com.nythicalnorm.nythicalSpaceProgram.planetshine.renderers.SpaceObjRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.*;

import java.lang.Math;
import java.util.Collection;
import java.util.Optional;

public class MapRenderer {
    private static final float SCALE_FACTOR = 1/1000000000f;
    private static VertexBuffer arrow = new VertexBuffer(VertexBuffer.Usage.DYNAMIC);
    private static ResourceLocation playerHeadTexture;

    public static void init() {
        playerHeadTexture = Minecraft.getInstance().player.getSkinTextureLocation();
        OrbitDrawer.generateCircle(2048);
        OrbitDrawer.generateHyperbola(2048);
    }

    public static void renderSkybox(PoseStack mapPosestack, Matrix4f projectionMatrix) {
        AtmosphereRenderer.renderSpaceSky(mapPosestack, projectionMatrix);
        PlanetShine.drawStarBuffer(mapPosestack, projectionMatrix, 1.0f);
    }

    public static void renderMapObjects(MapSolarSystem solScreen, PoseStack poseStack, Matrix4f projectionMatrix, Vector3d cameraPos, Orbit currentFocus, CelestialStateSupplier css) {
        RenderableObjects[] renderableObjects = SpaceObjRenderer.getRenderPlanets();

        PlanetaryBody planetRelativeTo = null;
        if (currentFocus instanceof PlanetaryBody) {
            planetRelativeTo = (PlanetaryBody) currentFocus;
        } else if (currentFocus instanceof ClientPlayerSpacecraftBody) {
            planetRelativeTo = css.getCurrentPlanetSOIin().get();
            cameraPos.add(currentFocus.getRelativePos());
        }

        if (planetRelativeTo == null) {
            NythicalSpaceProgram.logError("Can't find valid planet to open focus on");
        }

        Vector3f mapCameraPos = toMapCoordinate(cameraPos);
        poseStack.translate(-mapCameraPos.x, -mapCameraPos.y, -mapCameraPos.z);

        for (RenderableObjects plnt : renderableObjects) {
            Vector3f renderPos;

            if (plnt.getBody() == planetRelativeTo) {
                renderPos = new Vector3f(0f,0f,0f);
            } else if (planetRelativeTo.hasChild(plnt.getBody())) {
                renderPos = toMapCoordinate(plnt.getBody().getRelativePos());
            } else {
                Vector3d differenceVector = plnt.getBody().getAbsolutePos();
                differenceVector.sub(planetRelativeTo.getAbsolutePos());
                renderPos = toMapCoordinate(differenceVector);
            }
            poseStack.pushPose();
            poseStack.translate(renderPos.x, renderPos.y, renderPos.z);
            // all drawing that is relative to a planet occurs here.
            //test Velocity direction Lines
            //drawDebugVelocityLines(plnt.getBody().getRelativeVelocity().normalize(), poseStack, projectionMatrix);
            //draws the icon of all the child spacecraft
            drawHomePlayerIcon(solScreen, css, plnt.getBody(), poseStack, projectionMatrix);
            drawInPlanetChildren(solScreen, css, plnt.getBody(), poseStack, projectionMatrix);

            renderPlanetAt(plnt, poseStack, projectionMatrix);
            poseStack.popPose();
        }

    }

    private static void renderPlanetAt(RenderableObjects plnt, PoseStack poseStack, Matrix4f projectionMatrix) {
        float PlanetSize = (float) (2f*SCALE_FACTOR*plnt.getBody().getRadius());
        poseStack.scale(PlanetSize, PlanetSize, PlanetSize);
        poseStack.mulPose(plnt.getBody().getRotation());

        PlanetRenderer.render(plnt,false, Optional.empty(), poseStack, projectionMatrix, 0, 1.0f);
    }

    //draw test velocity lines
    private static void drawDebugVelocityLines(Vector3d dir, PoseStack pose, Matrix4f projectionMatrix) {
        Vector3f smallDir = new Vector3f((float) dir.x,(float) dir.y,(float) dir.z);
        smallDir.mul(0.1f);

        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(0f,0f,0f).color(255,0,0,255).endVertex();
        bufferbuilder.vertex(smallDir.x,smallDir.y,smallDir.z).color(255,0,0,255).endVertex();
        arrow.bind();
        arrow.upload(bufferbuilder.end());
        arrow.drawWithShader(pose.last().pose(), projectionMatrix, GameRenderer.getPositionColorShader());
        VertexBuffer.unbind();
    }

    private static void drawInPlanetChildren(MapSolarSystem solscreen, CelestialStateSupplier css, PlanetaryBody planet, PoseStack poseStack, Matrix4f projectionMatrix) {
        Collection<Orbit> childOrbits = planet.getChildren();
        if (childOrbits == null) {
            return;
        }
        for (Orbit obt : childOrbits) {
            if (obt.getOrbitalElements() != null) {
                OrbitDrawer.drawOrbit(obt, SCALE_FACTOR, poseStack, projectionMatrix);
            }
            if (obt instanceof EntityOrbitalBody) {
                int[] pixelCoords = worldToScreenCoordinate(toMapCoordinate(obt.getRelativePos()), poseStack, projectionMatrix, solscreen.width, solscreen.height);

                if (obt.equals(NythicalSpaceProgram.getCelestialStateSupplier().get().getPlayerData())) {
                    RenderableIcon playerHead = new RenderableIcon(pixelCoords, playerHeadTexture);
                    solscreen.addIconToRender(playerHead);
                } else { //draw other spacecraft icons here

                }
            }
        }
    }

    private static void drawHomePlayerIcon(MapSolarSystem solscreen, CelestialStateSupplier css, PlanetaryBody planet, PoseStack poseStack, Matrix4f projectionMatrix) {
        if (css.getCurrentPlanet().isPresent()) {
            if (css.getCurrentPlanet().get().equals(planet)) {
                int[] pixelCoords = worldToScreenCoordinate(toMapCoordinate(css.getPlayerData().getRelativePos()), poseStack, projectionMatrix, solscreen.width, solscreen.height);
                RenderableIcon playerHead = new RenderableIcon(pixelCoords, playerHeadTexture);
                solscreen.addIconToRender(playerHead);
            }
        }
    }

    private static int[] worldToScreenCoordinate(Vector3f pos, PoseStack poseStack,
                                                Matrix4f projectionMatrix, int width, int height) {
        Matrix4f clip_Pos = new Matrix4f(projectionMatrix).mul(new Matrix4f(poseStack.last().pose()));
        Vector4f clipVec = new Vector4f(pos.x, pos.y, pos.z, 1f).mul(clip_Pos);
        float x = clipVec.x/ clipVec.w;
        float y = -clipVec.y/ clipVec.w;
        float z = clipVec.z/ clipVec.w;

        int pixelX = (int) Math.round((x+1)*0.5f*width);
        int pixelY = (int) Math.floor((y+1)*0.5f*height);

        return new int[]{pixelX, pixelY};
    }

//    private static void renderPlayerHeadOnPlanet(Vector3f PlayerRelativePos, Vector3f cameraPos, PoseStack poseStack, Matrix4f projectionMatrix) {
//        poseStack.pushPose();
//        RenderSystem.disableDepthTest();
//
//        poseStack.translate(PlayerRelativePos.x, PlayerRelativePos.y, PlayerRelativePos.z);
//        poseStack.scale(0.001f, 0.001f, 0.001f);
//        poseStack.mulPose(new Quaternionf().lookAlong(cameraPos.x, cameraPos.y, cameraPos.z, 0f,1f,0f));
//
//        ResourceLocation playerTexture = Minecraft.getInstance().player.getSkinTextureLocation();
//        RenderSystem.setShaderTexture(0, playerTexture);
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//
//        playerHeadBillBoard.bind();
//        playerHeadBillBoard.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionTexShader());
//        VertexBuffer.unbind();
//
//        RenderSystem.enableDepthTest();
//        poseStack.popPose();
//    }

    public static Vector3f toMapCoordinate(Vector3d position) {
        position.mul(SCALE_FACTOR);
        return new Vector3f((float) position.x, (float) position.y, (float) position.z);
    }
}
