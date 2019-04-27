package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.boards.BoardCamera;
import com.mygdx.game.entities.hitboxes.BodyHitbox;
import com.mygdx.game.input.ControlMapping;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.textures.TextureManager;

import java.util.LinkedList;

import static java.lang.Math.*;

public class Player extends Entity /*implements WeaponController*/ {
    private Animation<TextureRegion> idleAnimation;
    private float animationTimer = 0;

    private BodyHitbox hitbox;

    private final double maxHealth = 1;
    private double health = maxHealth;
    private double radius = 20;
    private double accel = 2000;
    private double maxSpeed = 300;

    private BoardCamera boardCamera;
    private int currentWeaponIndex = 0;
    private boolean firingWeapon = false, prevFiringWeapon = false;
    private double targetX = 0, targetY = 0;

    public Player() {
        super(Team.PLAYER);

        terrainCollisionRadius = radius;

        idleAnimation = TextureManager.makeAnimation(TextureData.PLAYER_SHEET, 0, 4, 0.15f);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        hitbox = new BodyHitbox(this, team, radius) {
            @Override
            public void takeDamage(double damage, double knockback, double knockbackAngle) {
                health -= damage;
                xVel += knockback * Math.cos(knockbackAngle);
                yVel += knockback * Math.sin(knockbackAngle);
            }
        };

        bodyHitboxes = new BodyHitbox[] {hitbox};
        registerEntityAndHitboxes();
    }

    @Override
    public void updatePre(double delta) {
        LinkedList<Double[]> heldKeys = InputManager.inputManager.getHeldKeys();

        int moveX = 0;
        int moveY = 0;
        prevFiringWeapon = firingWeapon;
        firingWeapon = false;
        for(Double[] keys: heldKeys) {
            //System.out.println(keys[0] + " " + keys[1]);
            if(keys[0] == ControlMapping.MOVE_LEFT.keyCode)
                moveX--;
            if(keys[0] == ControlMapping.MOVE_RIGHT.keyCode)
                moveX++;
            if(keys[0] == ControlMapping.MOVE_UP.keyCode)
                moveY--;
            if(keys[0] == ControlMapping.MOVE_DOWN.keyCode)
                moveY++;

            if(keys[0] == ControlMapping.FIRE_WEAPON.keyCode)
                firingWeapon = true;
        }

        if(moveX == 0) {
            xVel = signum(xVel) * max(0, abs(xVel) - accel * delta);
        } else {
            xVel += moveX * accel * delta;
            xVel = signum(xVel) * min(maxSpeed, abs(xVel));
        }
        if(moveY == 0) {
            yVel = signum(yVel) * max(0, abs(yVel) - accel * delta);
        } else {
            yVel += moveY * accel * delta;
            yVel = signum(yVel) * min(maxSpeed, abs(yVel));
        }


        nextX = x + xVel * delta;
        nextY = y + yVel * delta;
    }

    @Override
    public void updatePost(double delta) {
        x = nextX;
        y = nextY;

        animationTimer += (float)delta;

        hitbox.setPosition(x, y);

        //TextureRegion t = idleAnimation.getKeyFrame(animationTimer);
        //sprite.setTexture(t);

        //targetX = InputManager.mouseX - boardCamera.getViewWidth()/2 + boardCamera.getCenterX() - x;
        //targetY = -InputManager.mouseY + boardCamera.getViewHeight()/2 + boardCamera.getCenterY() - y;
        //PlayerData.getEquippedWeapon(currentWeaponIndex).update(delta, this);
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
    public void updateSlotPositions(double slotSize) {
        slotMinX = (int)floor((x - radius) / slotSize);
        slotMaxX = (int)floor((x + radius) / slotSize);
        slotMinY = (int)floor((y - radius) / slotSize);
        slotMaxY = (int)floor((y + radius) / slotSize);
    }

    /*@Override
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
    }*/
}
