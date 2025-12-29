package com.nythicalnorm.nythicalSpaceProgram.spacecraft;

import com.nythicalnorm.nythicalSpaceProgram.dimensions.SpaceDimension;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.physics.PhysicsContext;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.physics.PlayerPhysicsPlanet;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.physics.PlayerPhysicsSpace;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3f;

public abstract class AbstractPlayerSpacecraftBody extends AbstractEntitySpacecraftBody {
    protected static final float JetpackRotationalForce = 0.1f;
    protected static final double JetpackTranslationForce = 1d;
    protected static final double JetpackThrottleForce = 25d;
    protected Player player;

    public AbstractPlayerSpacecraftBody() {
        this.angularVelocity = new Vector3f();
        this.velocityChangedLastFrame = false;
    }

    public void processMovement(SpacecraftControlState state) {

    }

    @Override
    public PhysicsContext getPhysicsContext() {
        //temporary dimension check will be changed to allow for different contexts
        if (player.level().dimension() == SpaceDimension.SPACE_LEVEL_KEY) {
            return new PlayerPhysicsSpace(player, this);
        }
        else {
            return new PlayerPhysicsPlanet(player, this);
        }
    }

    public void setPlayerEntity(Player playerNew) {
        this.player = playerNew;
    }

    public Player getPlayerEntity() {
        return player;
    }
}
