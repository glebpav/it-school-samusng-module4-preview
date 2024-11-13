package ru.samsung.gamestudio.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import ru.samsung.gamestudio.objects.MapObjects;
import ru.samsung.gamestudio.objects.wrappers.Ladder;
import ru.samsung.gamestudio.objects.characters.*;
import ru.samsung.gamestudio.objects.wrappers.PitBlock;
import ru.samsung.gamestudio.objects.wrappers.StaticBlock;
import ru.samsung.gamestudio.utils.*;
import ru.samsung.gamestudio.world.listeners.*;

import java.util.ArrayList;
import java.util.List;

import static ru.samsung.gamestudio.game.GameSettings.*;
import static ru.samsung.gamestudio.objects.MapLayers.*;

public class B2WorldManager implements Disposable {

    public World world;

    public Player player;

    private final ArrayList<Actor> actorsList;
    private final ArrayList<Body> bodiesGarbageList;
    private final ArrayList<Disposable> disposablesList;

    private float accumulator;
    private float tileScale;

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
        disposablesList = new ArrayList<>();
    }

    public void buildWorld(MapManager mapManager) {
        tileScale = mapManager.getTileScale();

        for (
                RectangleMapObject object :
                mapManager.getMap().getLayers().get(WALL.getName()).getObjects().getByType(RectangleMapObject.class)
        ) {
            Rectangle rect = object.getRectangle();
            new StaticBlock(world, rect);
        }

        for (
                RectangleMapObject object :
                mapManager.getMap().getLayers().get(FLOOR.getName()).getObjects().getByType(RectangleMapObject.class)
        ) {
            Rectangle rect = object.getRectangle();
            new StaticBlock(world, rect);
        }

        for (
                RectangleMapObject object :
                mapManager.getMap().getLayers().get(ACTOR.getName()).getObjects().getByType(RectangleMapObject.class)
        ) {
            Rectangle rect = object.getRectangle();

            if (object.getName().equals(MapObjects.PLAYER.getName())) {
                player = new Player(world, rect, onDamageListener, onScoreEarnedListener, onLoseListener, tileScale);
            } else if (object.getName().equals(MapObjects.ENEMY.getName())) {
                Enemy enemy = new Enemy(world, rect, (int) object.getProperties().get("walkLength"),
                        onRemoveBodyListener, tileScale);
                actorsList.add(enemy);
            } else {
                continue;
            }

            disposablesList.add(player);
        }

        for (
                RectangleMapObject object :
                mapManager.getMap().getLayers().get(INTERACTIVE_OBJECTS.getName()).getObjects().getByType(RectangleMapObject.class)
        ) {
            Rectangle rect = object.getRectangle();

            if (object.getName().equals(MapObjects.PIT.getName())) {
                new PitBlock(world, rect, onLoseListener);
            } else if (object.getName().equals(MapObjects.FINISH.getName())) {
                FinishLine finishLine = new FinishLine(world, rect, onWinListener, tileScale);
                actorsList.add(finishLine);
                disposablesList.add(finishLine);
            } else if (object.getName().equals(MapObjects.COIN.getName())) {
                Coin coin = new Coin(world, rect, onScoreEarnedListener, onRemoveBodyListener, tileScale);
                actorsList.add(coin);
                disposablesList.add(coin);
            } else if (object.getName().equals(MapObjects.LADDER.getName())) {
                new Ladder(world, rect);
            } else if (object.getName().equals(MapObjects.BONUS.getName())) {
                BonusBlock bonusBlock = new BonusBlock(world, rect, onRemoveBodyListener, onScoreEarnedListener, tileScale);
                actorsList.add(bonusBlock);
                disposablesList.add(bonusBlock);
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

        for (Body body : bodiesGarbageList) {
            world.destroyBody(body);
        }
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
        dispose();
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

    OnRemoveBodyListener onRemoveBodyListener = new OnRemoveBodyListener() {
        @Override
        public void onRemoveBody(Body body) {
            bodiesGarbageList.add(body);
        }
    };

    @Override
    public void dispose() {
        for (Disposable disposable : disposablesList) {
            disposable.dispose();
        }
        disposablesList.clear();
    }
}
