package ru.samsung.gamestudio;

public class GameSettings {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    public static final String SKIN_PATH = "skin/uiskin.json";

    // Physics settings

    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 70;
     public static final float PPI = SCREEN_HEIGHT / 15f / 32;

    public static final short FLOOR_BIT = 1;
    public static final short WALL_BIT = 2;
    public static final short PLAYER_BIT = 4;
    public static final short ENEMY_BIT = 8;
    public static final short EXIT_BIT = 16;
    public static final short PIT_BIT = 32;
    public static final short COIN_BIT = 64;

}
