package com.nythicalnorm.nythicalSpaceProgram.planetshine;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.*;

import java.lang.Math;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PlanetRenderer {
    private static final VertexBuffer planetvertex = new VertexBuffer(VertexBuffer.Usage.STATIC);
    private static final ResourceLocation Nila_texture =  ResourceLocation.parse("nythicalspaceprogram:textures/planets/moon_axis.png");
    private static final float InWorldPlanetsDistance = 64f;

    public static void setupModels() {
        PoseStack spherePose = new PoseStack();
        spherePose.setIdentity();
        List<BakedQuad> planetquads = SphereModelGenerator.getsphereQuads(); //planetModel.getQuads(null,null, RandomSource.create(), ModelData.builder().build(), RenderType.solid());
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        for(BakedQuad bakedquad : planetquads) {
            bufferbuilder.putBulkData(spherePose.last(), bakedquad, 1f, 1f, 1f, 10, 10);
        }
        planetvertex.bind();
        planetvertex.upload(bufferbuilder.end());
        VertexBuffer.unbind();

    }

    public static void renderPlanet(PoseStack poseStack, Minecraft mc, Camera camera, Matrix4f projectionMatrix) {
        poseStack.pushPose();
        //Vec3 PlanetPos = new Vec3(0,0,0); //CelestialStateSupplier.getPlanetPositon("nila", mc.getPartialTick());
        Vector3f RelativePlanetDir = new Vector3f(0,0,1);

        Matrix4f PlanetProjection = PerspectiveShift(projectionMatrix, (float) CelestialStateSupplier.lastUpdatedTimePassedPerSec,
                RelativePlanetDir, poseStack, camera);

        if (PlanetProjection != null) {
            planetvertex.bind();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, Nila_texture);
            ShaderInstance shad = GameRenderer.getPositionTexShader();

            planetvertex.drawWithShader(poseStack.last().pose(), PlanetProjection, shad);
            VertexBuffer.unbind();
        }
        poseStack.popPose();
    }

    private static Matrix4f PerspectiveShift(Matrix4f projectionMatrix, float PlanetDistance, Vector3f relativePlanetDir,
                                             PoseStack poseStack, Camera camera){
        Matrix4f returnMatrix = new Matrix4f(projectionMatrix);
        float PlanetAngularSize = (float) Math.atan(PlanetDistance);
        float theta = (float) (2f*Math.atan((1/returnMatrix.m11())));
        float screenAspectRatio = returnMatrix.m00()/returnMatrix.m11();
        float newVal = (float) Math.abs(1/Math.tan(PlanetAngularSize/2f));
        returnMatrix.m00(screenAspectRatio*newVal);
        returnMatrix.m11(newVal);

        poseStack.setIdentity();

        float PlanetAngularSizeTangent = (float) Math.tan(PlanetAngularSize/2);
        float thetaTangent = (float) Math.tan(theta/2);

        float ScalediffwithResize = Math.abs(PlanetAngularSizeTangent/thetaTangent); //(float) Math.abs(Math.tan(theta)/Math.tan(PlanetAngularSize));

        Vector2f planetRot = getAnglularComponents(relativePlanetDir.x,relativePlanetDir.y, relativePlanetDir.z);
        Vector3f camaraAngle = camera.getLookVector().normalize();
        Vector2f cameraRot = getAnglularComponents(camaraAngle.x,camaraAngle.y, camaraAngle.z);

        double diffRotX = getDiffAngle(cameraRot.x, planetRot.x) ;
        double diffRotY = getDiffAngle(cameraRot.y, planetRot.y) ;

        //removing the planet to stop it from wrapping around the other side when differenceBetweenRatio is too much
//        if (angleX.angle > Math.PI) {
//            return null;
//        }
        Vector3f CameraAngle = camera.getLookVector();
        Quaternionf rotationBetween = getRotationBetween(CameraAngle, relativePlanetDir);
        AxisAngle4f Diffangle = new AxisAngle4f(rotationBetween.normalize());
        float ogDiffAngle = Diffangle.angle;
//
//        Quaternionf rotationBetween = new Quaternionf();
//        float adjustedTheta = (float) Math.atan(Math.tan(theta)/screenAspectRatio);
//        float adjustedplanteAngle = (float) Math.atan(Math.tan(PlanetAngularSize)/screenAspectRatio);
//
//        float thetaHorizontal = (float) Math.tan(adjustedTheta/2);
//        float planteAngHorizontal = (float) Math.tan(adjustedplanteAngle/2);
//        float ScalediffHorizontal = (float) Math.abs(planteAngHorizontal/thetaHorizontal); //(float) Math.abs(Math.tan(theta)/Math.tan(PlanetAngularSize));
//
//        double AdjustedY =  -Math.atan(Math.tan(diffRotY)*ScalediffwithResize);
//        double AdjustedX = Math.atan(Math.tan(diffRotX)*ScalediffwithResize);
//
//        //double AdjustedY = -Math.tan(diffRotY/2)*ScalediffwithResize;
//        rotationBetween.mul(Axis.YP.rotation((float) (Math.PI + AdjustedY)));
//        rotationBetween.mul(Axis.XP.rotation((float) (AdjustedX)));

        Diffangle.angle = (float) Math.atan(Math.tan(ogDiffAngle)*ScalediffwithResize);
        rotationBetween = new Quaternionf(Diffangle);
        rotationBetween.mul(Axis.YP.rotation((float) (Math.PI)));
        rotationBetween.mul(Axis.YP.rotation((float) (2*Math.PI)));

        //poseStack.mulPose(rotationBetween);
        poseStack.rotateAround(rotationBetween,0,0,0);

        relativePlanetDir.mul(InWorldPlanetsDistance);
        poseStack.translate(relativePlanetDir.x, relativePlanetDir.y, relativePlanetDir.z);
        poseStack.scale(ScalediffwithResize,ScalediffwithResize,ScalediffwithResize);
        poseStack.scale(4,4,4);
        return returnMatrix;
    }

    private static Vector2f getAnglularComponents(float x, float y, float z) {
        Vector2f result = new Vector2f();
        result.y = (float) Math.atan2(x,z);
        result.x = (float) Math.asin(y);
        return result;
    }

    private static double getDiffAngle(double source,double dest) {
        if (source == Math.PI) {
            source = -Math.PI;
        }
        double n = source - dest;
//
//        if (n > Math.PI) {
//            n = n - Math.PI;
//            n = -n;
//        }
//        if (n < -Math.PI) {
//            n = Math.PI + (n + Math.PI);
//        }
        return n;
    }

    private static Quaternionf getRotationBetween(Vector3f u, Vector3f v)
    {
        Quaternionf q = new Quaternionf();
        Vector3f u1 = new Vector3f(u);
        Vector3f v1 = new Vector3f(v);

        Vector3f a = u1.cross(v1);
        q.x = a.x;
        q.y = a.y;
        q.z = a.z;

        q.w = 1 + u.dot(v);
        return q;
    }
}