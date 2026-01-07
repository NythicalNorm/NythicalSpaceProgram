package com.nythicalnorm.voxelspaceprogram.block.rocket_parts.entity.models;
// Made with Blockbench 5.0.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.nythicalnorm.voxelspaceprogram.VoxelSpaceProgram;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class EngineModels {
    public static final ModelLayerLocation ThreeKeroloxLayerLoc = new ModelLayerLocation(VoxelSpaceProgram.rl("three_kerolox_model"), "main");

	public static LayerDefinition createThreeKeroloxModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition fixed = partdefinition.addOrReplaceChild("fixed", CubeListBuilder.create().texOffs(82, 88).addBox(-11.0F, -17.5F, -11.0F, 22.0F, 3.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, 0.0F));

        PartDefinition gimbling = fixed.addOrReplaceChild("gimbling", CubeListBuilder.create().texOffs(0, 108).addBox(-16.0F, -0.4474F, -15.8947F, 3.0F, 6.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(82, 0).addBox(-18.0F, 5.5526F, -17.8947F, 3.0F, 8.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(82, 44).addBox(15.0F, 5.5526F, -17.8947F, 3.0F, 8.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(140, 113).addBox(-14.0F, -6.4474F, -13.8947F, 3.0F, 6.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(160, 45).addBox(-11.0F, -10.4474F, -10.8947F, 3.0F, 4.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(70, 113).addBox(13.0F, -0.4474F, -15.8947F, 3.0F, 6.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(0, 146).addBox(11.0F, -6.4474F, -13.8947F, 3.0F, 6.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(132, 166).addBox(8.0F, -10.4474F, -10.8947F, 3.0F, 4.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(160, 0).addBox(-8.0F, -17.4474F, -7.8947F, 16.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(160, 23).addBox(-15.0F, 5.5526F, -17.8947F, 30.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(160, 71).addBox(-13.0F, -0.4474F, 13.1053F, 26.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(170, 80).addBox(-11.0F, -6.4474F, 11.1053F, 22.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(170, 89).addBox(-11.0F, -6.4474F, -13.8947F, 22.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(170, 98).addBox(-8.0F, -10.4474F, 8.1053F, 16.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(170, 105).addBox(-8.0F, -10.4474F, -10.8947F, 16.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(62, 170).addBox(-13.0F, -0.4474F, -15.8947F, 26.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(160, 34).addBox(-15.0F, 5.5526F, 15.1053F, 30.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(140, 147).addBox(-16.0F, 13.5526F, 16.1053F, 32.0F, 16.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(62, 151).addBox(-16.0F, 13.5526F, -18.8947F, 32.0F, 16.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(16.0F, 13.5526F, -18.8947F, 3.0F, 16.0F, 38.0F, new CubeDeformation(0.0F))
                .texOffs(0, 54).addBox(-19.0F, 13.5526F, -18.8947F, 3.0F, 16.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.9474F, -0.1053F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}
}