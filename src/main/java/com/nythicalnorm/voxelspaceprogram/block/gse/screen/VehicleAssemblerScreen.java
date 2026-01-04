package com.nythicalnorm.voxelspaceprogram.block.gse.screen;

import com.nythicalnorm.voxelspaceprogram.VoxelSpaceProgram;
import com.nythicalnorm.voxelspaceprogram.block.gse.AssemblerState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class VehicleAssemblerScreen extends AbstractContainerScreen<VehicleAssemblerMenu> {
    private static final ResourceLocation BUILD_GUI = VoxelSpaceProgram.rl(
            "textures/gui/vehicleassembler/build.png");

    private static final ResourceLocation JUST_PLACED_GUI = VoxelSpaceProgram.rl(
            "textures/gui/vehicleassembler/just_placed.png");

    private static final int texWidth = 256;
    private static final int texHeight = 192;
    private ResourceLocation currentBG = VoxelSpaceProgram.rl(
            "textures/gui/vehicleassembler/just_placed.png");

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelX = 10000;
        this.titleLabelY = 10000;
    }

    public VehicleAssemblerScreen(VehicleAssemblerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        if (pMenu.getVehicleAssemblerBE() == null) {
            this.onClose();
            return;
        }
        AssemblerState state = pMenu.getVehicleAssemblerBE().getState();

        switch (state) {
            case JUST_PLACED:
                currentBG = JUST_PLACED_GUI;
                addRenderableWidget(Button.builder(CommonComponents.GUI_PROCEED, (button) -> {
                    pMenu.createBoundingBoxButtonPress();
                    pMenu.getVehicleAssemblerBE().getBlockPos();
                }).bounds((this.imageWidth / 2) + 75, (this.imageHeight / 2) + 20, 150, 20).build());;
                break;
            case ASSEMBLY_AREA_READY:
                currentBG = BUILD_GUI;
                break;
            default:
                currentBG = JUST_PLACED_GUI;
        }
    }



    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int x = (width - texWidth) / 2;
        int y = (height - texHeight) / 2;

        pGuiGraphics.blit(currentBG, x, y, 0, 0, texWidth, texHeight);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
