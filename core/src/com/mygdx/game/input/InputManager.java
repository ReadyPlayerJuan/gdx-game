package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.LinkedList;

public class InputManager implements InputProcessor {
    public static InputManager inputManager;

    public static void init() {
        inputManager = new InputManager();
        Gdx.input.setInputProcessor(inputManager);
    }



    private LinkedList<Double[]> pressedKeys, heldKeys, releasedKeys;
    private double mouseX, mouseY;

    public InputManager() {
        pressedKeys = new LinkedList<Double[]>();
        heldKeys = new LinkedList<Double[]>();
        releasedKeys = new LinkedList<Double[]>();
    }

    public void update(double delta) {
        for(Double[] d: heldKeys) {
            d[1] += delta;
            //System.out.println(d[0] + " " + d[1]);
        }

        pressedKeys.clear();
        releasedKeys.clear();
    }

    @Override
    public boolean keyDown(int keycode) {
        //add to pressed keys list
        pressedKeys.add(new Double[] {(double)keycode, 0.0});
        heldKeys.add(new Double[] {(double)keycode, 0.0});

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //remove from pressed keys list
        for (int i = 0; i < heldKeys.size(); i++) {
            if (heldKeys.get(i)[0] == keycode) {
                releasedKeys.add(heldKeys.remove(i));
                break;
            }
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }



    public LinkedList<Double[]> getPressedKeys() {
        return pressedKeys;
    }

    public LinkedList<Double[]> getHeldKeys() {
        return heldKeys;
    }

    public LinkedList<Double[]> getReleasedKeys() {
        return releasedKeys;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }
}
