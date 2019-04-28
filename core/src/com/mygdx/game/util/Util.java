package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.textures.TextureManager;

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

    public static NinePatch createNinePatch(TextureData data, float cornerSize) {
        Texture texture = TextureManager.getTexture(TextureData.ROUNDED_RECT);
        NinePatch sprite = new NinePatch(texture, texture.getWidth()/3, texture.getWidth()/3, texture.getHeight()/3, texture.getHeight()/3);
        sprite.setBottomHeight(cornerSize);
        sprite.setTopHeight(cornerSize);
        sprite.setLeftWidth(cornerSize);
        sprite.setRightWidth(cornerSize);
        return sprite;
    }
}
