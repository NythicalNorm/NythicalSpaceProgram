package com.nythicalnorm.nythicalSpaceProgram.network;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.EntitySpacecraftBody;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundLoginSolarSystemState {
    private final EntitySpacecraftBody playerData;

    public ClientboundLoginSolarSystemState(EntitySpacecraftBody playerData) {
        this.playerData = playerData;
    }

    public ClientboundLoginSolarSystemState(FriendlyByteBuf friendlyByteBuf) {
        this.playerData = new EntitySpacecraftBody();

        if (friendlyByteBuf.readBoolean()) {
            playerData.decode(friendlyByteBuf);
        }
    }

    public ClientboundLoginSolarSystemState(boolean noData) {
        this.playerData = null;
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        if (playerData != null) {
            friendlyByteBuf.writeBoolean(true);
            playerData.encode(friendlyByteBuf);
        } else {
            friendlyByteBuf.writeBoolean(false);
        }
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        if (contextSupplier.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> NythicalSpaceProgram.startClient(playerData));
            context.setPacketHandled(true);
        }
    }
}
