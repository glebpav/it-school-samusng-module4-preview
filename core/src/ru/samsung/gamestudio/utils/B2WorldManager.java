package ru.samsung.gamestudio.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.objects.Enemy;
import ru.samsung.gamestudio.objects.Player;
import ru.samsung.gamestudio.objects.Updatable;
import ru.samsung.gamestudio.objects.blocks.StaticBlock;

import java.util.ArrayList;

import static ru.samsung.gamestudio.GameSettings.*;

public class B2WorldManager {

    public Player player;
    public ArrayList<Enemy> enemiesList;
    ArrayList<Updatable> updatableList;

    private float accumulator;

    public B2WorldManager (World world, MapManager mapManager) {

        /*for(MapLayer mapLayer : mapManager.map.getLayers()) {
            System.out.println("map layer name: " + mapLayer.getName());
            for(RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
                // System.out.println("object class name: " + object.getClass().getName());
                Rectangle rect = object.getRectangle();
                new StaticBlock(world, rect);
            }
        }*/

        updatableList = new ArrayList<>();
        enemiesList = new ArrayList<>();

        for(RectangleMapObject object : mapManager.map.getLayers().get("walls").getObjects().getByType(RectangleMapObject.class)) {
            // System.out.println("object class name: " + object.getClass().getName());
            Rectangle rect = object.getRectangle();
            new StaticBlock(world, rect);
        }

        for (RectangleMapObject object : mapManager.map.getLayers().get("Actors").getObjects().getByType(RectangleMapObject.class)) {
            switch (object.getName())  {
                case "Player" : {
                    Rectangle rect = object.getRectangle();
                    player = new Player(world, rect);
                    updatableList.add(player);
                    break;
                }
                case "enemy1" : {
                    Rectangle rect = object.getRectangle();
                    enemiesList.add(new Enemy(world, rect));
                    updatableList.add(enemiesList.get(enemiesList.size() - 1));
                }
            }
        }

    }

    public void update(float delta) {
        updatableList.forEach(updatable -> updatable.update(delta));
    }

    public void stepWorld(World world) {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

}
