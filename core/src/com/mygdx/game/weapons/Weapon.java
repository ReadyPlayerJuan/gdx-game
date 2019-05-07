package com.mygdx.game.weapons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.textures.TextureManager;
import com.mygdx.game.weapons.stats.WeaponStat;
import com.mygdx.game.weapons.stats.WeaponStatType;

public abstract class Weapon {
    protected final WeaponType type;
    protected final TextureRegion texture;
    protected final WeaponStat[] availableStats;
    protected final double[][] defaultStats;

    protected String name;

    protected String statNames = "";
    protected String statValues = "";
    protected String statBaseValues = "";
    protected String statRolls = "";

    protected double[][] stats;
    protected double[] variationRolls;

    public Weapon(WeaponType type, TextureData textureSheet, int textureIndex, WeaponStat[] availableStats, double[][] defaultStats) {
        this.type = type;
        this.name = type.getName();
        this.texture = TextureManager.getTextureRegion(textureSheet, textureIndex);
        //this.elementalWeights = elementalWeights;
        this.availableStats = availableStats;
        this.defaultStats = defaultStats;
    }

    protected void randomizeVariationRolls() {
        variationRolls = new double[availableStats.length];
        for(int i = 0; i < availableStats.length; i++) {
            variationRolls[i] = (Math.random() * 2) - 1;
        }
    }

    protected void initStats() {
        stats = new double[availableStats.length][2];

        for(int i = 0; i < availableStats.length; i++) {
            stats[i][0] = defaultStats[i][0] + (defaultStats[i][1] * variationRolls[i]);
            stats[i][1] = stats[i][0];
        }
    }

    public abstract void update(double delta, WeaponController controller);
    protected abstract void fire(double delta, WeaponController controller);

    protected void updateWeaponInfo() {
        StringBuilder nameString = new StringBuilder();
        for(WeaponStat stat: availableStats) {
            nameString.append(stat.getName()).append("\n");
        }
        statNames = nameString.toString();


        StringBuilder valueString = new StringBuilder();
        for(int i = 0; i < availableStats.length; i++) {
            valueString.append(formatStatString(availableStats[i].getStatType(), stats[i][1], availableStats[i].getNumDecimals(), false, true));
            valueString.append("\n");
        }
        statValues = valueString.toString();


        StringBuilder baseValueString = new StringBuilder();
        for(int i = 0; i < availableStats.length; i++) {
            baseValueString.append(formatStatString(availableStats[i].getStatType(), stats[i][0], availableStats[i].getNumDecimals(), false, true));
            baseValueString.append("\n");
        }
        statBaseValues = baseValueString.toString();


        StringBuilder rollString = new StringBuilder();
        for(int i = 0; i < availableStats.length; i++) {
            double value = stats[i][0] - defaultStats[i][0];
            if(value * availableStats[i].getStatBenefitSign() < 0)
                rollString.append("[RED]");
            else if(value * availableStats[i].getStatBenefitSign() > 0)
                rollString.append("[FOREST]");

            rollString.append(formatStatString(availableStats[i].getStatType(), value, availableStats[i].getNumDecimals(), true, true));
            rollString.append(" (");
            rollString.append(formatStatString(WeaponStatType.PERCENTAGE, variationRolls[i], 0, true, false));
            rollString.append("%)[]\n");
        }
        statRolls = rollString.toString();
    }

    private String formatStatString(WeaponStatType type, double value, int numDecimals, boolean sign, boolean postscript) {
        String pre = "";
        String post = "";

        if(sign && value > 0)
            pre = "+";
        switch(type) {
            case PERCENTAGE:
            case PERCENTAGE_BETWEEN_ZERO_AND_ONE:
                value *= 100;
                if(postscript)
                    post = "%";
                break;
            case TIME:
                if(postscript)
                    post = "s";
                break;
            case MULTIPLIER:
                if(postscript)
                    post = "x";
                break;
        }
        return pre + String.format("%." + numDecimals + "f", value) + post;
    }

    public String getName() {
        return name;
    }

    public String getStatNamesString() {
        return statNames;
    }

    public String getStatValuesString() {
        return statValues;
    }

    public String getStatBaseValuesString() {
        return statBaseValues;
    }

    public String getStatRollsString() {
        return statRolls;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeaponStat[] getAvailableStats() {
        return availableStats;
    }

    public WeaponType getType() {
        return type;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public double[][] getDefaultStats() {
        return defaultStats;
    }

    public double[][] getStats() {
        return stats;
    }

    public double[] getStat(WeaponStat stat) {
        return stats[getWeaponStatIndex(stat)];
    }

    public double[] getDefaultStat(WeaponStat stat) {
        return defaultStats[getWeaponStatIndex(stat)];
    }

    private int getWeaponStatIndex(WeaponStat stat) {
        for(int i = 0; i < availableStats.length; i++) {
            if(availableStats[i] == stat)
                return i;
        }
        return -1;
    }
}
