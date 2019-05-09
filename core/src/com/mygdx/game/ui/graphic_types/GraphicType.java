package com.mygdx.game.ui.graphic_types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GraphicType {
    public GraphicType setColor(Color color) {
        System.out.println("TRYING TO IMPROPERLY SET COLOR ON GRAPHICS TYPE");
        return this;
    }

    public GraphicType setBorder(float[] borderSize, Color[] borderColor) {
        System.out.println("TRYING TO IMPROPERLY SET BORDER ON GRAPHICS TYPE");
        return this;
    }

    public abstract void draw(SpriteBatch batch, float centerX, float centerY, float width, float height);
}
