package ru.samsung.gamestudio.utils;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapManager {

    private final TiledMap map;
    private final MapProperties properties;

    public MapManager(String pathToMap) {

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load(pathToMap);
        // todo: create fields (tile width and count of tiles) instead of properties
        properties = map.getProperties();

    }

    public TiledMap getMap() {
        return map;
    }

    public MapProperties getProperties() {
        return properties;
    }
}
