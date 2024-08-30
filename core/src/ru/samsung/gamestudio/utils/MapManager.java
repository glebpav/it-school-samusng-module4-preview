package ru.samsung.gamestudio.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

import static ru.samsung.gamestudio.game.GameSettings.SCREEN_HEIGHT;

public class MapManager implements Disposable {

    private final TiledMap map;
    private final int tileSize;
    private final int countOfTilesHorizontal;
    private final float tileScale;

    public MapManager(String pathToMap) {
        TmxMapLoader mapLoader = new TmxMapLoader();
        try {
            map = mapLoader.load(pathToMap);
            MapProperties properties = map.getProperties();
            tileSize = properties.get("tilewidth", Integer.class);
            countOfTilesHorizontal = properties.get("width", Integer.class);
            float countOfTilesVertical = properties.get("height", Integer.class);
            tileScale = SCREEN_HEIGHT / tileSize / countOfTilesVertical;
        } catch (GdxRuntimeException e) {
            Gdx.app.error("MapManager", "Failed to load map, check the file path or format: " + pathToMap, e);
            throw e;
        } catch (NullPointerException e) {
            Gdx.app.error("MapManager", "Map properties missing or invalid in: " + pathToMap, e);
            throw e;
        }
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
