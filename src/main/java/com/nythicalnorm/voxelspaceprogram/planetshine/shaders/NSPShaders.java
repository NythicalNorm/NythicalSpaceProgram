package com.nythicalnorm.voxelspaceprogram.planetshine.shaders;

import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class NSPShaders {
    private static ShaderInstance PLANETSHADER;
    private static ShaderInstance SKYBOXSHADER;

    private static Supplier<ShaderInstance> SKYBOXSHADERSUPPLIER;
    private static Supplier<ShaderInstance> PLANETSHADERSUPPLIER;

    public static void setPlanetShaderInstance(ShaderInstance planet){
        PLANETSHADER = planet;
        PLANETSHADERSUPPLIER = () -> PLANETSHADER;
    }

    public static void setSkyboxShaderInstance(ShaderInstance shad){
        SKYBOXSHADER = shad;
        SKYBOXSHADERSUPPLIER = () -> SKYBOXSHADER;
    }

    public static Supplier<ShaderInstance> getPlanetShaderInstance(){
        return PLANETSHADERSUPPLIER;
    }

    public static Supplier<ShaderInstance> getSkyboxShaderInstance(){
        return SKYBOXSHADERSUPPLIER;
    }
}
