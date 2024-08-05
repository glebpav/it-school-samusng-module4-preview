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
import org.w3c.dom.ls.LSOutput;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.game.GameSession;
import ru.samsung.gamestudio.game.GameState;
import ru.samsung.gamestudio.ui.components.LoseDialog;
import ru.samsung.gamestudio.ui.components.WinDialog;
import ru.samsung.gamestudio.utils.B2WorldManager;
import ru.samsung.gamestudio.utils.MapManager;
import ru.samsung.gamestudio.utils.OnLoseListener;
import ru.samsung.gamestudio.utils.OnWinListener;

import static ru.samsung.gamestudio.GameSettings.*;

public class GameScreen extends BaseScreen {

    private MapManager mapManager;
    private B2WorldManager b2WorldManager;
    private OrthoCachedTiledMapRenderer mapRenderer;
    private Box2DDebugRenderer debugRenderer;

    private GameSession session;

    private LoseDialog loseDialog;
    private WinDialog winDialog;

    public GameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);
        debugRenderer = new Box2DDebugRenderer();
        b2WorldManager = new B2WorldManager();
        b2WorldManager.setOnLoseListener(onLoseListener);
        b2WorldManager.setOnWinListener(onWinListener);
        session = new GameSession();

        loseDialog = new LoseDialog(myGdxGame.skin);
        winDialog = new WinDialog(myGdxGame.skin);
        loseDialog.homeButton.addListener(onButtonHomeClicked);
        winDialog.homeButton.addListener(onButtonHomeClicked);
    }

    @Override
    public void show() {
        super.show();
        session.startGame();
    }

    @Override
    public void render(float delta) {

        // System.out.println("fps: " + Gdx.graphics.getFramesPerSecond());

        myGdxGame.camera.position.x =
                Math.min(Math.max(b2WorldManager.player.getX(), SCREEN_WIDTH / 2f),
                        PPI * mapManager.prop.get("width", Integer.class)
                                * mapManager.prop.get("tilewidth", Integer.class) - SCREEN_WIDTH / 2f
                );
        b2WorldManager.stepWorld();
        b2WorldManager.update(delta);

        // ScreenUtils.clear(51f/255, 50f/255, 60f/255, 1f);
        // ScreenUtils.clear(0, 0.5f, 1f, 0.5f);
        ScreenUtils.clear(0, 0, 0, 0);

        mapRenderer.setView(myGdxGame.camera);
        mapRenderer.render();
        debugRenderer.render(b2WorldManager.world, myGdxGame.camera.combined);

        super.render(delta, false);

    }

    @Override
    public void handleInput() {

        if (session.state == GameState.PLAYING) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                b2WorldManager.player.moveUp();
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                b2WorldManager.player.moveLeft();
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                b2WorldManager.player.moveRight();
            } else if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                b2WorldManager.player.attack();
            }

        }

    }

    public void loadLevel(String pathToLevel) {

        mapManager = new MapManager(pathToLevel);
        mapRenderer = new OrthoCachedTiledMapRenderer(mapManager.map, PPI);

        b2WorldManager.buildWorld(mapManager);
        b2WorldManager.getAllActors().forEach(actor -> stage.addActor(actor));

    }

    public void exitLevel() {
        b2WorldManager.getAllActors().forEach(Actor::remove);
        b2WorldManager.clearWorld();
        myGdxGame.setScreen(myGdxGame.menuScreen);
    }

    OnLoseListener onLoseListener = loseText -> {
        loseDialog.setText(loseText);
        loseDialog.setPosition(
                myGdxGame.camera.position.x - loseDialog.getWidth() / 2,
                myGdxGame.camera.position.y - loseDialog.getHeight() / 2
        );
        stage.addActor(loseDialog);
        session.endGame();
    };

    @Override
    public void hide() {
        myGdxGame.camera.position.x = SCREEN_WIDTH / 2f;
    }

    OnWinListener onWinListener = () -> {
        winDialog.setTime(TimeUtils.millis() - session.sessionStartTime);
        winDialog.setPosition(
                myGdxGame.camera.position.x - loseDialog.getWidth() / 2,
                myGdxGame.camera.position.y - loseDialog.getHeight() / 2
        );
        stage.addActor(winDialog);
        session.endGame();
    };

    ClickListener onButtonHomeClicked = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            loseDialog.remove();
            winDialog.remove();
            exitLevel();
        }
    };
}
