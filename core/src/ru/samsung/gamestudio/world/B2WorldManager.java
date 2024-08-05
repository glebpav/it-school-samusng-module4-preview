package ru.samsung.gamestudio.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.samsung.gamestudio.objects.blocks.FinishLine;
import ru.samsung.gamestudio.objects.blocks.PitBlock;
import ru.samsung.gamestudio.objects.characters.Coin;
import ru.samsung.gamestudio.objects.characters.Enemy;
import ru.samsung.gamestudio.objects.characters.Player;
import ru.samsung.gamestudio.objects.Updatable;
import ru.samsung.gamestudio.objects.blocks.StaticBlock;
import ru.samsung.gamestudio.utils.*;
import ru.samsung.gamestudio.world.listeners.*;

import java.util.ArrayList;
import java.util.List;

import static ru.samsung.gamestudio.GameSettings.*;

public class B2WorldManager {

    public World world;

    public Player player;
    public ArrayList<Enemy> enemiesList;
    public ArrayList<Coin> coinsList;
    ArrayList<Updatable> updatableList;

    private float accumulator;

    private OnLoseListener onLoseListener;
    private OnWinListener onWinListener;
    private OnCollectCoinListener onCollectCoinListener;
    private OnDamageListener onDamageListener;

    public B2WorldManager() {

        Box2D.init();
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new ContactManager());

        updatableList = new ArrayList<>();
        enemiesList = new ArrayList<>();
        coinsList = new ArrayList<>();
    }

    public void buildWorld(MapManager mapManager) {

        for (
                RectangleMapObject object :
                mapManager.map.getLayers().get("walls").getObjects().getByType(RectangleMapObject.class)
        ) {
            Rectangle rect = object.getRectangle();
            new StaticBlock(world, rect);
        }

        for (
                RectangleMapObject object :
                mapManager.map.getLayers().get("actors").getObjects().getByType(RectangleMapObject.class)
        ) {
            switch (object.getName()) {
                case "player": {
                    Rectangle rect = object.getRectangle();
                    player = new Player(world, rect);
                    updatableList.add(player);
                    break;
                }
                case "enemy1": {
                    Rectangle rect = object.getRectangle();
                    enemiesList.add(new Enemy(world, rect));
                    updatableList.add(enemiesList.get(enemiesList.size() - 1));
                }
            }
        }

        for (
                RectangleMapObject object :
                mapManager.map.getLayers().get("interactiveObjects").getObjects().getByType(RectangleMapObject.class)
        ) {
            switch (object.getName()) {
                case "pit": {
                    Rectangle rect = object.getRectangle();
                    new PitBlock(world, rect, onLoseListener);
                    break;
                }
                case "finishLine": {
                    Rectangle rect = object.getRectangle();
                    new FinishLine(world, rect, onWinListener);
                    break;
                }
                case "coin": {
                    Rectangle rect = object.getRectangle();
                    coinsList.add(new Coin(world, rect, onCollectCoinListener));
                    break;
                }
            }
        }
    }

    public void update(float delta) {
        updatableList.forEach(updatable -> updatable.update(delta));
    }

    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    public List<Actor> getAllActors() {
        List<Actor> actors = new ArrayList<>();
        actors.add(player);
        actors.addAll(enemiesList);
        actors.addAll(coinsList);
        // ...
        return actors;
    }

    public void setOnLoseListener(OnLoseListener onLoseListener) {
        this.onLoseListener = onLoseListener;
    }

    public void setOnWinListener(OnWinListener onWinListener) {
        this.onWinListener = onWinListener;
    }

    public void setOnCollectCoinListener(OnCollectCoinListener onCollectCoinListener) {
        this.onCollectCoinListener = onCollectCoinListener;
    }

    public void setOnDamageListener(OnDamageListener onDamageListener) {
        this.onDamageListener = onDamageListener;
    }

    public void clearWorld() {
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new ContactManager());

        player = null;
        enemiesList.clear();
        coinsList.clear();
    }

}