package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class SwapperUI extends UI {
    private HashMap<Object, UI> views;
    private HashMap<Object, Boolean> viewVisible;

    public SwapperUI() {
        super.setContentFill(true, true);

        views = new HashMap<Object, UI>();
        viewVisible = new HashMap<Object, Boolean>();
    }

    @Override
    public UI addChild(UI child) {
        System.out.println("ERROR: CAN'T ADD DIRECT CHILD TO SWAPPER UI");
        return null;
    }

    @Override
    public void setActive(boolean visible) {
        this.active = visible;
        if(!visible) {
            for(Object key: views.keySet()) {
                views.get(key).setActive(false);
            }
        } else {
            for(Object key: views.keySet()) {
                views.get(key).setActive(viewVisible.get(key));
            }
        }
    }

    public SwapperUI addChild(Object key, UI child) {
        views.put(key, child);
        viewVisible.put(key, false);
        return this;
    }

    public SwapperUI setViewVisible(Object key, boolean visible) {
        viewVisible.put(key, visible);
        views.get(key).setActive(visible && this.active);
        return this;
    }

    public SwapperUI setAllVisible(boolean visible) {
        for(Object key: views.keySet()) {
            viewVisible.put(key, visible);
            views.get(key).setActive(visible && this.active);
        }
        return this;
    }

    private void setCurrent(Object key) {
        children.clear();
        children.add(views.get(key));
    }

    @Override
    public void update(double delta) {
        updateChildren(delta);
    }

    @Override
    public void updateChildren(double delta) {
        for(Object key: views.keySet()) {
            setCurrent(key);
            super.updateChildren(delta);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        drawChildren(batch);
    }

    @Override
    public void drawChildren(SpriteBatch batch) {
        for(Object key: views.keySet()) {
            if(viewVisible.get(key)) {
                setCurrent(key);
                super.drawChildren(batch);
            }
        }
    }

    @Override
    public void format() {
        for(Object key: views.keySet()) {
            setCurrent(key);
            super.format();
        }
    }

    @Override
    public UI setContentAlign(int contentAlignVertical, int contentAlignHorizontal) {
        System.out.println("ERROR: CAN'T SET FORMATTING FOR SWAPPER UI");
        return null;
    }

    @Override
    public UI setContentFill(boolean fillContentsVertical, boolean fillContentsHorizontal) {
        System.out.println("ERROR: CAN'T SET FORMATTING FOR SWAPPER UI");
        return null;
    }

    @Override
    public UI setContentFit(boolean fitContentsVertical, boolean fitContentsHorizontal) {
        System.out.println("ERROR: CAN'T SET FORMATTING FOR SWAPPER UI");
        return null;
    }

    @Override
    public UI setVertical(boolean vertical) {
        System.out.println("ERROR: CAN'T SET FORMATTING FOR SWAPPER UI");
        return null;
    }
}
