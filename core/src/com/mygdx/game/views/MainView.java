package com.mygdx.game.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainView extends View {
    private View gameView;

    public MainView(int width, int height) {
        super(null, width, height);

        gameView = new GameView(this, width, height);
    }

    @Override
    public void update(double delta) {
        updateSubViews(delta);
    }

    @Override
    public void preDraw() {
        preDrawSubViews();
    }

    @Override
    public void draw(SpriteBatch batch) {
        gameView.draw(batch);
    }

    @Override
    public void processViewAction(String action) {
        System.out.println("UNHANDLED VIEW ACTION: " + action);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
