package org.waveapi.api.world.entity;

import net.minecraft.entity.Entity;
import org.waveapi.api.math.Vector3;

public class EntityBase {
    public Entity entity;

    public EntityBase(Entity entity) {
        this.entity = entity;
    }

    public void setVelocity(Vector3 velocity) {
        entity.setVelocity(velocity.v);
    }

    public float getYaw() {
        return entity.getYaw();
    }
    public float getPitch() {
        return entity.getPitch();
    }


    public boolean isAlive() {
        return entity.isAlive();
    }
}
