package com.mygdx.game.weapons.stats;

import com.badlogic.gdx.graphics.Color;

public enum WeaponRarity {
    WHITE   (0, new Color(1.00f, 1.00f, 1.00f, 1), new Color(0.50f, 0.50f, 0.50f, 1), new Color(0.00f, 0.00f, 0.00f, 1)),
    GREEN   (1, new Color(0.00f, 0.88f, 0.22f, 1), new Color(1.00f, 1.00f, 1.00f, 1), new Color(1.00f, 1.00f, 1.00f, 1)),
    BLUE    (2, new Color(0.28f, 0.30f, 0.91f, 1), new Color(1.00f, 1.00f, 1.00f, 1), new Color(1.00f, 1.00f, 1.00f, 1)),
    PURPLE  (3, new Color(0.58f, 0.23f, 0.80f, 1), new Color(1.00f, 1.00f, 1.00f, 1), new Color(1.00f, 1.00f, 1.00f, 1)),
    ORANGE  (4, new Color(1.00f, 0.62f, 0.00f, 1), new Color(1.00f, 1.00f, 1.00f, 1), new Color(1.00f, 1.00f, 1.00f, 1)),
    RED     (5, new Color(0.88f, 0.18f, 0.14f, 1), new Color(1.00f, 1.00f, 1.00f, 1), new Color(1.00f, 1.00f, 1.00f, 1)),
    PINK    (6, new Color(0.98f, 0.52f, 0.91f, 1), new Color(1.00f, 1.00f, 1.00f, 1), new Color(1.00f, 1.00f, 1.00f, 1));//,
    //GOLD    (7, new Color(1.00f, 0.78f, 0.23f, 1), new Color(1.00f, 1.00f, 1.00f, 1), new Color(1.00f, 1.00f, 1.00f, 1));


    private final double statMultiplierTierIncrease = 1.5;
    private double statMultiplier;
    private Color mainColor, borderColor, textColor;

    WeaponRarity(double tier, Color mainColor, Color borderColor, Color textColor) {
        this.statMultiplier = Math.pow(statMultiplierTierIncrease, tier);
        this.mainColor = mainColor;
        this.borderColor = borderColor;
        this.textColor = textColor;
    }

    public double getStatMultiplier() {
        return statMultiplier;
    }

    public Color getMainColor() {
        return mainColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getTextColor() {
        return textColor;
    }
}
