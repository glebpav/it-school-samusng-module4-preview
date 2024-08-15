package ru.samsung.gamestudio.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LevelManager {

    private final static Level[] levelsArray = new Level[] {
            new Level("First fight", "maps/level4.tmx"),
            new Level("New enemies", "maps/level5.tmx"),
            new Level("Wow, ladder", "maps/level4.tmx"),
            new Level("Cool level", "maps/level4.tmx"),
            new Level("Empty test", "maps/level2.tmx"),
            new Level("Empty test", "maps/level2.tmx"),
            new Level("Empty test", "maps/level2.tmx"),
            new Level("Empty test", "maps/level2.tmx"),
            new Level("Empty test", "maps/level2.tmx"),
            new Level("Empty test", "maps/level2.tmx"),
            new Level("Empty test", "maps/level2.tmx"),
            new Level("Empty test", "maps/level2.tmx"),
            new Level("Empty test", "maps/level2.tmx"),
            new Level("Empty test", "maps/level2.tmx"),
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
