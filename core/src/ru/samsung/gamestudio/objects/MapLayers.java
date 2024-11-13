package ru.samsung.gamestudio.objects;


public enum MapLayers {

    WALL("walls"),
    FLOOR("floor"),
    ACTOR("actor"),
    INTERACTIVE_OBJECTS("interactive_objects");

    public final String name;

    MapLayers(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
