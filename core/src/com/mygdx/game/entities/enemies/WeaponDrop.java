package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Team;
import com.mygdx.game.entities.hitboxes.BodyHitbox;
import com.mygdx.game.util.Util;
import com.mygdx.game.weapons.Weapon;

import static java.lang.Math.*;
import static java.lang.Math.sin;

public class WeaponDrop extends Entity {
    private final Weapon weapon;
    private boolean pickedUp = false;

    private BodyHitbox hitbox;
    private double radius = 10;
    private double accel = 900;
    private double friction = 0.25;

    private double maxPushSpeed = 200;
    private double pushVelX = 0;
    private double pushVelY = 0;

    private double spriteAngle;
    private double rotationSpeed;
    private final double spriteScale = 5.5;
    private final double glowScale = spriteScale * 2.0;

    public WeaponDrop(Weapon weapon, double x, double y) {
        super(Team.PLAYER, x, y);
        this.weapon = weapon;

        spriteAngle = (Math.random() * 2 * Math.PI) - Math.PI;
        rotationSpeed = Util.mix(0.01, 0.04, Math.random());

        double angle = Math.random() * 2 * Math.PI;
        this.x += radius * Math.cos(angle);
        this.y += radius * Math.sin(angle);
        double launchSpeed = Util.mix(150, 300, Math.random());
        xVel = launchSpeed * Math.cos(angle);
        yVel = launchSpeed * Math.sin(angle);

        terrainCollisionRadius = radius;

        hitbox = new BodyHitbox(this, team, radius) {
            @Override
            public void takeDamage(double damage, double knockback, double knockbackAngle) {
                //xVel += knockback * Math.cos(knockbackAngle);
                //yVel += knockback * Math.sin(knockbackAngle);
            }

            @Override
            public void getPushedBy(BodyHitbox other) {
                double angle = Math.atan2(other.getY() - y, other.getX() - x);
                double normalX = Math.cos(angle);
                double normalY = Math.sin(angle);

                double distance = Math.hypot(other.getY() - y, other.getX() - x);
                double pushPct = Math.max(0, Math.min(1, Util.mix(1.0, -0.05, Math.pow(distance / (radius + other.getRadius()), 3))));

                pushVelX -= maxPushSpeed * pushPct * normalX;
                pushVelY -= maxPushSpeed * pushPct * normalY;
            }
        };
        hitbox.setDamageable(false);
        hitbox.setPosition(x, y);

        bodyHitboxes = new BodyHitbox[] {hitbox};
        registerEntityAndHitboxes();
    }

    @Override
    public void updatePre(double delta) {
        //friction
        double vel = Math.hypot(xVel, yVel);
        double angle = Math.atan2(yVel, xVel);
        vel = signum(vel) * max(0, abs(vel) - accel * friction * delta);
        xVel = vel * cos(angle);
        yVel = vel * sin(angle);

        //set next position for collision detection
        nextX = x + (xVel + pushVelX) * delta;
        nextY = y + (yVel + pushVelY) * delta;

        pushVelX = 0;
        pushVelY = 0;
    }

    @Override
    public void updatePost(double delta) {
        x = nextX;
        y = nextY;

        spriteAngle = Math.signum(spriteAngle) * (Math.abs(spriteAngle) + (Math.hypot(xVel, yVel)) * rotationSpeed);

        hitbox.setPosition(x, y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        int numGlowLayers = 6;

        batch.setColor(1, 1, 1, (1.0f / numGlowLayers));
        for(double i = 0; i < 1; i += (1.0 / numGlowLayers)) {
            batch.draw(weapon.getTexture(),
                    (float)(x-radius),
                    (float)(y-radius),
                    (float)radius,
                    (float)radius,
                    (float)radius*2,
                    (float)radius*2,
                    (float)Util.mix(glowScale, spriteScale, i),
                    (float)Util.mix(glowScale, spriteScale, i),
                    (float)spriteAngle);
        }

        batch.setColor(Color.BLACK);
        batch.draw(weapon.getTexture(),
                (float)(x-radius),
                (float)(y-radius),
                (float)radius,
                (float)radius,
                (float)radius*2,
                (float)radius*2,
                (float)spriteScale,
                (float)spriteScale,
                (float)spriteAngle);
    }

    @Override
    public void eventTerrainCollision(double angle) {

    }

    @Override
    public boolean isAlive() {
        return !pickedUp;
    }

    @Override
    public void kill() {
        pickedUp = true;
    }

    @Override
    public void updateSlotPositions(double slotSize) {
        slotMinX = (int)Math.floor((x - radius) / slotSize);
        slotMaxX = (int)Math.floor((x + radius) / slotSize);
        slotMinY = (int)Math.floor((y - radius) / slotSize);
        slotMaxY = (int)Math.floor((y + radius) / slotSize);
    }
}
