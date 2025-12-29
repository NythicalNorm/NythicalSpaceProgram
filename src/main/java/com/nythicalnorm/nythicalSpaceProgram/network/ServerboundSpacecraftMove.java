package com.nythicalnorm.nythicalSpaceProgram.network;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.SpacecraftControlState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.Stack;
import java.util.function.Supplier;

public class ServerboundSpacecraftMove {
    // Need to add a currentTimeStamp so when the orbital elements are calculated on the server side it matches regardless of ping
    private final SpacecraftControlState spacecraftControlState;
    private final Stack<String> spacecraftBodyAddress;

    public ServerboundSpacecraftMove(Stack<String> controlledBody, SpacecraftControlState spacecraftControlState) {
        this.spacecraftControlState = spacecraftControlState;
        this.spacecraftBodyAddress = controlledBody;
    }

    public ServerboundSpacecraftMove(FriendlyByteBuf friendlyByteBuf) {
        this.spacecraftBodyAddress = NetworkEncoders.readStack(friendlyByteBuf);
        this.spacecraftControlState = new SpacecraftControlState(friendlyByteBuf);
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        NetworkEncoders.writeStack(friendlyByteBuf, spacecraftBodyAddress);
        spacecraftControlState.encode(friendlyByteBuf);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        if (contextSupplier.get().getDirection() == NetworkDirection.PLAY_TO_SERVER ) {
            NetworkEvent.Context context = contextSupplier.get();
            NythicalSpaceProgram.getSolarSystem().ifPresent(solarSystem -> {
                context.enqueueWork(() -> solarSystem.handleSpacecraftMove(context.getSender(), spacecraftBodyAddress, spacecraftControlState));
            });
            context.setPacketHandled(true);
        }
    }
}
