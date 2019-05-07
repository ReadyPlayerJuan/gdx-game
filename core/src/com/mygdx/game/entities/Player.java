package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.boards.BoardCamera;
import com.mygdx.game.entities.hitboxes.BodyHitbox;
import com.mygdx.game.input.ControlMapping;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.textures.TextureManager;
import com.mygdx.game.util.Util;
import com.mygdx.game.weapons.WeaponController;

import java.util.LinkedList;

import static java.lang.Math.*;

public class Player extends Entity implements WeaponController {
    private Animation<TextureRegion> idleAnimation;
    private float animationTimer = 0;

    private BodyHitbox hitbox;

    private final double maxHealth = 1;
    private double health = maxHealth;
    private double radius = 20;
    private double accel = 3000;
    private double friction = 0.75;
    private double maxSpeed = 400;
    private double turnAssist = 2.0;

    private double maxPushSpeed = 200;
    private double pushVelX = 0;
    private double pushVelY = 0;


    private BoardCamera boardCamera;
    private int currentWeaponIndex = 0;
    private boolean firingWeapon = false, prevFiringWeapon = false;
    private double targetX = 0, targetY = 0;

    public Player() {
        super(Team.PLAYER);
        terrainCollisionRadius = radius;

        idleAnimation = TextureManager.makeAnimation(TextureData.PLAYER_SHEET, 0, 4, 1f);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        hitbox = new BodyHitbox(this, team, radius) {
            @Override
            public void takeDamage(double damage, double knockback, double knockbackAngle) {
                health -= damage;
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
        LinkedList<Double[]> heldKeys = InputManager.getHeldKeys();

        //check input
        int moveX = 0;
        int moveY = 0;
        prevFiringWeapon = firingWeapon;
        firingWeapon = false;
        for(Double[] keys: heldKeys) {
            //System.out.println(keys[0] + " " + keys[1]);
            if(keys[0] == ControlMapping.MOVE_LEFT)
                moveX--;
            if(keys[0] == ControlMapping.MOVE_RIGHT)
                moveX++;
            if(keys[0] == ControlMapping.MOVE_UP)
                moveY--;
            if(keys[0] == ControlMapping.MOVE_DOWN)
                moveY++;

            if(keys[0] == ControlMapping.FIRE_WEAPON)
                firingWeapon = true;
        }


        //accelerate
        if(moveX != 0) {
            xVel += moveX * accel * delta;
        }
        if(moveY != 0) {
            yVel += moveY * accel * delta;
        }


        //cap max speed
        double vel = Math.hypot(xVel, yVel);
        double angle = Math.atan2(yVel, xVel);
        if(vel > maxSpeed) {
            vel = signum(vel) * min(maxSpeed, abs(vel) - accel * delta);
        } else if(moveX == 0 && moveY == 0) {
            vel = signum(vel) * max(0, abs(vel) - accel * friction * delta);
        }

        //assist in turning
        if(moveX != 0 || moveY != 0) {
            double targetAngle = Math.atan2(moveY, moveX);
            double turnAssistAmount = Math.cos(targetAngle) * Math.cos(angle) + Math.sin(targetAngle) * Math.sin(angle);
            //System.out.println(turnAssistAmount);

            double angleDiff = targetAngle - angle;
            if(angleDiff > Math.PI)
                angleDiff -= 2*Math.PI;
            if(angleDiff < -Math.PI)
                angleDiff += 2*Math.PI;

            angle += angleDiff * Math.min(1, turnAssist * turnAssistAmount * delta);
        }
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

        targetX = InputManager.getMouseX() - boardCamera.viewportWidth/2 + boardCamera.position.x - x;
        targetY = InputManager.getMouseY() - boardCamera.viewportHeight/2 + boardCamera.position.y - y;
        PlayerData.getEquippedWeapon(currentWeaponIndex).update(delta, this);
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

        //sprite.draw(batch);
    }

    public void setBoardCamera(BoardCamera boardCamera) {
        this.boardCamera = boardCamera;
    }

    @Override
    public void eventTerrainCollision(double angle) {

    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void kill() {
        health = 0;
    }

    @Override
    public void updateSlotPositions(double slotSize) {
        slotMinX = (int)floor((x - radius) / slotSize);
        slotMaxX = (int)floor((x + radius) / slotSize);
        slotMinY = (int)floor((y - radius) / slotSize);
        slotMaxY = (int)floor((y + radius) / slotSize);
    }

    @Override
    public double getTargetX() {
        return targetX;
    }

    @Override
    public double getTargetY() {
        return targetY;
    }

    @Override
    public boolean isFiringWeapon() {
        return firingWeapon;
    }

    @Override
    public boolean wasFiringWeapon() {
        return prevFiringWeapon;
    }

    @Override
    public void kick(double angle, double kick) {
        xVel += kick * Math.cos(angle);
        yVel += kick * Math.sin(angle);
    }
}
