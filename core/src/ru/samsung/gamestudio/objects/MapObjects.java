package ru.samsung.gamestudio.objects;

public enum MapObjects {

    PLAYER("player"),
    ENEMY("enemy1"),
    PIT("pit"),
    FINISH("finishLine"),
    COIN("coin"),
    LADDER("ladder"),
    BONUS("bonusBlock");

    private final String name;

    MapObjects(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
