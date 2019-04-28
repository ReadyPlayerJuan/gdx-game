package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontManager {
    public static BitmapFont debugFont24;
    public static BitmapFont debugFont48;

    public static void init() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("AireExterior.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.size = 24;
        debugFont24 = generator.generateFont(parameter);
        //debugFont24.getData().markupEnabled = true;

        parameter.size = 48;
        debugFont48 = generator.generateFont(parameter);

        generator.dispose();
    }

    public static void dispose() {
        debugFont24.dispose();
        debugFont48.dispose();
    }
}
