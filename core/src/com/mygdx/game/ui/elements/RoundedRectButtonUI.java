package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.util.Util;

public abstract class RoundedRectButtonUI extends ButtonUI {
    protected NinePatch sprite;
    protected Color borderColor;
    protected float borderSize = 0;

    public RoundedRectButtonUI(float cornerSize, Color defaultColor, Color hoverColor, Color pressColor) {
        super(defaultColor, hoverColor, pressColor);

        sprite = Util.createNinePatch(TextureData.ROUNDED_RECT, cornerSize);
    }

    public RoundedRectButtonUI setBorder(float size, Color color) {
        borderSize = size;
        borderColor = color;
        return this;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(borderSize == 0) {
            sprite.setColor(currentColor);
            sprite.draw(batch, centerX - width / 2, centerY - height / 2, width, height);
        } else {
            sprite.setColor(borderColor);
            sprite.draw(batch, centerX - width / 2, centerY - height / 2, width, height);
            sprite.setColor(currentColor);
            sprite.draw(batch, centerX - width / 2 + borderSize, centerY - height / 2 + borderSize, width - borderSize*2, height - borderSize*2);
        }
        drawChildren(batch);
    }
}
