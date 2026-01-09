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
public class EngineModelData {
    public static final ModelLayerLocation ThreeKeroloxLayerLoc = new ModelLayerLocation(VoxelSpaceProgram.rl("three_kerolox_model"), "main");
    public static final ModelLayerLocation TwoKeroloxLayerLoc = new ModelLayerLocation(VoxelSpaceProgram.rl("two_kerolox_model"), "main");

	public static LayerDefinition createThreeKeroloxModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition fixed = partdefinition.addOrReplaceChild("fixed", CubeListBuilder.create().texOffs(82, 88).addBox(-11.0F, -17.5F, -11.0F, 22.0F, 3.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, 0.0F));

        PartDefinition gimbaling = fixed.addOrReplaceChild("gimbaling", CubeListBuilder.create().texOffs(0, 108).addBox(-16.0F, -0.4474F, -15.8947F, 3.0F, 6.0F, 32.0F, new CubeDeformation(0.0F))
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

    public static LayerDefinition createTwoKeroloxModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition fixed = partdefinition.addOrReplaceChild("fixed", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, 11.0F, -20.0F, 24.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(1, 27).addBox(-17.0F, 13.0F, -17.0F, 18.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -19.0F, 8.0F));

        PartDefinition gimbaling = fixed.addOrReplaceChild("gimbaling", CubeListBuilder.create().texOffs(36, 118).addBox(2.0F, 0.25F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(81, 112).addBox(4.0F, 2.25F, -6.0F, 2.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(97, 48).addBox(6.0F, 5.25F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(74, 26).addBox(7.0F, 8.25F, -9.0F, 2.0F, 4.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 79).addBox(8.0F, 12.25F, -10.0F, 2.0F, 6.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(9.0F, 18.25F, -11.0F, 2.0F, 9.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(56, 118).addBox(-4.0F, 0.25F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(96, 0).addBox(-8.0F, 5.25F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(48, 48).addBox(-11.0F, 18.25F, -11.0F, 2.0F, 9.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(110, 112).addBox(-6.0F, 2.25F, -6.0F, 2.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(88, 79).addBox(-9.0F, 8.25F, -9.0F, 2.0F, 4.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(44, 79).addBox(-10.0F, 12.25F, -10.0F, 2.0F, 6.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(64, 112).addBox(-2.0F, 0.25F, -4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 118).addBox(-2.0F, 0.25F, 2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(114, 42).addBox(-4.0F, 2.25F, -6.0F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 119).addBox(-4.0F, 2.25F, 4.0F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 113).addBox(-6.0F, 5.25F, -8.0F, 12.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(114, 25).addBox(-6.0F, 5.25F, 6.0F, 12.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(97, 19).addBox(-7.0F, 8.25F, -9.0F, 14.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(1, 113).addBox(-7.0F, 8.25F, 7.0F, 14.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(1, 105).addBox(-8.0F, 12.25F, -10.0F, 16.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(41, 105).addBox(-8.0F, 12.25F, 8.0F, 16.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(97, 67).addBox(-9.0F, 18.25F, -11.0F, 18.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(89, 101).addBox(-9.0F, 18.25F, 9.0F, 18.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 15.75F, -8.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }
}