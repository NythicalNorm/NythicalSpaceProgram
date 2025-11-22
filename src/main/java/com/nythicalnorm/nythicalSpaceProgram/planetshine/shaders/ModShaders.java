package com.nythicalnorm.nythicalSpaceProgram.planetshine.shaders;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

// function is from  @gigaherz on github
public class ModShaders {
//
//    public static RenderType planetShader(ResourceLocation texture)
//    {
//        return CustomRenderTypes.PLANET.apply(texture);
//    }

    public static void setPlanetShaderInstance(ShaderInstance planet){
        CustomRenderTypes.planetShader = planet;
    }

    public static ShaderInstance getPlanetShaderInstance(){
        return CustomRenderTypes.planetShader;
    }

    // Keep private because this stuff isn't meant to be public
    private static class CustomRenderTypes extends RenderType
    {
        // Holds the object loaded via RegisterShadersEvent
        private static ShaderInstance planetShader;

        // Shader state for use in the render type, the supplier ensures it updates automatically with resource reloads
        private static final ShaderStateShard RENDERTYPE_PLANET_SHADER = new ShaderStateShard(() -> planetShader);

        // Dummy constructor needed to make java happy
        private CustomRenderTypes(String s, VertexFormat v, VertexFormat.Mode m, int i, boolean b, boolean b2, Runnable r, Runnable r2)
        {
            super(s, v, m, i, b, b2, r, r2);
            throw new IllegalStateException("This class is not meant to be constructed!");
        }

        // The memoize caches the output value for each input, meaning the expensive registration process doesn't have to rerun
        public static Function<ResourceLocation, RenderType> PLANET = Util.memoize(CustomRenderTypes::planet);

        // Defines the RenderType. Make sure the name is unique by including your MODID in the name.
        private static RenderType planet(ResourceLocation locationIn)
        {
            RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_PLANET_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(locationIn, false, false))
                    .setTransparencyState(NO_TRANSPARENCY)
                    .setLightmapState(NO_LIGHTMAP)
                    .setOverlayState(NO_OVERLAY)
                    .createCompositeState(true);
            return create("nythicalspaceprogram_planet", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, rendertype$state);
        }
    }
}
