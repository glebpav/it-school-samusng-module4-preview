package ru.samsung.gamestudio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

public class GameSettings {

    private static final float baseHeight = 560;
    private static final float baseRatio = Gdx.graphics.getHeight() / baseHeight;

    public static final float SCREEN_WIDTH = Gdx.graphics.getWidth() / baseRatio;
    public static final float SCREEN_HEIGHT = Gdx.graphics.getHeight() / baseRatio;

    public static final String SKIN_PATH = "skin/skin.json";
    private static final String LOCALIZATION_BUNDLE_PATH = "localization/bundle";

    public static final int COIN_VALUE = 50;
    public static final int PLAYER_LIVES = 3;
    public static final int ENEMY_DAMAGE = 1;
    public static final int KILLED_ENEMY_VALUE = 10;
    public static final int BONUS_VALUE = 30;

    // Physics settings

    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.014f;

    public static final short FLOOR_BIT = 1;
    public static final short WALL_BIT = 2;
    public static final short PLAYER_BIT = 4;
    public static final short ENEMY_BIT = 8;
    public static final short EXIT_BIT = 16;
    public static final short PIT_BIT = 32;
    public static final short COIN_BIT = 64;
    public static final short LADDER_BIT = 128;
    public static final short BONUS_BIT = 256;
    public static final short PLAYER_HEAD_BIT = 512;
    public static final short PLAYER_FEET_BIT = 1024;

    public static I18NBundle localizationBundle =
            I18NBundle.createBundle(Gdx.files.internal(LOCALIZATION_BUNDLE_PATH));

}
