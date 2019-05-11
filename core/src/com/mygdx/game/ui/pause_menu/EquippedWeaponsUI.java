package com.mygdx.game.ui.pause_menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.PlayerData;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.textures.TextureManager;
import com.mygdx.game.ui.elements.ButtonUI;
import com.mygdx.game.ui.elements.UI;
import com.mygdx.game.ui.graphic_types.RectGT;
import com.mygdx.game.views.PauseMenuView;
import com.mygdx.game.weapons.Weapon;

import java.util.ArrayList;

import static com.mygdx.game.util.Util.makeGray;

public class EquippedWeaponsUI extends UI implements WeaponIconContainer {
    private final PauseMenuView pauseMenuView;
    private final float UI_SCALE;
    private final float padding = 6.0f;
    private final float shadowOffset = 4.0f;

    private WeaponIconUI weaponIcon1, weaponIcon2;
    private ArrayList<WeaponIconUI> weaponIconsSorted;

    public EquippedWeaponsUI(float UI_SCALE, PauseMenuView pauseMenuView) {
        this.UI_SCALE = UI_SCALE;
        this.pauseMenuView = pauseMenuView;
        setContentFit(true, true);
        setVertical(false);

        weaponIconsSorted = new ArrayList<WeaponIconUI>();

        UI column1 = new UI().setContentFit(true, true).setPaddingX(padding);
        UI column2 = new UI().setContentFit(true, true).setPaddingX(padding);

        UI cell1 = new UI().setContentFit(true, true).setPaddingY(padding);
        UI cell2 = new UI().setContentFit(true, true).setPaddingY(padding);
        UI cell3 = new UI().setContentFit(true, true).setPaddingY(padding);
        UI cell4 = new UI().setContentFit(true, true).setPaddingY(padding);

        weaponIcon1 = new WeaponIconUI(pauseMenuView, this, PauseMenuView.WEAPON_ICON_WIDTH, PauseMenuView.WEAPON_ICON_HEIGHT, PauseMenuView.WEAPON_ICON_TITLE_HEIGHT);
        weaponIcon2 = new WeaponIconUI(pauseMenuView, this, PauseMenuView.WEAPON_ICON_WIDTH, PauseMenuView.WEAPON_ICON_HEIGHT, PauseMenuView.WEAPON_ICON_TITLE_HEIGHT);
        weaponIconsSorted.add(weaponIcon1);
        weaponIconsSorted.add(weaponIcon2);

        UI arrowIcon1 = new ButtonUI(makeGray(1.0f, 1.0f), makeGray(0.92f, 1.0f), makeGray(0.84f, 1.0f),
                new RectGT(TextureManager.getTexture(TextureData.ARROW))) {
            public void draw(SpriteBatch batch) {
                graphicType.setColor(PauseMenuView.SHADOW_COLOR).draw(batch, centerX + shadowOffset, centerY - shadowOffset, width, height);
                graphicType.setColor(currentColor).draw(batch, centerX, centerY, width, height);
                drawChildren(batch);
            }
            public void press(double hoverTimer, double pressTimer) {
                swapEqippedWeapons();
            }
            public void hold(double hoverTimer, double pressTimer) {}
            public void release(double hoverTimer, double pressTimer) {}
            public void mouseOver(double hoverTimer) {}
            public void hover(double hoverTimer) {}
            public void mouseLeave(double hoverTimer) {}
        }.setSize(PauseMenuView.WEAPON_ICON_WIDTH, PauseMenuView.WEAPON_ICON_HEIGHT);

        UI arrowIcon2 = new ButtonUI(makeGray(1.0f, 1.0f), makeGray(0.92f, 1.0f), makeGray(0.84f, 1.0f),
                new RectGT(TextureManager.getTexture(TextureData.ARROW))) {
            public void draw(SpriteBatch batch) {
                graphicType.setColor(PauseMenuView.SHADOW_COLOR).draw(batch, centerX + shadowOffset, centerY - shadowOffset, -width, -height);
                graphicType.setColor(currentColor).draw(batch, centerX, centerY, -width, -height);
                drawChildren(batch);
            }
            public void press(double hoverTimer, double pressTimer) {
                swapEqippedWeapons();
            }
            public void hold(double hoverTimer, double pressTimer) {}
            public void release(double hoverTimer, double pressTimer) {}
            public void mouseOver(double hoverTimer) {}
            public void hover(double hoverTimer) {}
            public void mouseLeave(double hoverTimer) {}
        }.setSize(PauseMenuView.WEAPON_ICON_WIDTH, PauseMenuView.WEAPON_ICON_HEIGHT);

        cell1.addChild(weaponIcon1);
        cell2.addChild(arrowIcon1);
        cell3.addChild(arrowIcon2);
        cell4.addChild(weaponIcon2);

        column1.addChild(cell1).addChild(cell2);
        column2.addChild(cell3).addChild(cell4);

        column1.addToParent(this);
        column2.addToParent(this);

        weaponIcon1.setWeapon(PlayerData.getEquippedWeapon(0));
        weaponIcon2.setWeapon(PlayerData.getEquippedWeapon(1));
    }

    private void swapEqippedWeapons() {
        PlayerData.swapEquippedWeapons();

        weaponIcon1.setWeapon(PlayerData.getEquippedWeapon(0));
        weaponIcon2.setWeapon(PlayerData.getEquippedWeapon(1));
    }

    public void updateEquippedWeapons() {
        PlayerData.setEquippedWeapon(0, weaponIcon1.getWeapon());
        PlayerData.setEquippedWeapon(1, weaponIcon2.getWeapon());
    }

    public void updateWeaponIcon(Weapon weapon) {
        if(weaponIcon1.getWeapon().equals(weapon)) {
            weaponIcon1.setWeapon(null);
            weaponIcon1.setWeapon(weapon);
        } else if(weaponIcon2.getWeapon().equals(weapon)) {
            weaponIcon2.setWeapon(null);
            weaponIcon2.setWeapon(weapon);
        }
    }

    @Override
    public void update(double delta) {
        updateChildren(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        //graphicType.setColor(currentColor).draw(batch, centerX, centerY, width, height);
        drawChildren(batch);

        for(WeaponIconUI icon: weaponIconsSorted) {
            icon.drawReal(batch);
        }
    }

    @Override
    public void moveToFront(WeaponIconUI icon) {
        for(int i = 0; i < weaponIconsSorted.size(); i++) {
            if(weaponIconsSorted.get(i).equals(icon)) {
                weaponIconsSorted.add(weaponIconsSorted.remove(i));
                break;
            }
        }
    }
}
