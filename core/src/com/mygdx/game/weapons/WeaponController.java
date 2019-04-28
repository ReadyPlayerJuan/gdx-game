package com.mygdx.game.weapons;

import com.mygdx.game.entities.Team;

public interface WeaponController {
    public Team getTeam();
    public double getX();
    public double getY();
    public double getTargetX();
    public double getTargetY();
    public boolean isFiringWeapon();
    public boolean wasFiringWeapon();

    public void kick(double angle, double kick);
}
