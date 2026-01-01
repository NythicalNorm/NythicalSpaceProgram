package com.nythicalnorm.nythicalSpaceProgram.spacecraft;

import com.nythicalnorm.nythicalSpaceProgram.solarsystem.Orbit;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.OrbitalElements;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.physics.PhysicsContext;
import net.minecraft.network.FriendlyByteBuf;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class EntitySpacecraftBody extends Orbit {
    protected Vector3f angularVelocity;
    protected boolean velocityChangedLastFrame;
    private static final float tolerance = 1e-8f;

    public EntitySpacecraftBody() {
        this.absoluteOrbitalPos = new Vector3d();
        this.relativeOrbitalPos = new Vector3d();
        this.relativeVelocity = new Vector3d();
        this.rotation = new Quaternionf();
        this.angularVelocity = new Vector3f();
        this.orbitalElements = new OrbitalElements(0f,0f, 0f, 0f, 0f, 0L);
    }

    public void simulatePropagate(long TimeElapsed, Vector3d parentPos, double mass) {
        if (!velocityChangedLastFrame) {
            Vector3d[] stateVectors = orbitalElements.ToCartesian(TimeElapsed);
            this.relativeOrbitalPos = stateVectors[0];
            this.relativeVelocity = stateVectors[1];
        } else {
            orbitalElements.fromCartesian(this.relativeOrbitalPos, this.relativeVelocity, TimeElapsed);
            velocityChangedLastFrame = false;
        }
        absoluteOrbitalPos = new Vector3d(parentPos).add(relativeOrbitalPos);
        updateRotationFromVelocity();
    }

    private void updateRotationFromVelocity() {
        if (angularVelocity.x > tolerance || angularVelocity.y > tolerance || angularVelocity.z > tolerance) {
            Quaternionf rotationalVel = new Quaternionf(angularVelocity.x, angularVelocity.y, angularVelocity.z, 0f);
            //No Idea how this is going to work for Players ??? \_(ツ)_/
            //this.rotation.mul(rotationalVel.mul(0.5f));
        }
    }

    public Vector3f getAngularVelocity() {
        return new Vector3f(angularVelocity);
    }

    public void setVelocityForUpdate(Vector3d velocity, Vector3f angularVelocity) {
        this.relativeVelocity = velocity;
        this.angularVelocity = angularVelocity;
        velocityChangedLastFrame = true;
    }

    public void processMovement(SpacecraftControlState state) {
        this.relativeOrbitalPos = state.relativePos;
        this.relativeVelocity = state.relativeVelocity;
        this.angularVelocity = state.angularVelocity;
        this.rotation = state.rotation;
        velocityChangedLastFrame = true;
    }

    // don't use this use the Overrides
    public PhysicsContext getPhysicsContext() {
        return null;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        super.encode(buffer);
        buffer.writeVector3f(angularVelocity);
    }

    @Override
    public void decode(FriendlyByteBuf buffer) {
        super.decode(buffer);
        angularVelocity = buffer.readVector3f();
    }
}
