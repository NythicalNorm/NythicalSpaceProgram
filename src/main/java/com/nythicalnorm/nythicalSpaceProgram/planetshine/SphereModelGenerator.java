package com.nythicalnorm.nythicalSpaceProgram.planetshine;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SphereModelGenerator {
    private static final float textureboundingboxU1 = 0;
    private static final float textureboundingboxU2 = 1;
    private static final float textureboundingboxV1 = 0;
    private static final float textureboundingboxV2 = 1;
    private static final float radius = 0.5f;
    private static final Vec3 modelOffset = new Vec3(0.5,0.5,0.5);

    public static List<BakedQuad> getsphereQuads() {
        List<BakedQuad> quads = new ArrayList<>();

        int MaxLatitude = 64;
        int MaxLongitude = 2 * MaxLatitude;

        for (int latitudeIter = 0; latitudeIter < MaxLatitude; latitudeIter++) {
            for (int longitudeIter = 0; longitudeIter < MaxLongitude; longitudeIter++) {
                BakedQuad  quad0 = quad(getCartesian(latitudeIter,longitudeIter,MaxLatitude, MaxLongitude),
                                    getCartesian(latitudeIter,longitudeIter + 1,MaxLatitude, MaxLongitude),
                                    getCartesian(latitudeIter + 1,longitudeIter + 1,MaxLatitude, MaxLongitude),
                                    getCartesian(latitudeIter + 1,longitudeIter,MaxLatitude, MaxLongitude),
                        latitudeIter,longitudeIter,MaxLatitude, MaxLongitude);
                quads.add(quad0);
            }
        }
        return quads;
    }

    private static Vec3 getCartesian(int latitudeIter, int longitudeIter, int maxLatitude,  int maxLongitude)
    {
        double latAngle = Math.PI*latitudeIter/maxLatitude;
        double longAngle = (2*Math.PI*longitudeIter/maxLongitude) + Math.PI;

        double x = radius*Math.sin(latAngle)*Math.cos(longAngle);
        double y = radius*Math.cos(latAngle);
        double z = radius*Math.sin(latAngle)*Math.sin(longAngle);

        return new Vec3(x,y,z);
    }

    public static BakedQuad quad(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4,
                                 float latitudeIter, float longitudeIter, float maxLatitude,  float maxLongitude) {
        BakedQuad[] quad = new BakedQuad[1];
        QuadBakingVertexConsumer builder = new QuadBakingVertexConsumer(q -> quad[0] = q);

        float usize = textureboundingboxU1 - textureboundingboxU2;
        float vsize = textureboundingboxV2 - textureboundingboxV1;

        putVertex(builder, v1.x, v1.y, v1.z, (longitudeIter/maxLongitude), (latitudeIter/maxLatitude), usize, vsize);
        putVertex(builder, v2.x, v2.y, v2.z, ((longitudeIter + 1)/maxLongitude), (latitudeIter/maxLatitude), usize, vsize);
        putVertex(builder, v3.x, v3.y, v3.z, ((longitudeIter + 1)/maxLongitude), ((latitudeIter + 1)/maxLatitude), usize, vsize);
        putVertex(builder, v4.x, v4.y, v4.z, (longitudeIter/maxLongitude), ((latitudeIter + 1)/maxLatitude), usize, vsize);
        return quad[0];
    }

    private static void putVertex(VertexConsumer builder,
                                  double x, double y, double z, float u1, float v1, //,float u2, float v2,
                                  float usize, float vsize) {
        float iu = textureboundingboxU2 + (usize*u1);
        float iv = textureboundingboxV1 + (vsize*v1);
        builder.vertex(x + modelOffset.x, y + modelOffset.y, z + modelOffset.z)
                .uv(iu, iv)
                .endVertex();
    }
}
