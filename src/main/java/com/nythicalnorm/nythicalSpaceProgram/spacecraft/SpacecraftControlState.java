package com.nythicalnorm.nythicalSpaceProgram.spacecraft;

import com.nythicalnorm.nythicalSpaceProgram.network.NetworkEncoders;
import net.minecraft.network.FriendlyByteBuf;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class SpacecraftControlState {
//    public final float inputAD;
//    public final float inputSW;
//    public final float inputQE;
//    public final float inputShiftCTRL;
    public final float throttle;
    public final boolean SAS;
    public final boolean RCS;
    public final boolean inDockingMode;
    public final Vector3d relativePos;
    public final Vector3d relativeVelocity;
    public final Vector3f angularVelocity;
    public final Quaternionf rotation;


    public SpacecraftControlState(float throttle, boolean SAS, boolean RCS, boolean inDockingMode, Vector3d relativePos, Vector3d relativeVelocity,
                                  Quaternionf rotation, Vector3f angularVel) {
        this.throttle = throttle;
        this.SAS = SAS;
        this.RCS = RCS;
        this.inDockingMode = inDockingMode;
        this.relativePos = relativePos;
        this.relativeVelocity = relativeVelocity;
        this.rotation = rotation;
        this.angularVelocity = angularVel;
    }

    public SpacecraftControlState(FriendlyByteBuf friendlyByteBuf) {
        throttle = friendlyByteBuf.readFloat();
        SAS = friendlyByteBuf.readBoolean();
        RCS = friendlyByteBuf.readBoolean();
        inDockingMode = friendlyByteBuf.readBoolean();

        relativePos = NetworkEncoders.readVector3d(friendlyByteBuf);
        relativeVelocity = NetworkEncoders.readVector3d(friendlyByteBuf);
        angularVelocity = friendlyByteBuf.readVector3f();
        rotation = friendlyByteBuf.readQuaternion();
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(throttle);
        friendlyByteBuf.writeBoolean(SAS);
        friendlyByteBuf.writeBoolean(RCS);
        friendlyByteBuf.writeBoolean(inDockingMode);

        NetworkEncoders.writeVector3d(friendlyByteBuf, relativePos);
        NetworkEncoders.writeVector3d(friendlyByteBuf, relativeVelocity);
        friendlyByteBuf.writeVector3f(angularVelocity);
        friendlyByteBuf.writeQuaternion(rotation);
    }
}
