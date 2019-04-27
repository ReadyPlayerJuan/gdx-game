package com.mygdx.game.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class TextureManager {
    private static final HashMap<TextureData, Texture> textures = new HashMap<TextureData, Texture>();
    private static final HashMap<TextureData, TextureRegion[]> textureSheets = new HashMap<TextureData, TextureRegion[]>();

    public static void loadTextures() {
        for(TextureData data: TextureData.values()) {
            data.load();
        }
    }

    public static void loadTexture(TextureData data, int sheetRows, int sheetCols) {
        loadTexture(data);

        if(sheetRows != 1 || sheetCols != 1) {
            if(textureSheets.get(data) == null) {
                Texture texture = new Texture(data.getFileName());
                TextureRegion[][] regions = TextureRegion.split(texture, texture.getWidth() / sheetCols, texture.getHeight() / sheetRows);
                TextureRegion[] regionsFlat = new TextureRegion[regions.length * regions[0].length];

                for(int r = 0; r < regions.length; r++) {
                    for(int c = 0; c < regions[0].length; c++) {
                        regionsFlat[r*regions.length + c] = regions[r][c];
                    }
                }

                textureSheets.put(data, regionsFlat);
            }
        }
    }

    public static void loadTexture(TextureData data) {
        if(textures.get(data) == null)
            textures.put(data, new Texture(data.getFileName()));
    }

    public static Texture getTexture(TextureData data) {
        return textures.get(data);
    }

    public static TextureRegion getTextureRegion(TextureData data, int index) {
        return textureSheets.get(data)[index];
    }

    public static Animation<TextureRegion> makeAnimation(TextureData spriteSheet, int startIndex, int endIndex, float frameTime) {
        TextureRegion[] regions = textureSheets.get(spriteSheet);

        TextureRegion[] frames = new TextureRegion[endIndex - startIndex];
        for(int i = startIndex; i < endIndex; i++) {
            frames[i - startIndex] = regions[i];
        }
        return new Animation<TextureRegion>(frameTime, frames);
    }

    public static void dispose() {
        for(TextureData key: textures.keySet()) {
            textures.get(key).dispose();
        }
        textures.clear();
        textureSheets.clear();
    }
}
