package ru.samsung.gamestudio.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LevelManager {

    private final static Level[] levelsArray = new Level[] {
            new Level("First fight", "maps/level1.tmx"),
            new Level("New enemies", "maps/level2.tmx"),
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
            new Level("Empty test", "maps/level2.tmx"),
            new Level("Empty test", "maps/level2.tmx"),
    };

    public static Level[] getAllLevels() {
        return levelsArray;
    }

    public static List<String> getPaths() {
        return Arrays.stream(levelsArray).map(level -> level.path).collect(Collectors.toList());
    }

    public static List<String> getNames() {
        return Arrays.stream(levelsArray).map(level -> level.name).collect(Collectors.toList());
    }

}
