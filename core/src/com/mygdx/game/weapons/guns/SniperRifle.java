package com.mygdx.game.weapons.guns;

import com.mygdx.game.entities.projectiles.TestProjectile;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.weapons.WeaponController;
import com.mygdx.game.weapons.WeaponGenerator;
import com.mygdx.game.weapons.WeaponType;
import com.mygdx.game.weapons.stats.WeaponStat;

public class SniperRifle extends Weapon {
    public static WeaponGenerator generator = new WeaponGenerator() {
        @Override
        public Weapon generateWeapon() {
            return new SniperRifle();
        }
    };

    private static final WeaponStat[] availableStats = new WeaponStat[] {
            WeaponStat.BULLET_DAMAGE,
            WeaponStat.BULLET_SPEED,
            WeaponStat.BULLET_SIZE,
            WeaponStat.BULLET_KNOCKBACK,
            WeaponStat.WEAPON_SPREAD,
            WeaponStat.WEAPON_FIRE_RATE,
            WeaponStat.WEAPON_KICK,
    };
    private static final double[][] defaultStats = new double[][] {
            {26.0, 6.0},     //DAMAGE
            {950, 150},      //BULLET SPEED
            {11.0, 1.0},     //BULLET SIZE
            {400, 75},       //BULLET KNOCKBACK
            {0.04, 0.015},   //WEAPON SPREAD
            {1.10, 0.40},    //WEAPON FIRE RATE
            {400, 75},       //WEAPON KICK
    };

    private double fireTimer = 0;

    public SniperRifle() {
        super(WeaponType.SNIPER_RIFLE, TextureData.WEAPONS, 1, availableStats, defaultStats);

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

        new TestProjectile(controller, this, TextureData.PLAYER_SHEET,
                controller.getX(), controller.getY(),
                stats[0][1],   //damage
                stats[1][1],   //speed
                variedAngle,   //angle
                stats[2][1],   //bullet radius
                stats[3][1]);  //knockback
    }
}
