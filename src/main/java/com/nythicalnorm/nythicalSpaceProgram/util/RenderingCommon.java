package com.nythicalnorm.nythicalSpaceProgram.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

@OnlyIn(Dist.CLIENT)
public class RenderingCommon {
    public static int[] worldToScreenCoordinate(Vector3f pos, PoseStack poseStack,
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
}
