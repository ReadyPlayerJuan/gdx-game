package com.mygdx.game.util;

public class Util {
    public static double skewPctPow(double pct, double pow) {
        return skewPctPow(pct, pow, pow);
    }

    public static double skewPctPow(double pct, double powL, double powR) {
        if(pct < 0.5) {
            return Math.pow(pct * 2, powL) * 0.5;
        } else {
            return 1 - (Math.pow((1 - pct) * 2, powR) * 0.5);
        }
    }

    public static double mix(double a, double b, double pct) {
        return (a * (1 - pct)) + (b * pct);
    }

    public static int choose(int... ints) {
        return ints[(int)(ints.length * Math.random())];
    }
}
