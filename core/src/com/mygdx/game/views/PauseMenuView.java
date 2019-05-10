package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.mygdx.game.input.ControlMapping;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.ui.elements.*;
import com.mygdx.game.ui.graphic_types.RectGT;
import com.mygdx.game.ui.pause_menu.*;
import com.mygdx.game.weapons.Weapon;

import static com.mygdx.game.util.Util.makeGray;

public class PauseMenuView extends View {
    private FrameBuffer frameBuffer;
    private SpriteBatch bufferBatch;

    private UI backgroundParent, foregroundParent;
    private EquippedWeaponsUI equippedWeapons;
    private InventoryUI inventory;
    private WeaponInfoUI weaponInfo1, weaponInfo2;
    private WeaponStatPopupUI statPopup;

    private boolean mousePressed, prevMousePressed;
    private final double dragIconTimerMin = 0.2;
    private boolean recentlyGrabbedIcon;
    private WeaponIconUI grabbedWeaponIcon = null;
    private WeaponIconUI grabbedWeaponOrigin = null;
    //private WeaponIconUI grabbedWeaponTargetIcon = null;

    private float pauseMenuAlpha = 0f;
    private float pauseMenuFadeSpeed = 16.0f;

    private float offsetPct = 0.025f;
    private float offsetMoveSpeed = 4.0f;
    private float targetOffsetX, targetOffsetY, offsetX, offsetY;

    public static final float TOP_BAR_HEIGHT = 80f;
    public static final float WEAPON_ICON_WIDTH = 80f;
    public static final float WEAPON_ICON_TITLE_HEIGHT = 20f;
    public static final float WEAPON_ICON_HEIGHT = WEAPON_ICON_WIDTH + WEAPON_ICON_TITLE_HEIGHT;
    public static final Color SHADOW_COLOR = makeGray(0, 0.65f);

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
        UI topBar = new UI().setHeight(TOP_BAR_HEIGHT * UI_SCALE).setContentFill(true, true)
                .addToParent(foregroundParent);
        UI topBarContainer = new UI().setContentAlign(UI.BOTTOM, UI.LEFT).setPadding(10 * UI_SCALE, 10 * UI_SCALE)
                .setGraphicType(new RectGT().setColor(topBarColor)).addToParent(topBar);
        UI topBarBorder1 = new UI().setHeight(4 * UI_SCALE).setGraphicType(new RectGT().setColor(topBarBorderColor1)).addToParent(topBar);
        UI topBarBorder2 = new UI().setHeight(4 * UI_SCALE).setGraphicType(new RectGT().setColor(topBarBorderColor2)).addToParent(topBar);


        UI botBar = new UI().setHeight(TOP_BAR_HEIGHT * UI_SCALE).setContentFill(true, true)
                .addToParent(foregroundParent);
        UI botBarBorder2 = new UI().setHeight(4 * UI_SCALE).setGraphicType(new RectGT().setColor(topBarBorderColor2)).addToParent(botBar);
        UI botBarBorder1 = new UI().setHeight(4 * UI_SCALE).setGraphicType(new RectGT().setColor(topBarBorderColor1)).addToParent(botBar);
        UI botBarContainer = new UI().setContentAlign(UI.TOP, UI.LEFT).setPadding(10 * UI_SCALE, 10 * UI_SCALE)
                .setGraphicType(new RectGT().setColor(topBarColor)).addToParent(botBar);


        backgroundParent.format();
        foregroundParent.format();


        equippedWeapons = new EquippedWeaponsUI(UI_SCALE, this);
        equippedWeapons.format();

        inventory = new InventoryUI(UI_SCALE, this);
        inventory.format();

        //grabbedWeaponIcon = new WeaponIconUI(this, WEAPON_ICON_WIDTH, WEAPON_ICON_HEIGHT, WEAPON_ICON_TITLE_HEIGHT);

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

        inventory.setActive(focused);
        inventory.update(0);

