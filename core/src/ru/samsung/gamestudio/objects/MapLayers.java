package ru.samsung.gamestudio.objects;


public enum MapLayers {

    WALL("walls"),
    FLOOR("floor"),
    ACTOR("actors"),
    INTERACTIVE_OBJECTS("interactiveObjects");

    public final String name;

    MapLayers(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
