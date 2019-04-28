package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.LinkedList;

public class InputManager implements InputProcessor {
    public static InputManager inputManager;

    private static LinkedList<Double[]> pressedKeys = new LinkedList<Double[]>();
    private static LinkedList<Double[]> heldKeys = new LinkedList<Double[]>();
    private static LinkedList<Double[]> releasedKeys = new LinkedList<Double[]>();
    private static double mouseX, mouseY;

    public static void init() {
        inputManager = new InputManager();
        Gdx.input.setInputProcessor(inputManager);
    }

    public static void update(double delta) {
        for(Double[] d: heldKeys) {
            d[1] += delta;
            //System.out.println(d[0] + " " + d[1]);
        }

        pressedKeys.clear();
        releasedKeys.clear();
    }

    private static void processKeyPress(int keycode) {
        //add to pressed keys list
        pressedKeys.add(new Double[] {(double)keycode, 0.0});
        heldKeys.add(new Double[] {(double)keycode, 0.0});
    }

    private static void processKeyRelease(int keycode) {
        //remove from pressed keys list
        for (int i = 0; i < heldKeys.size(); i++) {
            if (heldKeys.get(i)[0] == keycode) {
                releasedKeys.add(heldKeys.remove(i));
                break;
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        processKeyPress(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        processKeyRelease(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        processKeyPress(button + 1000);
        mouseX = screenX;
        mouseY = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        processKeyRelease(button + 1000);
        mouseX = screenX;
        mouseY = screenY;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseX = screenX;
        mouseY = screenY;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseX = screenX;
        mouseY = screenY;
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }



    public static LinkedList<Double[]> getPressedKeys() {
        return pressedKeys;
    }

    public static LinkedList<Double[]> getHeldKeys() {
        return heldKeys;
    }

    public static LinkedList<Double[]> getReleasedKeys() {
        return releasedKeys;
    }

    public static boolean keyPressed(int keycode) {
        for(Double[] d: pressedKeys) {
            if(d[0] == keycode) {
                return true;
            }
        }
        return false;
    }

    public static boolean keyHeld(int keycode) {
        for(Double[] d: heldKeys) {
            if(d[0] == keycode) {
                return true;
            }
        }
        return false;
    }

    public static boolean keyReleased(int keycode) {
        for(Double[] d: releasedKeys) {
            if(d[0] == keycode) {
                return true;
            }
        }
        return false;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }
}
