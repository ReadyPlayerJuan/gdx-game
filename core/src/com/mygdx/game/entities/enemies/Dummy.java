package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Team;
import com.mygdx.game.entities.hitboxes.BodyHitbox;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.textures.TextureManager;

public class Dummy extends Entity {
    private Sprite sprite;
    private BodyHitbox hitbox;

    private final double maxHealth = 30;
    private double health = maxHealth;
    private double radius = 20;

    public Dummy(double x, double y) {
        super(Team.ENEMY, x, y);

        terrainCollisionRadius = radius;

        //sprite = new Sprite(SpriteData.PLAYER).setPosition((float)x, (float)y).setSize((float)radius*2);
        sprite = new Sprite(TextureManager.getTexture(TextureData.TEST_IMAGE));
        sprite.setSize((float)radius, (float)radius);

        hitbox = new BodyHitbox(this, team, radius) {
            @Override
            public void takeDamage(double damage, double knockback, double knockbackAngle) {
                health -= damage;
                System.out.println("health: " + health);
                //xVel += knockback * Math.cos(knockbackAngle);
                //yVel += knockback * Math.sin(knockbackAngle);
            }
        };

        bodyHitboxes = new BodyHitbox[] {hitbox};
        registerEntityAndHitboxes();
    }

    @Override
    public void updatePre(double delta) {
        nextX = x + xVel * delta;
        nextY = y + yVel * delta;
    }

    @Override
    public void updatePost(double delta) {
        x = nextX;
        y = nextY;
        sprite.setRotation(sprite.getRotation() + 0.01f);
        //sprite.setImageIndex((int)(WindowManager.getTime() * 1.0) % 4);
        sprite.setPosition((float)x, (float)y);

        hitbox.setPosition(x, y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
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
