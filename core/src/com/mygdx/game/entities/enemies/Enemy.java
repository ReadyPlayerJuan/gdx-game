package com.mygdx.game.entities.enemies;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Team;
import com.mygdx.game.util.Util;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.weapons.WeaponType;

public abstract class Enemy extends Entity {
    private final double LOOT_QUANTITY_VARIATION = 3;
    protected double lootRarity, lootQuantity;
    protected boolean autoGenerateLoot = true;
    protected Weapon[] loot;

    public Enemy(Team team, double lootRarity, double lootQuantity, double x, double y) {
        super(team, x, y);

        this.lootRarity = lootRarity;
        this.lootQuantity = lootQuantity;

        if(autoGenerateLoot)
            generateLoot();
    }

    public Enemy(Team team, double x, double y) {
        this(team, 0, 0, x, y);
    }

    protected void generateLoot() {
        int numDrops = (int)Math.floor(lootQuantity * Util.weightedRandom(LOOT_QUANTITY_VARIATION) + Math.random());
        //System.out.println(numDrops + " " + lootQuantity + " " + Util.weightedRandom(LOOT_QUANTITY_VARIATION));

        loot = new Weapon[numDrops];
        for(int i = 0; i < numDrops; i++) {
            loot[i] = WeaponType.generateRandomWeapon();
        }
    }

    protected void dropLoot() {
        for(Weapon weapon: loot) {
            new WeaponDrop(weapon, x, y);
        }
        loot = new Weapon[0];
    }
}
