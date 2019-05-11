package com.mygdx.game.weapons;

import com.mygdx.game.entities.Rarity;

public abstract class WeaponGenerator {
    public abstract Weapon generateWeapon();
    public abstract Weapon generateWeapon(Rarity rarity);
}
