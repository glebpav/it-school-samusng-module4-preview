package ru.samsung.gamestudio.utils;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

import static ru.samsung.gamestudio.game.GameSettings.SCREEN_HEIGHT;

public class MapManager implements Disposable {

    private final TiledMap map;
    private final int tileSize;
    private final int countOfTilesHorizontal;
    private final float tileScale;

    public MapManager(String pathToMap) {
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load(pathToMap);
        MapProperties properties = map.getProperties();
        tileSize = properties.get("tilewidth", Integer.class);
        countOfTilesHorizontal = properties.get("width", Integer.class);
        float countOfTilesVertical = properties.get("height", Integer.class);
        tileScale = SCREEN_HEIGHT / tileSize / countOfTilesVertical;
    }

    public TiledMap getMap() {
        return map;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getCountOfTilesHorizontal() {
        return countOfTilesHorizontal;
    }

    public float getTileScale() {
        return tileScale;
    }

    @Override
    public void dispose() {
        map.dispose();
    }

}
