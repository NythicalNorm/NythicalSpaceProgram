package com.nythicalnorm.nythicalSpaceProgram.planetshine.map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.nythicalnorm.nythicalSpaceProgram.orbit.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class OrbitDrawer {
    private static VertexBuffer circleBuffer;
    private static VertexBuffer hyperbolaBuffer;

    public static void generateCircle(int segments) {
        circleBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        for (int i = 0; i < segments; i++) {
            float angleAround = (float) i/(segments);
            float angleAroundNext = (float) (i+1f)/(segments);

            angleAround = angleAround * Mth.TWO_PI;
            angleAroundNext = angleAroundNext * Mth.TWO_PI;

            bufferbuilder.vertex(Math.cos(angleAround), 0f, Math.sin(angleAround)).color(1.0f,1.0f,1.0f,1.0f).endVertex();
            bufferbuilder.vertex(Math.cos(angleAroundNext), 0f, Math.sin(angleAroundNext)).color(1.0f,1.0f,1.0f,1.0f).endVertex();
        }

        circleBuffer.bind();
        circleBuffer.upload(bufferbuilder.end());
        VertexBuffer.unbind();
    }

    public static void generateHyperbola(int segments) {
        hyperbolaBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        for (int i = 0; i < segments; i++) {
            float angleAround = (float) i/(segments);
            float angleAroundNext = (float) (i+1f)/(segments);
        }

        hyperbolaBuffer.bind();
        hyperbolaBuffer.upload(bufferbuilder.end());
        VertexBuffer.unbind();
    }

    public static void drawOrbit(Orbit orbitalBody, float scaleFactor, PoseStack poseStack, Matrix4f projectionMatrix) {
        OrbitalElements orbitalElements = orbitalBody.getOrbitalElements();
        VertexBuffer drawBuffer = (orbitalElements.Eccentricity > 0 && orbitalElements.Eccentricity < 1) ? circleBuffer : hyperbolaBuffer;

        double a = orbitalElements.SemiMajorAxis;
        double b = a*Math.sqrt(1-(orbitalElements.Eccentricity*orbitalElements.Eccentricity));
        a = a*scaleFactor;
        b = b*scaleFactor;
        float distanceFromCenterToFoci = (float) Math.sqrt(a*a - b*b);

        poseStack.pushPose();
        Quaternionf orbitRotations = new Quaternionf();
        orbitRotations.rotateY((float) -orbitalElements.LongitudeOfAscendingNode);
        orbitRotations.rotateX((float) -orbitalElements.Inclination);
        orbitRotations.rotateY((float) -orbitalElements.ArgumentOfPeriapsis);
        poseStack.mulPose(orbitRotations);

        poseStack.translate(-distanceFromCenterToFoci, 0f, 0f);
        poseStack.scale((float) a,1f,(float) b);

        if (orbitalBody instanceof EntityOrbitalBody) {
            RenderSystem.setShaderColor(0.0f,0.0f,1.0f,1.0f);
        } else {
            RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        }

        drawBuffer.bind();
        drawBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionColorShader());
        VertexBuffer.unbind();
        poseStack.popPose();
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
    }
}
