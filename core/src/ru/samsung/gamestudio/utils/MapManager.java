package ru.samsung.gamestudio.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.objects.blocks.StaticBlock;

public class MapManager {

    public TmxMapLoader mapLoader;
    public TiledMap map;
    public MapProperties prop;

    public MapManager(int level) {

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/level" + level + ".tmx");
        prop = map.getProperties();

    }

}
