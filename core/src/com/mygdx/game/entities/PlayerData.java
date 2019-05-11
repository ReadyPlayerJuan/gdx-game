package com.mygdx.game.entities;

import com.mygdx.game.weapons.Weapon;

public class PlayerData {
    public static final int INVENTORY_WIDTH = 6;
    public static final int INVENTORY_HEIGHT = 4;
    public static final int INVENTORY_NUM_PAGES = 3;
    private static final int INVENTORY_CAPACITY = INVENTORY_WIDTH * INVENTORY_HEIGHT * INVENTORY_NUM_PAGES;

    private static Weapon[] equippedWeapons = {null, null};
    private static Weapon[] inventory = new Weapon[INVENTORY_CAPACITY];
    private static int numUsedSlots = 0;

    public static void setEquippedWeapon(int slot, Weapon weapon) {
        equippedWeapons[slot] = weapon;
    }

    public static Weapon getEquippedWeapon(int slot) {
        return equippedWeapons[slot];
    }

    public static void swapEquippedWeapons() {
        Weapon temp = equippedWeapons[1];
        equippedWeapons[1] = equippedWeapons[0];
        equippedWeapons[0] = temp;
    }

    public static boolean addWeaponToInventory(Weapon weapon) {
        if(numUsedSlots == INVENTORY_CAPACITY)
            return false;

        for(int i = 0; i < INVENTORY_CAPACITY; i++) {
            if(inventory[i] == null) {
                inventory[i] = weapon;
                break;
            }
        }
        numUsedSlots++;
        return true;
    }

    public static void setInventoryWeapon(int slot, Weapon weapon) {
        inventory[slot] = weapon;
    }

    public static Weapon[] getInventory() {
        return inventory;
    }
}
