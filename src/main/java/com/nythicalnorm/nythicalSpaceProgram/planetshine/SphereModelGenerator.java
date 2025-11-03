package com.nythicalnorm.nythicalSpaceProgram.planetshine;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer;

import java.util.ArrayList;
import java.util.List;

public class SphereModelGenerator {
    public static List<BakedQuad> getsphereQuads() {
        List<BakedQuad> quads = new ArrayList<>();
        //ResourceLocation LOCATION = ResourceLocation.parse("nythicalspaceprogram:textures/planets/moon_axis.png");
        TextureAtlasSprite spriteSide = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply( ResourceLocation.parse("entity/conduit/base")) ;
        quads.add(quad(v(0, 1, 1), v(1, 1, 1), v(1, 1, 0), v(0, 1, 0), spriteSide));
        quads.add(quad(v(0, 0, 0), v(1, 0, 0), v(1, 0, 1), v(0, 0, 1), spriteSide));
        quads.add(quad(v(1, 0, 0), v(1, 1, 0), v(1, 1, 1), v(1, 0, 1), spriteSide));
        quads.add(quad(v(0, 0, 1), v(0, 1, 1), v(0, 1, 0), v(0, 0, 0), spriteSide));
        quads.add(quad(v(0, 1, 0), v(1, 1, 0), v(1, 0, 0), v(0, 0, 0), spriteSide));
        quads.add(quad(v(0, 0, 1), v(1, 0, 1), v(1, 1, 1), v(0, 1, 1), spriteSide));
        return quads;
    }

    private static Vec3 v(int x, int y, int z) {
        return new Vec3(x, y, z);
    }

    public static BakedQuad quad(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4, TextureAtlasSprite sprite) {
        BakedQuad[] quad = new BakedQuad[1];
        QuadBakingVertexConsumer builder = new QuadBakingVertexConsumer(q -> quad[0] = q);
        builder.setSprite(sprite);
        //builder.setDirection(Direction.getNearest(normal.x, normal.y, normal.z));
        putVertex(builder, v1.x, v1.y, v1.z, 0, 0, sprite);
        putVertex(builder, v2.x, v2.y, v2.z, 0, 16, sprite);
        putVertex(builder, v3.x, v3.y, v3.z, 16, 16, sprite);
        putVertex(builder, v4.x, v4.y, v4.z, 16, 0, sprite);
        return quad[0];
    }

    private static void putVertex(VertexConsumer builder,
                                  double x, double y, double z, float u, float v,
                                  TextureAtlasSprite sprite) {
        float iu = sprite.getU(u);
        float iv = sprite.getV(v);
        builder.vertex(x, y, z)
                .uv(iu, iv)
                .uv2(0, 0)
                .endVertex();
    }
}
