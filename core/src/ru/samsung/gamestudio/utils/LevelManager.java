package ru.samsung.gamestudio.utils;


import static ru.samsung.gamestudio.game.GameSettings.localizationBundle;

public class LevelManager {

    private final static Level[] levelsArray = new Level[]{
            new Level(localizationBundle.get("level1Name"), "maps/level4.tmx"),
            new Level(localizationBundle.get("level2Name"), "maps/level5.tmx"),
            new Level(localizationBundle.get("level3Name"), "maps/level6.tmx"),
            new Level(localizationBundle.get("level4Name"), "maps/level4.tmx"),
            new Level(localizationBundle.get("level2Name"), "maps/level2.tmx"),
            new Level(localizationBundle.get("level3Name"), "maps/level2.tmx"),
            new Level(localizationBundle.get("level4Name"), "maps/level2.tmx"),
            new Level(localizationBundle.get("level1Name"), "maps/level2.tmx"),
            new Level(localizationBundle.get("level2Name"), "maps/level2.tmx"),
            new Level(localizationBundle.get("level3Name"), "maps/level2.tmx"),
            new Level(localizationBundle.get("level4Name"), "maps/level2.tmx"),
            new Level(localizationBundle.get("level1Name"), "maps/level2.tmx"),
            new Level(localizationBundle.get("level2Name"), "maps/level2.tmx"),
            new Level(localizationBundle.get("level3Name"), "maps/level2.tmx"),
    };

    public static Level[] getAllLevels() {
        return levelsArray;
    }

    public static Level getLevel(int levelIdx) {
        levelsArray[levelIdx].updateIsAvailable();
        return levelsArray[levelIdx];
    }

    public static boolean isLevelAvailable(int levelIdx) {
        if (levelIdx == 0) return true;
        return MemoryManager.loadLevelState(levelsArray[levelIdx - 1].getName());
    }

}
