package ru.samsung.gamestudio.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.objects.blocks.StaticBlock;

public class B2WorldManager {

    public B2WorldManager (World world, MapManager mapManager) {

        for(MapLayer mapLayer : mapManager.map.getLayers()) {
            System.out.println("map layer name: " + mapLayer.getName());
            for(RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
                // System.out.println("object class name: " + object.getClass().getName());
                Rectangle rect = object.getRectangle();
                new StaticBlock(world, rect);
            }
        }

    }

}
