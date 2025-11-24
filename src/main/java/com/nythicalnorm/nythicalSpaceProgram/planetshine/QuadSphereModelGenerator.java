package com.nythicalnorm.nythicalSpaceProgram.planetshine;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class QuadSphereModelGenerator {
    private static final float textureboundingboxU1 = 0;
    private static final float textureboundingboxU2 = 1;
    private static final float textureboundingboxV1 = 0;
    private static final float textureboundingboxV2 = 1;
    private static final float radius = 0.5f;
    private static final Vector3d modelOffset = new Vector3d(0.0,0.0,0.0);

    public static List<BakedQuad> getsphereQuads() {
        List<BakedQuad> quads = new ArrayList<>();
        int QuadsPerSide = 32;

        int MaxPerSide = QuadsPerSide / 2;

        for (int squareSide = 0; squareSide < 6; squareSide++) {
            for (int sidesUpIter = -MaxPerSide; sidesUpIter < MaxPerSide; sidesUpIter++) {
                for (int sidesRightIter = -MaxPerSide; sidesRightIter < MaxPerSide; sidesRightIter++) {
                    BakedQuad quad0 = quad(getSquarePos(sidesUpIter, sidesRightIter, MaxPerSide, squareSide),
                            getSquarePos(sidesUpIter, sidesRightIter + 1, MaxPerSide, squareSide),
                            getSquarePos(sidesUpIter + 1, sidesRightIter + 1, MaxPerSide, squareSide),
                            getSquarePos(sidesUpIter + 1, sidesRightIter, MaxPerSide, squareSide),
                            sidesUpIter, sidesRightIter, QuadsPerSide);
                    quads.add(quad0);
                }
            }
        }
        return quads;
    }

    private static Vector3f getSquarePos(float sidesUpIter, float sidesRightIter, float MaxPerSide, int squareSide) {
        float sidesrightP = sidesRightIter/MaxPerSide;
        float sidesupP = sidesUpIter/MaxPerSide;
        Vector3f squarePos = new Vector3f();

        squarePos = switch (squareSide) {
            case 0 -> new Vector3f(sidesrightP, sidesupP, 1f);
            case 1 -> new Vector3f(1f, sidesupP, -sidesrightP);
            case 2 -> new Vector3f(-sidesrightP, sidesupP, -1);
            case 3 -> new Vector3f(-1f, sidesupP, sidesrightP);
            case 4 -> new Vector3f(-sidesrightP, 1f, sidesupP);
            case 5 -> new Vector3f(sidesrightP, -1f, sidesupP);
            default -> squarePos;
        };
        squarePos.normalize();
        squarePos.mul(radius);
        return squarePos;
    }

    public static BakedQuad quad(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4,
                                 float sidesUpIter, float sidesRightIter, float QuadsPerSide) {
        BakedQuad[] quad = new BakedQuad[1];
        QuadBakingVertexConsumer builder = new QuadBakingVertexConsumer(q -> quad[0] = q);

        float usize = textureboundingboxU2 - textureboundingboxU1;
        float vsize = textureboundingboxV2 - textureboundingboxV1;
        sidesUpIter =  sidesUpIter + QuadsPerSide/2;
        sidesRightIter = sidesRightIter + QuadsPerSide/2;

        putVertex(builder, v1.x, v1.y, v1.z, (sidesRightIter/QuadsPerSide), (sidesUpIter/QuadsPerSide), usize, vsize);
        putVertex(builder, v2.x, v2.y, v2.z, ((sidesRightIter + 1)/QuadsPerSide), (sidesUpIter/QuadsPerSide), usize, vsize);
        putVertex(builder, v3.x, v3.y, v3.z, ((sidesRightIter + 1)/QuadsPerSide), ((sidesUpIter + 1)/QuadsPerSide), usize, vsize);
        putVertex(builder, v4.x, v4.y, v4.z, (sidesRightIter/QuadsPerSide), ((sidesUpIter + 1)/QuadsPerSide), usize, vsize);
        return quad[0];
    }

    private static void putVertex(VertexConsumer builder,
                                  double x, double y, double z, float u1, float v1, //,float u2, float v2,
                                  float usize, float vsize) {
        float iu = textureboundingboxU1 + (usize*u1);
        float iv = textureboundingboxV2 - (vsize*v1);
        builder.vertex(x + modelOffset.x, y + modelOffset.y, z + modelOffset.z)
                .uv(iu, iv)
                .endVertex();
    }
}
