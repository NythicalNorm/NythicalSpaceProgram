package com.nythicalnorm.nythicalSpaceProgram.spacecraft.physics;

import com.nythicalnorm.nythicalSpaceProgram.spacecraft.AbstractEntitySpacecraftBody;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3f;

public abstract class PhysicsContext {
    protected final Entity playerEntity;
    protected final AbstractEntitySpacecraftBody orbitBody;

    public PhysicsContext(Entity playerEntity, AbstractEntitySpacecraftBody orbitBody) {
        this.playerEntity = playerEntity;
        this.orbitBody = orbitBody;
    }

    public abstract void applyAcceleration(double accelerationX, double accelerationY, double accelerationZ, Vector3f angularAcceleration);
}
