package com.mygdx.game.ui.graphic_types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.textures.TextureManager;
import com.mygdx.game.util.Util;

public class RectGT extends GraphicType {
    protected Texture texture;
    protected TextureRegion textureRegion;
    protected Color color = Color.WHITE;

    public RectGT(Texture texture) {
        this.texture = texture;
    }

    public RectGT(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public RectGT() {
        this.texture = TextureManager.getColorTexture(Color.WHITE);
    }

    @Override
    public GraphicType setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public void draw(SpriteBatch batch, float centerX, float centerY, float width, float height) {
        batch.setColor(color);
        if(texture != null)
            batch.draw(texture, centerX - width/2, centerY - height/2, width, height);
        else
            batch.draw(textureRegion, centerX - width/2, centerY - height/2, width, height);
        batch.setColor(Color.WHITE);
    }
}
