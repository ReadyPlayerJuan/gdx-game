package com.mygdx.game.ui.pause_menu;

import com.mygdx.game.weapons.Weapon;

public interface WeaponIconContainer {
    public void moveToFront(WeaponIconUI icon);
    public void updateWeaponIcon(Weapon weapon);
}
