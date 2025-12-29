package com.nythicalnorm.nythicalSpaceProgram.spacecraft.physics;

import com.nythicalnorm.nythicalSpaceProgram.spacecraft.EntitySpacecraftBody;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class PlayerPhysicsPlanet extends PhysicsContext{
    public PlayerPhysicsPlanet(Entity playerEntity, EntitySpacecraftBody orbitBody) {
        super(playerEntity, orbitBody);
    }

    @Override
    public boolean applyAcceleration(double accelerationX, double accelerationY, double accelerationZ, Vector3f angularAcceleration) {
        if (playerEntity instanceof LocalPlayer player) {
            player.travel(new Vec3(-accelerationX, accelerationY, accelerationZ));
        }
        return false;
    }
}
