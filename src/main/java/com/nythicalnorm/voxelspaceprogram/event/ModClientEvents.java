package com.nythicalnorm.voxelspaceprogram.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.nythicalnorm.voxelspaceprogram.VoxelSpaceProgram;
import com.nythicalnorm.voxelspaceprogram.block.manufacturing.entity.NSPBlockEntities;
import com.nythicalnorm.voxelspaceprogram.block.manufacturing.entity.models.MagnetizerModels;
import com.nythicalnorm.voxelspaceprogram.block.manufacturing.entity.renderer.MagnetizerBlockEntityRenderer;
import com.nythicalnorm.voxelspaceprogram.block.rocket_parts.entity.models.EngineModelData;
import com.nythicalnorm.voxelspaceprogram.block.rocket_parts.renderer.EngineEntityRenderer;
import com.nythicalnorm.voxelspaceprogram.planetshine.generators.QuadSphereModelGenerator;
import com.nythicalnorm.voxelspaceprogram.planetshine.PlanetShine;
import com.nythicalnorm.voxelspaceprogram.planetshine.shaders.NSPShaders;
import com.nythicalnorm.voxelspaceprogram.util.KeyBindings;
import net.minecraft.Util;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = VoxelSpaceProgram.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void RegisterBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(NSPBlockEntities.MAGNETIZER_BE.get(), MagnetizerBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(NSPBlockEntities.ENGINE_BE.get(), EngineEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MagnetizerModels.LAYER_LOCATION, MagnetizerModels::createBodyLayer);
        event.registerLayerDefinition(EngineModelData.ThreeKeroloxLayerLoc, EngineModelData::createThreeKeroloxModel);
        event.registerLayerDefinition(EngineModelData.TwoKeroloxLayerLoc, EngineModelData::createTwoKeroloxModel);
    }

    @SubscribeEvent
    public static void OnLevelRenderedStartEvent(RenderLevelStageEvent.RegisterStageEvent event) {
        VoxelSpaceProgram.log("Baking Planet Models: ");
        long  beforeTimes = Util.getMillis();
        QuadSphereModelGenerator.setupModels();
        PlanetShine.setupBuffers();
        VoxelSpaceProgram.log("Setup Complete Took : " + (Util.getMillis()-beforeTimes) + " milliseconds");
    }

    @SubscribeEvent
    public static void OnKeyRegister (RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.INC_TIME_WARP_KEY);
        event.register(KeyBindings.DEC_TIME_WARP_KEY);
        event.register(KeyBindings.OPEN_SOLAR_SYSTEM_MAP_KEY);
        event.register(KeyBindings.USE_PLAYER_JETPACK_KEY);

        event.register(KeyBindings.INCREASE_THROTTLE_KEY);
        event.register(KeyBindings.DECREASE_THROTTLE_KEY);
        event.register(KeyBindings.STAGING_KEY);
        event.register(KeyBindings.RCS_TOGGLE_KEY);
        event.register(KeyBindings.SAS_TOGGLE_KEY);
        event.register(KeyBindings.DOCKING_MODE_TOGGLE_KEY);
        event.register(KeyBindings.CLOCKWISE_SPIN_KEY);
        event.register(KeyBindings.ANTI_CLOCKWISE_SPIN_KEY);
    }

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException
    {
        // Adds a shader to the list, the callback runs when loading is complete.
        event.registerShader(new ShaderInstance(event.getResourceProvider(), VoxelSpaceProgram.rl(
                "voxelspaceprogram_planet"), DefaultVertexFormat.POSITION_TEX), NSPShaders::setPlanetShaderInstance);

        event.registerShader(new ShaderInstance(event.getResourceProvider(), VoxelSpaceProgram.rl(
                "voxelspaceprogram_skybox"), DefaultVertexFormat.POSITION_COLOR), NSPShaders::setSkyboxShaderInstance);
    }
}
