package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Team;
import com.mygdx.game.entities.hitboxes.BodyHitbox;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.textures.TextureManager;
import com.mygdx.game.util.Util;

import static java.lang.Math.*;
import static java.lang.Math.abs;

public class Dummy extends Entity {
    private Animation<TextureRegion> idleAnimation;
    private float animationTimer = 0;

    private BodyHitbox hitbox;

    private final double maxHealth = 30;
    private double health = maxHealth;
    private double radius = 20;
    private double accel = 1500;
    private double friction = 1.0;

    private double maxPushSpeed = 200;
    private double pushVelX = 0;
    private double pushVelY = 0;

    public Dummy(double x, double y) {
        super(Team.ENEMY, x, y);

        terrainCollisionRadius = radius;

        idleAnimation = TextureManager.makeAnimation(TextureData.PLAYER_SHEET, 0, 4, 1f);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        hitbox = new BodyHitbox(this, team, radius) {
            @Override
            public void takeDamage(double damage, double knockback, double knockbackAngle) {
                health -= damage;
                //System.out.println("health: " + health);
                xVel += knockback * Math.cos(knockbackAngle);
                yVel += knockback * Math.sin(knockbackAngle);
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

        animationTimer += (float)delta;

        hitbox.setPosition(x, y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(idleAnimation.getKeyFrame(animationTimer),
                (float)(x-radius),
                (float)(y-radius),
                (float)radius,
                (float)radius,
                (float)radius*2,
                (float)radius*2,
                1f,
                1f,
                0);
    }

    @Override
    public void eventTerrainCollision(double angle) {

    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void updateSlotPositions(double slotSize) {
        slotMinX = (int)Math.floor((x - radius) / slotSize);
        slotMaxX = (int)Math.floor((x + radius) / slotSize);
        slotMinY = (int)Math.floor((y - radius) / slotSize);
        slotMaxY = (int)Math.floor((y + radius) / slotSize);
    }
}
