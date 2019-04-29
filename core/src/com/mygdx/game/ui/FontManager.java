package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontManager {
    public static BitmapFont aireExterior24;
    public static BitmapFont aireExterior36;
    public static BitmapFont aireExterior48;

    public static void init() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("AireExterior.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.size = 24;
        aireExterior24 = generator.generateFont(parameter);
        //debugFont24.getData().markupEnabled = true;

        parameter.size = 36;
        aireExterior36 = generator.generateFont(parameter);

        parameter.size = 48;
        aireExterior48 = generator.generateFont(parameter);

        generator.dispose();
    }

    public static void dispose() {
        aireExterior24.dispose();
        aireExterior36.dispose();
        aireExterior48.dispose();
    }
}
