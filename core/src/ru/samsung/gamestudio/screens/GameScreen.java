package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.game.GameSession;
import ru.samsung.gamestudio.game.GameState;
import ru.samsung.gamestudio.ui.screens.GameUi;
import ru.samsung.gamestudio.utils.Level;
import ru.samsung.gamestudio.utils.MemoryManager;
import ru.samsung.gamestudio.world.B2WorldManager;
import ru.samsung.gamestudio.utils.MapManager;
import ru.samsung.gamestudio.world.listeners.OnScoreEarnedListener;
import ru.samsung.gamestudio.world.listeners.OnDamageListener;
import ru.samsung.gamestudio.world.listeners.OnLoseListener;
import ru.samsung.gamestudio.world.listeners.OnWinListener;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class GameScreen extends BaseScreen {

    private final B2WorldManager b2WorldManager;
    private final Box2DDebugRenderer debugRenderer;
    private MapManager mapManager;
    private OrthoCachedTiledMapRenderer mapRenderer;
    private Level level;

    private GameSession session;
    private GameUi gameUi;

    public GameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);
        debugRenderer = new Box2DDebugRenderer();
        b2WorldManager = new B2WorldManager();
        session = new GameSession();
        gameUi = new GameUi(myGdxGame.skin);

        stage.addActor(gameUi);

        b2WorldManager.setOnLoseListener(onLoseListener);
        b2WorldManager.setOnWinListener(onWinListener);
        b2WorldManager.setOnCollectCoinListener(onScoreEarnedListener);
        b2WorldManager.setOnDamageListener(onDamageListener);

        gameUi.loseDialog.homeButton.addListener(onButtonHomeClicked);
        gameUi.winDialog.homeButton.addListener(onButtonHomeClicked);
    }

    @Override
    public void show() {
        super.show();
        session.startGame();
        gameUi.hudUi.clearHud();
    }

    @Override
    public void render(float delta) {

        // System.out.println("fps: " + Gdx.graphics.getFramesPerSecond());

        b2WorldManager.stepWorld();
        myGdxGame.camera.position.x =
                Math.min(
                        Math.max(b2WorldManager.player.getX(), SCREEN_WIDTH / 2f),
                        PPI * mapManager.getProperties().get("width", Integer.class)
                                * mapManager.getProperties().get("tilewidth", Integer.class) - SCREEN_WIDTH / 2f
                );

        gameUi.makeHudCentered(myGdxGame.camera.position.x);

        ScreenUtils.clear(0, 0, 0, 0);
        mapRenderer.setView(myGdxGame.camera);
        mapRenderer.render();
        debugRenderer.render(b2WorldManager.world, myGdxGame.camera.combined);

        super.render(delta, false);

    }

    @Override
    public void handleInput() {

        if (session.getState() == GameState.PLAYING) {

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                b2WorldManager.player.moveUp();
            } if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                b2WorldManager.player.moveLeft();
            } if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                b2WorldManager.player.moveRight();
            } if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                b2WorldManager.player.attack();
            }

        }

    }

    public void loadLevel(Level level) {
        this.level = level;

        mapManager = new MapManager(level.getPath());
        mapRenderer = new OrthoCachedTiledMapRenderer(mapManager.getMap(), PPI);

        b2WorldManager.buildWorld(mapManager);
        b2WorldManager.getAllActors().forEach(actor -> {
            stage.addActor(actor);
        });
        gameUi.toFront();

    }

    public void exitLevel() {
        b2WorldManager.getAllActors().forEach(Actor::remove);
        b2WorldManager.clearWorld();
        myGdxGame.setScreen(myGdxGame.menuScreen);
    }

    @Override
    public void hide() {
        myGdxGame.camera.position.x = SCREEN_WIDTH / 2f;
    }

    OnLoseListener onLoseListener = loseText -> {
        gameUi.showLoseDialog(loseText, myGdxGame.camera.position);
        session.endGame();
    };

    OnWinListener onWinListener = () -> {
        gameUi.showWinDialog(
                TimeUtils.millis() - session.getSessionStartTime(),
                session.getScore(),
                myGdxGame.camera.position
        );
        session.endGame();
        MemoryManager.saveLevelState(level.getName(), true);
    };

    OnScoreEarnedListener onScoreEarnedListener = coinValue -> {
        session.addScore(coinValue);
        gameUi.hudUi.setScore(session.getScore());
    };

    OnDamageListener onDamageListener = leftLives -> {
        gameUi.hudUi.setLeftLives(leftLives);
    };

    ClickListener onButtonHomeClicked = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            gameUi.hideDialogs();
            exitLevel();
        }
    };

}
