package com.mygdx.game.entities.hitboxes;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Team;

public abstract class DamagerHitbox extends Hitbox {

    public DamagerHitbox(Entity owner, Team team, double x, double y, double radius) {
        super(owner, team, x, y, radius);
    }

    public DamagerHitbox(Entity owner, Team team, double radius) {
        super(owner, team, radius);
    }

    public abstract void damage(BodyHitbox other);
}
