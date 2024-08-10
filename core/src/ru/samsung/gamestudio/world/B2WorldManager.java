package ru.samsung.gamestudio.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.samsung.gamestudio.objects.blocks.Ladder;
import ru.samsung.gamestudio.objects.characters.*;
import ru.samsung.gamestudio.objects.blocks.PitBlock;
import ru.samsung.gamestudio.objects.blocks.StaticBlock;
import ru.samsung.gamestudio.utils.*;
import ru.samsung.gamestudio.world.listeners.*;

import java.util.ArrayList;
import java.util.List;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class B2WorldManager {

    public World world;

    public Player player;

    private final ArrayList<Actor> actorsList;
    private ArrayList<Body> bodiesGarbageList;

    private float accumulator;

    private OnLoseListener onLoseListener;
    private OnWinListener onWinListener;
    private OnScoreEarnedListener onScoreEarnedListener;
    private OnDamageListener onDamageListener;

    public B2WorldManager() {

        Box2D.init();
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new ContactManager());

        actorsList = new ArrayList<>();
        bodiesGarbageList = new ArrayList<>();
    }

    public void buildWorld(MapManager mapManager) {

        for (
                RectangleMapObject object :
                mapManager.getMap().getLayers().get("walls").getObjects().getByType(RectangleMapObject.class)
        ) {
            Rectangle rect = object.getRectangle();
            new StaticBlock(world, rect);
        }

        // todo: make floor block
        for (
                RectangleMapObject object :
                mapManager.getMap().getLayers().get("floor").getObjects().getByType(RectangleMapObject.class)
        ) {
            Rectangle rect = object.getRectangle();
            new StaticBlock(world, rect);
        }

        for (
                RectangleMapObject object :
                mapManager.getMap().getLayers().get("actors").getObjects().getByType(RectangleMapObject.class)
        ) {
            switch (object.getName()) {
                case "player": {
                    Rectangle rect = object.getRectangle();
                    player = new Player(world, rect, onDamageListener, onScoreEarnedListener, onLoseListener);
                    break;
                }
                case "enemy1": {
                    Rectangle rect = object.getRectangle();
                    actorsList.add(new Enemy(world, rect, (int) object.getProperties().get("walkLength"), onRemoveBodyListener));
                }
            }
        }

        for (
                RectangleMapObject object :
                mapManager.getMap().getLayers().get("interactiveObjects").getObjects().getByType(RectangleMapObject.class)
        ) {
            switch (object.getName()) {
                case "pit": {
                    Rectangle rect = object.getRectangle();
                    new PitBlock(world, rect, onLoseListener);
                    break;
                }
                case "finishLine": {
                    Rectangle rect = object.getRectangle();
                    actorsList.add(new FinishLine(world, rect, onWinListener));
                    break;
                }
                case "coin": {
                    Rectangle rect = object.getRectangle();
                    actorsList.add(new Coin(world, rect, onScoreEarnedListener, onRemoveBodyListener));
                    break;
                }
                case "ladder": {
                    Rectangle rect = object.getRectangle();
                    new Ladder(world, rect);
                    break;
                }
                case "bonusBlock": {
                    System.out.println("bonusBlock");
                    Rectangle rect = object.getRectangle();
                    actorsList.add(new BonusBlock(world, rect, onRemoveBodyListener, onScoreEarnedListener));
                    break;
                }
            }
        }
    }

    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }

        bodiesGarbageList.forEach(body -> world.destroyBody(body));
        bodiesGarbageList.clear();

    }

    public List<Actor> getAllActors() {
        List<Actor> actors = new ArrayList<>();
        actors.add(player);
        actors.addAll(actorsList);
        return actors;
    }

    public void clearWorld() {
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new ContactManager());

        player = null;
        actorsList.clear();
    }

    public void setOnLoseListener(OnLoseListener onLoseListener) {
        this.onLoseListener = onLoseListener;
    }

    public void setOnWinListener(OnWinListener onWinListener) {
        this.onWinListener = onWinListener;
    }

    public void setOnCollectCoinListener(OnScoreEarnedListener onScoreEarnedListener) {
        this.onScoreEarnedListener = onScoreEarnedListener;
    }

    public void setOnDamageListener(OnDamageListener onDamageListener) {
        this.onDamageListener = onDamageListener;
    }

    OnRemoveBodyListener onRemoveBodyListener = body ->  {
        bodiesGarbageList.add(body);
    };

}
