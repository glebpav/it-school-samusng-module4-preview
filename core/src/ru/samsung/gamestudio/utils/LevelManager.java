package ru.samsung.gamestudio.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LevelManager {

    private final static Level[] levelsArray = new Level[] {
            new Level("First fight", "maps/level4.tmx"),
            new Level("New enemies", "maps/level4.tmx"),
            new Level("Wow, ladder", "maps/level4.tmx"),
            new Level("Cool level", "maps/level4.tmx"),
            new Level(" ", "maps/level2.tmx"),
            new Level(" ", "maps/level2.tmx"),
            new Level(" ", "maps/level2.tmx"),
            new Level(" ", "maps/level2.tmx"),
            new Level(" ", "maps/level2.tmx"),
            new Level(" ", "maps/level2.tmx"),
            new Level(" ", "maps/level2.tmx"),
            new Level(" ", "maps/level2.tmx"),
            new Level(" ", "maps/level2.tmx"),
            new Level(" ", "maps/level2.tmx"),
    };

    public static Level[] getAllLevels() {
        return levelsArray;
    }

    public static Level getLevel(int levelIdx) {
        levelsArray[levelIdx].updateIsAvailable();
        return levelsArray[levelIdx];
    }

    public static List<String> getPaths() {
        return Arrays.stream(levelsArray).map(level -> level.getPath()).collect(Collectors.toList());
    }

    public static List<String> getNames() {
        return Arrays.stream(levelsArray).map(level -> level.getName()).collect(Collectors.toList());
    }

    public static boolean isLevelAvailable(int levelIdx) {
        if (levelIdx == 0) return true;
        return MemoryManager.loadLevelState(levelsArray[levelIdx - 1].getName());
    }

}
