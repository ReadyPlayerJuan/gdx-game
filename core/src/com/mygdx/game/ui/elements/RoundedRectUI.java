package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.util.Util;

public class RoundedRectUI extends UI {
    protected NinePatch sprite;
    protected Color color, borderColor;
    protected float borderSize = 0;

    public RoundedRectUI(float cornerSize, Color color) {
        this.color = color;
        sprite = Util.createNinePatch(TextureData.ROUNDED_RECT, cornerSize);
        sprite.setColor(color);
    }

    public RoundedRectUI setBorder(float size, Color color) {
        borderSize = size;
        borderColor = color;
        return this;
    }

    @Override
    public void update(double delta) {
        updateChildren(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(borderSize == 0) {
            sprite.setColor(color);
            sprite.draw(batch, centerX - width / 2, centerY - height / 2, width, height);
        } else {
            sprite.setColor(borderColor);
            sprite.draw(batch, centerX - width / 2, centerY - height / 2, width, height);
            sprite.setColor(color);
            sprite.draw(batch, centerX - width / 2 + borderSize, centerY - height / 2 + borderSize, width - borderSize*2, height - borderSize*2);
        }
        drawChildren(batch);
    }
}