        foregroundParent.setActive(focused);
        foregroundParent.update(0);
    }

    @Override
    public void update(double delta) {
        if(isFocused)
            pauseMenuAlpha = (float)Math.min(1, pauseMenuAlpha + delta * pauseMenuFadeSpeed);
        else
            pauseMenuAlpha = (float)Math.max(0, pauseMenuAlpha - delta * pauseMenuFadeSpeed);

        double mouseX = InputManager.getMouseX();
        double mouseY = InputManager.getFlippedMouseY();
        targetOffsetX = -(float)(mouseX - width/2) * offsetPct;
        targetOffsetY = -(float)(mouseY - height/2) * offsetPct;
        offsetX += (float)(Math.signum(targetOffsetX - offsetX) * Math.min(1, delta * offsetMoveSpeed) * Math.abs(targetOffsetX - offsetX));
        offsetY += (float)(Math.signum(targetOffsetY - offsetY) * Math.min(1, delta * offsetMoveSpeed) * Math.abs(targetOffsetY - offsetY));

        prevMousePressed = mousePressed;
        mousePressed = (InputManager.keyPressed(ControlMapping.CLICK_LEFT) || InputManager.keyHeld(ControlMapping.CLICK_LEFT));


        if(pauseMenuAlpha > 0) {
            backgroundParent.update(delta);
            //backgroundParent.format();


            equippedWeapons.setPosition(width * (1f / 3) + (int) offsetX, height / 2 + (int) offsetY);
            equippedWeapons.format();
            equippedWeapons.update(delta);

            inventory.setPosition(width * (2f / 3) + (int) offsetX, height / 2 + (int) offsetY);
            inventory.format();
            inventory.update(delta);

            if(grabbedWeaponIcon != null) {
                grabbedWeaponIcon.setPosition(grabbedWeaponOrigin.getCenterX(), grabbedWeaponOrigin.getCenterY());
                grabbedWeaponIcon.setTargetOffset(
                        (float) mouseX - grabbedWeaponOrigin.getCenterX(),
                        (float) mouseY - grabbedWeaponOrigin.getCenterY());
                grabbedWeaponIcon.update(delta);
                //grabbedWeaponIcon.format();

            /*if(!mousePressed && prevMousePressed && !recentlyGrabbedIcon) {
                dropIcon();
            }*/
            }
            recentlyGrabbedIcon = false;


            foregroundParent.update(delta);
            //foregroundParent.format();
        }
    }

    public void pressIcon(WeaponIconUI icon, double hoverTimer, double pressTimer) {

    }

    public void holdIcon(WeaponIconUI icon, double hoverTimer, double pressTimer) {

    }

    public void releaseIcon(WeaponIconUI icon, double hoverTimer, double pressTimer) {
        if(grabbedWeaponIcon == null && icon.getWeapon() != null) {
            grabIcon(icon);
        } else if(grabbedWeaponIcon != null && !recentlyGrabbedIcon) {
            dropIcon(icon);
        }
    }

    public void mouseOverIcon(WeaponIconUI icon, double hoverTimer) {
        if(grabbedWeaponIcon != null) {
            //grabbedWeaponTargetIcon = icon;
        }
    }

    public void hoverIcon(WeaponIconUI icon, double hoverTimer) {

    }

    public void mouseLeaveIcon(WeaponIconUI icon, double hoverTimer) {
        if(grabbedWeaponIcon != null) {
            //grabbedWeaponTargetIcon = null;
        }
    }

    private void grabIcon(WeaponIconUI icon) {
        System.out.println("grabbed");
        grabbedWeaponIcon = new WeaponIconUI(null, null, WEAPON_ICON_WIDTH, WEAPON_ICON_HEIGHT, WEAPON_ICON_TITLE_HEIGHT);
        grabbedWeaponIcon.setWeapon(icon.getWeapon());
        grabbedWeaponOrigin = icon.setWeapon(null);
        recentlyGrabbedIcon = true;
    }

    private void dropIcon(WeaponIconUI target) {
        if(target != null) {
            Weapon temp = target.getWeapon();

            target.setWeapon(grabbedWeaponIcon.getWeapon());
            target.setOffset(
                    grabbedWeaponIcon.getCenterX() + grabbedWeaponIcon.getOffsetX() - target.getCenterX(),
                    grabbedWeaponIcon.getCenterY() + grabbedWeaponIcon.getOffsetY() - target.getCenterY());
            target.setTargetOffset(0, 0);
            target.update(0);

            if(temp != null) {
                System.out.println("swapped");

                grabbedWeaponOrigin.setWeapon(temp);
                grabbedWeaponOrigin.setOffset(
                        target.getCenterX() + target.getOffsetX() - grabbedWeaponOrigin.getCenterX(),
                        target.getCenterY() + target.getOffsetY() - grabbedWeaponOrigin.getCenterY());
                grabbedWeaponOrigin.setTargetOffset(0, 0);
                grabbedWeaponOrigin.update(0);
                grabbedWeaponOrigin.moveToFront();
            } else {
                System.out.println("dropped");
            }

            target.moveToFront();
        } else {
            System.out.println("returned");

            grabbedWeaponOrigin.setWeapon(grabbedWeaponIcon.getWeapon());
            grabbedWeaponOrigin.setOffset(
                    grabbedWeaponIcon.getCenterX() + grabbedWeaponIcon.getOffsetX() - grabbedWeaponOrigin.getCenterX(),
                    grabbedWeaponIcon.getCenterY() + grabbedWeaponIcon.getOffsetY() - grabbedWeaponOrigin.getCenterY());
            grabbedWeaponOrigin.setTargetOffset(0, 0);
            grabbedWeaponOrigin.update(0);
            grabbedWeaponOrigin.moveToFront();
        }
        equippedWeapons.updateEquippedWeapons();

        grabbedWeaponIcon = null;
        grabbedWeaponOrigin = null;
    }

    @Override
    public void preDraw() {
        if(pauseMenuAlpha > 0) {
            frameBuffer.bind();
            Gdx.gl.glClearColor(0, 0, 0, 0.0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                    (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV: 0));

            bufferBatch.enableBlending();

            bufferBatch.begin();
            backgroundParent.draw(bufferBatch);

        /*if(statPopup.isActive()) {
            statPopup.draw(batch);
        }*/

            equippedWeapons.draw(bufferBatch);

            inventory.draw(bufferBatch);

            if(grabbedWeaponIcon != null)
                grabbedWeaponIcon.drawReal(bufferBatch);


            foregroundParent.draw(bufferBatch);
            bufferBatch.end();

            FrameBuffer.unbind();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(pauseMenuAlpha > 0) {
            batch.setColor(1, 1, 1, pauseMenuAlpha);
            batch.draw(frameBuffer.getColorBufferTexture(), 0, height, width, -height);
            batch.setColor(1, 1, 1, 1);
        }
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
