package com.nythicalnorm.voxelspaceprogram.network.assembler;

import com.nythicalnorm.voxelspaceprogram.block.gse.entity.VehicleAssemblerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AssemblerButtonPress extends ServerboundAssemblerGUI {
    private final ButtonType buttonType;

    public AssemblerButtonPress(ButtonType type, BlockPos assemblerPos) {
        super(assemblerPos);
        this.buttonType = type;
    }

    public AssemblerButtonPress(FriendlyByteBuf friendlyByteBuf) {
        super(friendlyByteBuf);
        this.buttonType = friendlyByteBuf.readEnum(ButtonType.class);
    }

    @Override
    public void encode(FriendlyByteBuf friendlyByteBuf) {
        super.encode(friendlyByteBuf);
        friendlyByteBuf.writeEnum(this.buttonType);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        if (contextSupplier.get().getDirection() == NetworkDirection.PLAY_TO_SERVER ) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                BlockEntity entity = context.getSender().level().getBlockEntity(assemblerPos);
                if (entity instanceof VehicleAssemblerEntity vehicleAssembler) {
                    vehicleAssembler.buttonPress(this.buttonType);
                }
            });
            context.setPacketHandled(true);
        }
    }
}
