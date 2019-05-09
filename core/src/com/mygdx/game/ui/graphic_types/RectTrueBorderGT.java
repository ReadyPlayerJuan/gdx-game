package com.mygdx.game.ui.graphic_types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.textures.TextureManager;

public class RectTrueBorderGT extends GraphicType {
    protected Texture texture;
    protected Color color;
    protected float[] borderSize;
    protected Color[] borderColor;

    public RectTrueBorderGT(Texture texture) {
        this.texture = texture;
    }

    public RectTrueBorderGT() {
        this.texture = TextureManager.getColorTexture(Color.WHITE);
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
                batch.setColor(borderColor[i]);
            else
                batch.setColor(color);


            if(i < borderSize.length) {
                float border = borderSize[i];
                batch.draw(texture, centerX - width/2, centerY - height/2, width, border);
                batch.draw(texture, centerX - width/2, centerY + height/2 - border, width, border);
                batch.draw(texture, centerX - width/2, centerY - height/2 + border, border, height - border*2);
                batch.draw(texture, centerX + width/2 - border, centerY - height/2 + border, border, height - border*2);

                width -= border*2;
                height -= border*2;
            } else {
                batch.draw(texture, centerX - width/2, centerY - height/2, width, height);
            }
        }
        batch.setColor(Color.WHITE);
    }
}
