package ru.samsung.gamestudio.utils;

public class Level {

    public final String name;
    public final String path;
    public boolean isAvailable;

    public Level(String name, String path) {
        this.name = name;
        this.path = path;
        isAvailable = false;
    }

}
