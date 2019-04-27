package com.mygdx.game.weapons.guns;

import com.mygdx.game.entities.projectiles.TestProjectile;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.weapons.WeaponController;
import com.mygdx.game.weapons.WeaponType;
import com.mygdx.game.weapons.stats.WeaponStat;

public class Pistol extends Weapon {
    private static final WeaponType type = WeaponType.PISTOL;
    private static final double[] elementalWeights = new double[] {
            1.0, //NO ELEMENT
            0.0, //EXPLOSION
            0.0, //FIRE
            0.0, //LIGHTNING
            0.0, //FROST
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
            {12.0, 15.0},   //DAMAGE
            {400, 500},     //BULLET SPEED
            {11.0, 13.0},    //BULLET SIZE
            {80, 120},      //BULLET KNOCKBACK
            {0.06, 0.08},   //WEAPON SPREAD
            {2.75, 3.25},   //WEAPON FIRE RATE
            {40.0, 60.0},   //WEAPON KICK
    };

    //inherited: protected WeaponStat[] stats;
    //inherited: protected double[] variationRolls;

    private double fireTimer = 0;

    public Pistol() {
        super(type, elementalWeights, availableStats, defaultStats);

        randomizeVariationRolls();
        initStats();
    }

    @Override
    public void update(double delta, WeaponController controller) {
        boolean firing = controller.isFiringWeapon();
        boolean prevFiring = controller.wasFiringWeapon();

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
        angle += Math.toRadians(180) * stats[4][1] * Math.random() * Math.signum(Math.random()-0.5); //add angle variation

        new TestProjectile(controller, this, TextureData.PLAYER_SHEET,
                controller.getX(), controller.getY(),
                stats[0][1],   //damage
                stats[1][1],   //speed
                angle,         //angle
                stats[2][1],   //bullet radius
                stats[3][1]);  //knockback
    }
}
