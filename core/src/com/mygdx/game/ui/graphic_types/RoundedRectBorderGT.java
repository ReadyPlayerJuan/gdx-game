package com.mygdx.game.ui.graphic_types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.util.Util;

public class RoundedRectBorderGT extends GraphicType {
    protected NinePatch sprite;
    protected Color color;
    protected float[] borderSize;
    protected Color[] borderColor;

    public RoundedRectBorderGT(float cornerSize) {
        sprite = Util.createNinePatch(TextureData.ROUNDED_RECT, cornerSize);
    }

    @Override
    public GraphicType setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public GraphicType setBorder(float[] borderSize, Color[] borderColor) {
        this.borderColor = borderColor;
        this.borderSize = borderSize;
        return this;
    }

    @Override
    public void draw(SpriteBatch batch, float centerX, float centerY, float width, float height) {
        for(int i = 0; i <= borderSize.length; i++) {
            if(i < borderSize.length)
                sprite.setColor(borderColor[i]);
            else
                sprite.setColor(color);

            sprite.draw(batch, centerX - width/2, centerY - height/2, width, height);

            if(i < borderSize.length) {
                width -= borderSize[i]*2;
                height -= borderSize[i]*2;
            }
        }
    }
}
