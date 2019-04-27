package com.mygdx.game.textures;

public enum TextureData {
    TEST_IMAGE ("badlogic.jpg"),
    PLAYER_SHEET ("test_sprite_1.png", 2, 2);

    private String fileName;
    private int sheetRows, sheetCols;

    TextureData(String fileName) {
        this.fileName = fileName;
        sheetRows = 1;
        sheetCols = 1;
    }

    TextureData(String fileName, int sheetRows, int sheetCols) {
        this.fileName = fileName;
        this.sheetRows = sheetRows;
        this.sheetCols = sheetCols;
    }

    public String getFileName() {
        return fileName;
    }

    public void load() {
        TextureManager.loadTexture(this, sheetRows, sheetCols);
    }
}
