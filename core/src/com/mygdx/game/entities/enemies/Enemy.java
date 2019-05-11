package com.mygdx.game.entities.enemies;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Rarity;
import com.mygdx.game.entities.Team;
import com.mygdx.game.util.Util;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.weapons.WeaponType;

public abstract class Enemy extends Entity {
    private final double LOOT_QUANTITY_VARIATION = 3;
    private final double LOWER_RARITY_DROP_CHANCE = 0.20;
    private final double HIGHER_RARITY_DROP_CHANCE = 0.05;
    protected double lootQuantity;
    protected Weapon[] loot;
    protected Rarity rarity;

    public Enemy(Team team, Rarity rarity, boolean autoGenerateLoot, double lootQuantity, double x, double y) {
        super(team, x, y);

        this.rarity = rarity;
        this.lootQuantity = lootQuantity;

        if(autoGenerateLoot)
            generateLoot();
    }

    /*public Enemy(Team team, double x, double y) {
        this(team, 0, 0, x, y);
    }*/

    protected void generateLoot() {
        int numDrops = (int)Math.floor(lootQuantity * Util.weightedRandom(LOOT_QUANTITY_VARIATION) + Math.random());
        //System.out.println(numDrops + " " + lootQuantity + " " + Util.weightedRandom(LOOT_QUANTITY_VARIATION));

        loot = new Weapon[numDrops];
        for(int i = 0; i < numDrops; i++) {
            Rarity rarity = this.rarity;
            double rand = Math.random();
            if(rand < LOWER_RARITY_DROP_CHANCE)
                rarity = rarity.neighboringRarity(-1);
            else if(rand > 1 - HIGHER_RARITY_DROP_CHANCE)
                rarity = rarity.neighboringRarity(1);

            loot[i] = WeaponType.generateWeapon(rarity);
        }
    }

    protected void dropLoot() {
        for(Weapon weapon: loot) {
            new WeaponDrop(weapon, x, y);
        }
        loot = new Weapon[0];
    }
}
