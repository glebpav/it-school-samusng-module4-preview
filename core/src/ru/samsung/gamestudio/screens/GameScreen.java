package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.game.GameSession;
import ru.samsung.gamestudio.game.GameState;
import ru.samsung.gamestudio.ui.components.LiveBackground;
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

    private final Stage hudStage;
    private final Stage backgroundStage;

    private final LiveBackground liveBackground;
    private final GameUi gameUi;
    private final GameSession session;

    public GameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        FitViewport viewport2 = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        viewport2.setScreenBounds(0, 0, (int) SCREEN_WIDTH, (int) SCREEN_HEIGHT);
        hudStage = new Stage(viewport2);
        backgroundStage = new Stage(viewport2);

        debugRenderer = new Box2DDebugRenderer();
        b2WorldManager = new B2WorldManager();
        session = new GameSession();
        gameUi = new GameUi(myGdxGame.skin);
        liveBackground = new LiveBackground();

        backgroundStage.addActor(liveBackground);
        hudStage.addActor(gameUi);


        b2WorldManager.setOnLoseListener(onLoseListener);
        b2WorldManager.setOnWinListener(onWinListener);
        b2WorldManager.setOnCollectCoinListener(onScoreEarnedListener);
        b2WorldManager.setOnDamageListener(onDamageListener);

        gameUi.loseDialog.homeButton.addListener(onButtonHomeClicked);
        gameUi.winDialog.homeButton.addListener(onButtonHomeClicked);
        gameUi.loseDialog.restartButton.addListener(onButtonRestartClicked);

    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(hudStage);
        startGame();
    }

    @Override
    public void render(float delta) {

        b2WorldManager.stepWorld();
        myGdxGame.camera.update();

        myGdxGame.camera.position.x =
                Math.min(
                        Math.max(b2WorldManager.player.getX(), SCREEN_WIDTH / 2f),
                        mapManager.getTileScale() * mapManager.getTileSize()
                                * mapManager.getCountOfTilesHorizontal() - SCREEN_WIDTH / 2f
                );

        liveBackground.computeCloudPositions(myGdxGame.camera.position.x);

        ScreenUtils.clear(0, 0, 0, 0);

        backgroundStage.act();
        backgroundStage.draw();

        mapRenderer.setView(myGdxGame.camera);
        mapRenderer.render();

        debugRenderer.render(b2WorldManager.world, myGdxGame.camera.combined);
        super.render(delta, false);
        hudStage.act();
        hudStage.draw();

    }

    @Override
    public void handleInput() {
        if (session.getState() == GameState.PLAYING) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)
                    || gameUi.mobileController != null && gameUi.mobileController.upButton.isPressed()) {
                b2WorldManager.player.moveUp();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
                    || gameUi.mobileController != null && gameUi.mobileController.backButton.isPressed()) {
                b2WorldManager.player.moveLeft();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                    || gameUi.mobileController != null && gameUi.mobileController.forwardButton.isPressed()) {
                b2WorldManager.player.moveRight();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.F)
                    || gameUi.mobileController != null && gameUi.mobileController.attackButton.isPressed()) {
                b2WorldManager.player.attack();
            }
        }
    }

    private void startGame() {
        session.startGame();
        gameUi.hideDialogs();
        gameUi.hudUi.clearHud();
        loadLevel();
    }

    public void loadLevel() {
        mapManager = new MapManager(level.getPath());
        mapRenderer = new OrthoCachedTiledMapRenderer(mapManager.getMap(), mapManager.getTileScale());
        mapRenderer.setBlending(true);

        b2WorldManager.buildWorld(mapManager);

        for (Actor actor : b2WorldManager.getAllActors())
            baseStage.addActor(actor);

        gameUi.toFront();
    }

    public void clearLevel() {
        for (Actor actor : b2WorldManager.getAllActors())
            actor.remove();
        b2WorldManager.clearWorld();
    }

    public void exitLevel() {
        clearLevel();
        myGdxGame.setScreen(myGdxGame.menuScreen);
    }

    @Override
    public void hide() {
        myGdxGame.camera.position.x = SCREEN_WIDTH / 2f;
    }

    @Override
    public void dispose() {
        mapManager.dispose();
        mapRenderer.dispose();
        hudStage.dispose();
        baseStage.dispose();
        b2WorldManager.dispose();
        liveBackground.dispose();
        super.dispose();
    }

    OnLoseListener onLoseListener = new OnLoseListener() {
        @Override
        public void onLose(String loseText) {
            gameUi.showLoseDialog(loseText);
            session.endGame();
        }
    };

    OnWinListener onWinListener = new OnWinListener() {
        @Override
        public void onWin() {
            gameUi.showWinDialog(TimeUtils.millis() - session.getSessionStartTime(), session.getScore());
            session.endGame();
            MemoryManager.saveLevelState(level.getName(), true);
        }
    };

    OnScoreEarnedListener onScoreEarnedListener = new OnScoreEarnedListener() {
        @Override
        public void onScoreEarned(int scoreValue) {
            session.addScore(scoreValue);
            gameUi.hudUi.setScore(session.getScore());
        }
    };

    OnDamageListener onDamageListener = new OnDamageListener() {
        @Override
        public void onDamage(int leftLives) {
            gameUi.hudUi.setLeftLives(leftLives);
        }
    };

    ClickListener onButtonHomeClicked = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            exitLevel();
        }
    };

    ClickListener onButtonRestartClicked = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            clearLevel();
            startGame();
        }
    };


}
