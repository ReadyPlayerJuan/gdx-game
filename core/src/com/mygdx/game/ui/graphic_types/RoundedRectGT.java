package com.mygdx.game.ui.graphic_types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.util.Util;

public class RoundedRectGT extends GraphicType {
    protected NinePatch sprite;
    protected Color color;

    public RoundedRectGT(float cornerSize) {
        sprite = Util.createNinePatch(TextureData.ROUNDED_RECT, cornerSize);
    }

    @Override
    public GraphicType setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public void draw(SpriteBatch batch, float centerX, float centerY, float width, float height) {
        sprite.setColor(color);
        sprite.draw(batch, centerX - width / 2, centerY - height / 2, width, height);
    }
}
