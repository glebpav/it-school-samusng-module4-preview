package ru.samsung.gamestudio.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MemoryManager {

    private static final Preferences preferences = Gdx.app.getPreferences("User saves");

    public static void saveLevelState(String levelName, boolean isPassed) {
        preferences.putBoolean(levelName + "-state", isPassed);
        preferences.flush();
    }

    public static boolean loadLevelState(String levelName) {
        return preferences.getBoolean(levelName + "-state", false);
    }

}
