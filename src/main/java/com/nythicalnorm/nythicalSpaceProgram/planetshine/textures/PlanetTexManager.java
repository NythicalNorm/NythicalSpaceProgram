package com.nythicalnorm.nythicalSpaceProgram.planetshine.textures;

import com.mojang.blaze3d.platform.NativeImage;
import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Optional;

public class PlanetTexManager {
    HashMap<String, ResourceLocation> planetResourceLocations;

    public PlanetTexManager() {
        this.planetResourceLocations = new HashMap<>();
    }

    public void incomingTexture(String planetName, byte[] tex) {
        NythicalSpaceProgram.log(planetName + " texture received, Size: " + tex.length);
        ByteBuffer bytebuffer = MemoryUtil.memAlloc(tex.length);

        try {
            NativeImage planetImage = NativeImage.read(bytebuffer.put(tex));
            DynamicTexture texture = new DynamicTexture(planetImage);
            TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
            ResourceLocation texResourceLocation = texturemanager.register("nythicalspaceprogram:textures/planets/" + planetName, texture);
            planetResourceLocations.put(planetName, texResourceLocation);
        } catch (IOException e) {
            NythicalSpaceProgram.logError("png texture can't be parsed");
        }
    }

    public Optional<ResourceLocation> getTextureForPlanet(String planetName) {
        ResourceLocation returnLoc = planetResourceLocations.get(planetName);
        if (returnLoc != null) {
            return Optional.of(returnLoc);
        } else {
            return Optional.empty();
        }
    }
}
