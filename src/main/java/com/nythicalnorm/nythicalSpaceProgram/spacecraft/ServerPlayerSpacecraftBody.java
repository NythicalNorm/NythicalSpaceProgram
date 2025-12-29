package com.nythicalnorm.nythicalSpaceProgram.spacecraft;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.OrbitalElements;
import net.minecraft.server.level.ServerPlayer;
import org.joml.Quaternionf;

public class ServerPlayerSpacecraftBody extends AbstractPlayerSpacecraftBody {
    private boolean isShipBound;

    public ServerPlayerSpacecraftBody(ServerPlayer playerEntity, boolean isStableOrbit, boolean isShipBound, Quaternionf playerRot, OrbitalElements elements) {
        super();
        this.player = playerEntity;
        this.id = playerEntity.getStringUUID();
        this.isStableOrbit = isStableOrbit;
        this.isShipBound = isShipBound;
        this.rotation = playerRot;
        this.orbitalElements = elements;
    }

    public String getUUid() {
        return player.getStringUUID();
    }

    @Override
    public void removeYourself() {
        NythicalSpaceProgram.getSolarSystem().ifPresent(solarSystem -> {
            solarSystem.removePlayerFromOrbit(this.id);
        });
        super.removeYourself();
    }
}
