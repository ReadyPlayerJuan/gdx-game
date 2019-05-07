package com.mygdx.game.entities.hitboxes;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Team;

public abstract class BodyHitbox extends Hitbox {
    protected boolean damageable = true;
    protected boolean pushable = true;

    public BodyHitbox(Entity owner, Team team, double x, double y, double radius) {
        super(owner, team, x, y, radius);
    }

    public BodyHitbox(Entity owner, Team team, double radius) {
        super(owner, team, radius);
    }

    public abstract void takeDamage(double damage, double knockback, double knockbackAngle);
    public abstract void getPushedBy(BodyHitbox other);

    public boolean isDamageable() {
        return damageable;
    }

    public void setDamageable(boolean damageable) {
        this.damageable = damageable;
    }

    public boolean isPushable() {
        return pushable;
    }

    public void setPushable(boolean pushable) {
        this.pushable = pushable;
    }
}
