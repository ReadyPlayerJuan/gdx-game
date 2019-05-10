package com.mygdx.game.ui.pause_menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.PlayerData;
import com.mygdx.game.ui.elements.UI;
import com.mygdx.game.views.PauseMenuView;
import com.mygdx.game.weapons.Weapon;

import java.util.ArrayList;

public class InventoryUI extends UI implements WeaponIconContainer {
    private final PauseMenuView pauseMenuView;
    private final float UI_SCALE;
    private final float padding = 6.0f;
    //private final float shadowOffset = 4.0f;

    private WeaponIconUI[] weaponIcons;
    private ArrayList<WeaponIconUI> weaponIconsSorted;

    public InventoryUI(float UI_SCALE, PauseMenuView pauseMenuView) {
        this.UI_SCALE = UI_SCALE;
        this.pauseMenuView = pauseMenuView;
        setContentFit(true, true);
        setVertical(true);

        weaponIconsSorted = new ArrayList<WeaponIconUI>();

        UI[] rows = new UI[PlayerData.INVENTORY_HEIGHT];
        for(int i = 0; i < PlayerData.INVENTORY_HEIGHT; i++) {
            rows[i] = new UI().setVertical(false).setContentFit(true, true).setPaddingY(padding);
        }

        UI[] cells = new UI[PlayerData.INVENTORY_WIDTH * PlayerData.INVENTORY_HEIGHT];
        weaponIcons = new WeaponIconUI[PlayerData.INVENTORY_WIDTH * PlayerData.INVENTORY_HEIGHT];
        for(int i = 0; i < PlayerData.INVENTORY_WIDTH * PlayerData.INVENTORY_HEIGHT; i++) {
            cells[i] = new UI().setContentFit(true, true).setPaddingX(padding);
            weaponIcons[i] = new WeaponIconUI(pauseMenuView, this, PauseMenuView.WEAPON_ICON_WIDTH, PauseMenuView.WEAPON_ICON_HEIGHT, PauseMenuView.WEAPON_ICON_TITLE_HEIGHT);
            weaponIcons[i].addToParent(cells[i]);
            weaponIconsSorted.add(weaponIcons[i]);
        }

        for(int r = 0; r < PlayerData.INVENTORY_HEIGHT; r++) {
            for(int c = 0; c < PlayerData.INVENTORY_WIDTH; c++) {
                rows[r].addChild(cells[(r * PlayerData.INVENTORY_WIDTH) + c]);
            }
        }

        for(int i = 0; i < PlayerData.INVENTORY_HEIGHT; i++) {
            rows[i].addToParent(this);
        }

        setPage(0);
    }

    private void setPage(int page) {
        Weapon[] inventory = PlayerData.getInventory();
        for(int r = 0; r < PlayerData.INVENTORY_HEIGHT; r++) {
            for(int c = 0; c < PlayerData.INVENTORY_WIDTH; c++) {
                weaponIcons[(r * PlayerData.INVENTORY_WIDTH) + c].setWeapon(
                        inventory[(page * PlayerData.INVENTORY_WIDTH * PlayerData.INVENTORY_HEIGHT) + (r * PlayerData.INVENTORY_WIDTH) + c]);
            }
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
