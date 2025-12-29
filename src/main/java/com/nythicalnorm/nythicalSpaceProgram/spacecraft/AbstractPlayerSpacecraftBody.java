package com.nythicalnorm.nythicalSpaceProgram.spacecraft;

import com.nythicalnorm.nythicalSpaceProgram.dimensions.SpaceDimension;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.physics.PhysicsContext;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.physics.PlayerPhysicsPlanet;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.physics.PlayerPhysicsSpace;
import net.minecraft.world.entity.player.Player;

public abstract class AbstractPlayerSpacecraftBody extends EntitySpacecraftBody {
    protected static final float JetpackRotationalForce = 0.1f;
    protected static final double JetpackTranslationForce = 1d;
    protected static final double JetpackThrottleForce = 25d;
    protected Player player;

    public AbstractPlayerSpacecraftBody() {
        super();
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

    public void removeYourself() {
        if (parent != null) {
           if (parent.hasChild(this)) {
               parent.removeChild(this.id);
               this.parent = null;
           }
        }
    }
}
