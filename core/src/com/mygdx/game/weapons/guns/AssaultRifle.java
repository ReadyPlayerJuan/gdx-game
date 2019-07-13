package com.mygdx.game.weapons.guns;

import com.mygdx.game.entities.projectiles.TestProjectile;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.weapons.*;
import com.mygdx.game.entities.Rarity;
import com.mygdx.game.weapons.stats.WeaponStat;

public class AssaultRifle extends Weapon {
    public static WeaponGenerator generator = new WeaponGenerator() {
        @Override
        public Weapon generateWeapon() {
            return generateWeapon(Rarity.values()[(int)(Math.random() * Rarity.values().length)]);
        }

        @Override
        public Weapon generateWeapon(Rarity rarity) {
            return new AssaultRifle(Rarity.values()[(int)(Math.random() * Rarity.values().length)]);
        }
    };

    private static WeaponStat[] availableStats() {
        return new WeaponStat[] {
                WeaponStat.BULLET_DAMAGE,
                WeaponStat.BULLET_SPEED,
                WeaponStat.BULLET_SIZE,
                WeaponStat.BULLET_KNOCKBACK,
                WeaponStat.WEAPON_SPREAD,
                WeaponStat.WEAPON_FIRE_RATE,
                WeaponStat.WEAPON_KICK,
        };
    }
    private static final double[][] defaultStats() {
        return new double[][]{
                {7.5, 3.0},      //DAMAGE
                {575, 200},      //BULLET SPEED
                {11.0, 3.0},     //BULLET SIZE
                {175, 100},      //BULLET KNOCKBACK
                {0.040, 0.020},  //WEAPON SPREAD
                {5.50, 2.5},     //WEAPON FIRE RATE
                {125.0, 80.0},   //WEAPON KICK
        };
    }

    private double fireTimer = 0;

    public AssaultRifle(Rarity rarity) {
        super(WeaponType.ASSAULT_RIFLE, rarity, WeaponStarRating.random(), TextureData.WEAPONS, 3, availableStats(), defaultStats());

        randomizeVariationRolls();
        initStats();
        updateWeaponInfo();
    }

    @Override
    public void update(double delta, WeaponController controller) {
        boolean firing = controller.isFiringWeapon();
        //boolean prevFiring = controller.wasFiringWeapon();

        //System.out.println(fireTimer);
        double fireTime = 1.0 / stats[5][1];
        if(firing) {
            fireTimer += delta;

            while(fireTimer >= fireTime) {
                fireTimer -= fireTime;
                fire(delta, controller);
            }
        } else {
            fireTimer = Math.min(fireTime, fireTimer + delta);
        }
    }

    protected void fire(double delta, WeaponController controller) {
        double angle = Math.atan2(controller.getTargetY(), controller.getTargetX());
        double variedAngle = angle + Math.toRadians(180) * stats[4][1] * Math.random() * Math.signum(Math.random()-0.5); //add angle variation

        controller.kick(angle + Math.PI, stats[6][1]);

        new TestProjectile(controller, this, //null,//TextureData.PLAYER_SHEET,
                controller.getX(), controller.getY(),
                stats[0][1],   //damage
                stats[1][1],   //speed
                variedAngle,   //angle
                stats[2][1],   //bullet radius
                stats[3][1]);  //knockback
    }
}
