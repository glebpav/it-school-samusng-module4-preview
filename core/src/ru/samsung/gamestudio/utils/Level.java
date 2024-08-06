package ru.samsung.gamestudio.utils;

public class Level {

    private final String name;
    private final String path;
    private boolean isPassed;

    public Level(String name, String path) {
        this.name = name;
        this.path = path;
        isPassed = false;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void updateIsAvailable() {
        isPassed = MemoryManager.loadLevelState(name);
    }

}
