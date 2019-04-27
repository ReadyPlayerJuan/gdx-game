package com.mygdx.game.input;

public enum ControlMapping {
    MOVE_LEFT (29),
    MOVE_RIGHT (32),
    MOVE_UP (51),
    MOVE_DOWN (47),
    FIRE_WEAPON (1000),

    PAUSE_GAME (61);

    public int keyCode;
    ControlMapping(int key) {
        keyCode = key;
    }
}
