package ru.samsung.gamestudio;

public class GameSettings {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    public static final String SKIN_PATH = "skin/uiskin.json";

    // Physics settings

    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = SCREEN_HEIGHT / 15f / 32;


}
