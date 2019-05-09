package com.mygdx.game.ui.pause_menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.textures.TextureManager;
import com.mygdx.game.ui.elements.UI;

public class WeaponRollSliderUI extends UI {
    /*private final Color positiveColorOuter = Color.FOREST;
    private final Color positiveColorInner = Color.GREEN;
    private final Color negativeColorOuter = Color.FIREBRICK;
    private final Color negativeColorInner = Color.RED;
    private final float colorBarHeight = 0.75f;

    private Texture texture, positiveTextureOuter, positiveTextureInner, negativeTextureOuter, negativeTextureInner;

    private String minValue;
    private String maxValue;
    private float roll;
    private final float lineSize = 2.0f;

    public WeaponRollSliderUI(Color color) {
        texture = TextureManager.getColorTexture(color);
        positiveTextureOuter = TextureManager.getColorTexture(positiveColorOuter);
        positiveTextureInner = TextureManager.getColorTexture(positiveColorInner);
        negativeTextureOuter = TextureManager.getColorTexture(negativeColorOuter);
        negativeTextureInner = TextureManager.getColorTexture(negativeColorInner);
    }

    public void setInfo(String minValue, String maxValue, float roll) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.roll = roll;
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void draw(SpriteBatch batch) {
        float innerWidth = getInnerWidth();
        float innerHeight = getInnerHeight();

        if(roll > 0) {
            batch.draw(positiveTextureOuter, centerX, centerY - colorBarHeight * innerHeight/2,
                    innerWidth/2 * roll, innerHeight * colorBarHeight);
            batch.draw(positiveTextureInner, centerX + 1, centerY - colorBarHeight * innerHeight/2 + 1,
                    innerWidth/2 * roll - 2, innerHeight * colorBarHeight - 2);
        } else {
            batch.draw(negativeTextureOuter, centerX + innerWidth/2 * roll, centerY - colorBarHeight * innerHeight/2,
                    innerWidth/2 * -roll, innerHeight * colorBarHeight);
            batch.draw(negativeTextureInner, centerX + innerWidth/2 * roll + 1, centerY - colorBarHeight * innerHeight/2 + 1,
                    innerWidth/2 * -roll -2, innerHeight * colorBarHeight - 2);
        }

        batch.draw(texture, centerX - innerWidth/2, centerY - innerHeight/2, lineSize, innerHeight);
        batch.draw(texture, centerX + innerWidth/2 - lineSize, centerY - innerHeight/2, lineSize, innerHeight);
        batch.draw(texture, centerX - innerWidth/2, centerY - lineSize/2, innerWidth, lineSize);
    }*/
}
