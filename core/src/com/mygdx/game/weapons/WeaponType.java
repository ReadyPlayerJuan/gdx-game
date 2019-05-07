package com.mygdx.game.weapons;

import com.mygdx.game.weapons.guns.AssaultRifle;
import com.mygdx.game.weapons.guns.Pistol;
import com.mygdx.game.weapons.guns.Shotgun;
import com.mygdx.game.weapons.guns.SniperRifle;

public enum WeaponType {
    PISTOL ("Pistol", 1, Pistol.generator),
    SNIPER_RIFLE ("Sniper Rifle", 1, SniperRifle.generator),
    SHOTGUN ("Shotgun", 1, Shotgun.generator),
    ASSAULT_RIFLE ("Assault Rifle", 1, AssaultRifle.generator),
    MACHINE_GUN ("Machine Gun", 1, null),
    REVOLVER ("Revolver", 0.5, null),
    LASER_GUN ("Laser Gun", 0.3, null),
    CHARGE_RIFLE ("Charge Rifle", 0.3, null),
    TASER ("Taser", 0.3, null),
    FLAME_THROWER ("Flame Thrower", 0.5, null),
    ROCKET_LAUNCHER ("Rocket Launcher", 0.8, null),
    GRENADE_LAUNCHER ("Grenade Launcher", 0.8, null),
    MINE_LAYER ("Mine Layer", 0.4, null);

    private final String name;
    private final double rarity;
    private final WeaponGenerator generator;
    WeaponType(String name, double rarity, WeaponGenerator generator) {
        this.name = name;
        this.rarity = rarity;
        this.generator = generator;
    }
    public String getName() {
        return name;
    }
    /*public double getRarity() {
        return rarity;
    }*/
    /*public WeaponGenerator getGenerator() {
        return generator;
    }*/

    public static WeaponType generateRandomWeaponType() {
        WeaponType[] types = WeaponType.values();

        double total = 0;
        for(WeaponType type: types) {
            if(type.generator != null)
                total += type.rarity;
        }
        double rand = Math.random() * total;
        for(WeaponType type: types) {
            if(type.generator != null)
                rand -= type.rarity;
            if(rand < 0)
                return type;
        }
        return types[types.length-1];
    }

    public static Weapon generateWeapon(WeaponType type) {
        return type.generator.generateWeapon();
    }

    public static Weapon generateRandomWeapon() {
        return generateWeapon(generateRandomWeaponType());
    }
}
