package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.mygdx.game.entities.PlayerData;
import com.mygdx.game.ui.elements.*;
import com.mygdx.game.ui.graphic_types.RectGT;
import com.mygdx.game.ui.pause_menu.WeaponIconUI;
import com.mygdx.game.ui.pause_menu.WeaponInfoUI;
import com.mygdx.game.ui.pause_menu.WeaponStatPopupUI;

import static com.mygdx.game.util.Util.makeGray;

public class PauseMenuView extends View {
    private FrameBuffer frameBuffer;
    private SpriteBatch bufferBatch;

    private UI backgroundParent, foregroundParent;
    private UI equippedWeapons;
    private WeaponInfoUI weaponInfo1, weaponInfo2;
    private WeaponStatPopupUI statPopup;

    private float pauseMenuAlpha = 0f;
    private float pauseMenuFadeSpeed = 16.0f;

    private final float topBarHeight = 80f;
    private final float weaponIconWidth = 80f;
    private final float weaponIconTitleHeight = 20f;
    private final float weaponIconHeight = weaponIconWidth + weaponIconTitleHeight;

    private final float topBarAlpha = 0.88f;
    private final Color topBarColor = makeGray(0, topBarAlpha);
    private final Color topBarBorderColor1 = makeGray(0.2f, topBarAlpha);
    private final Color topBarBorderColor2 = makeGray(0.4f, topBarAlpha);

    private final float UI_SCALE = 1.0f;

    public PauseMenuView(View parentView, int width, int height) {
        super(parentView, width, height);

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, true);
        bufferBatch = new SpriteBatch();

        //statPopup = new WeaponStatPopupUI(this, cornerSize, borderSize, bodyColor, darkBodyColor, borderColor, textColor);
        backgroundParent = new UI().setSize(width, height).setPosition(width/2, height/2)
                .setContentFill(true, true)
                .setGraphicType(new RectGT().setColor(makeGray(0.4f, 0.5f)));


        foregroundParent = new UI().setSize(width, height).setPosition(width/2, height/2)
                .setContentAlign(UI.STRETCH, UI.CENTER).setContentFill(false, true);
        UI topBar = new UI().setHeight(topBarHeight * UI_SCALE).setContentFill(true, true)
                .addToParent(foregroundParent);
        UI topBarContainer = new UI().setContentAlign(UI.BOTTOM, UI.LEFT).setPadding(10 * UI_SCALE, 10 * UI_SCALE)
                .setGraphicType(new RectGT().setColor(topBarColor)).addToParent(topBar);
        UI topBarBorder1 = new UI().setHeight(4 * UI_SCALE).setGraphicType(new RectGT().setColor(topBarBorderColor1)).addToParent(topBar);
        UI topBarBorder2 = new UI().setHeight(4 * UI_SCALE).setGraphicType(new RectGT().setColor(topBarBorderColor2)).addToParent(topBar);


        UI botBar = new UI().setHeight(topBarHeight * UI_SCALE).setContentFill(true, true)
                .addToParent(foregroundParent);
        UI botBarBorder2 = new UI().setHeight(4 * UI_SCALE).setGraphicType(new RectGT().setColor(topBarBorderColor2)).addToParent(botBar);
        UI botBarBorder1 = new UI().setHeight(4 * UI_SCALE).setGraphicType(new RectGT().setColor(topBarBorderColor1)).addToParent(botBar);
        UI botBarContainer = new UI().setContentAlign(UI.TOP, UI.LEFT).setPadding(10 * UI_SCALE, 10 * UI_SCALE)
                .setGraphicType(new RectGT().setColor(topBarColor)).addToParent(botBar);


        backgroundParent.format();
        foregroundParent.format();


        equippedWeapons = new UI().setContentFit(true, true);
        WeaponIconUI weaponIcon = new WeaponIconUI(weaponIconWidth, weaponIconHeight, weaponIconTitleHeight);
        weaponIcon.setWeapon(PlayerData.getEquippedWeapon(0));
        equippedWeapons.addChild(weaponIcon);

        equippedWeapons.setPosition(width/2, height/2);
        equippedWeapons.format();

        //weaponLeftInfo.setWeapon(PlayerData.getEquippedWeapon(0));
        //weaponRightInfo.setWeapon(PlayerData.getEquippedWeapon(1));
    }

    @Override
    public void setFocused(boolean focused) {
        super.setFocused(focused);

        backgroundParent.setActive(focused);
        backgroundParent.update(0);

        equippedWeapons.setActive(focused);
        equippedWeapons.update(0);

        foregroundParent.setActive(focused);
        foregroundParent.update(0);
    }

    @Override
    public void update(double delta) {
        if(isFocused)
            pauseMenuAlpha = (float)Math.min(1, pauseMenuAlpha + delta * pauseMenuFadeSpeed);
        else
            pauseMenuAlpha = (float)Math.max(0, pauseMenuAlpha - delta * pauseMenuFadeSpeed);


        backgroundParent.update(delta);
        backgroundParent.format();


        equippedWeapons.update(delta);

        /*if(statPopup.isActive()) {
            statPopup.update(delta);
        }*/

        foregroundParent.update(delta);
        foregroundParent.format();
    }

    @Override
    public void preDraw() {
        frameBuffer.bind();
        Gdx.gl.glClearColor(0, 0, 0, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        bufferBatch.enableBlending();
        bufferBatch.begin();
        backgroundParent.draw(bufferBatch);

        /*if(statPopup.isActive()) {
            statPopup.draw(batch);
        }*/
        equippedWeapons.draw(bufferBatch);


        foregroundParent.draw(bufferBatch);
        bufferBatch.end();

        FrameBuffer.unbind();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.setColor(1, 1, 1, pauseMenuAlpha);
        batch.draw(frameBuffer.getColorBufferTexture(), 0, height, width, -height);
        batch.setColor(1, 1, 1, 1);
    }

    @Override
    public void processViewAction(String action) {

    }

    @Override
    public void dispose() {
        super.dispose();
        frameBuffer.dispose();
        bufferBatch.dispose();
    }
}
