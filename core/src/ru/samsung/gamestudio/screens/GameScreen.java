package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.utils.B2WorldManager;
import ru.samsung.gamestudio.utils.MapManager;

import static ru.samsung.gamestudio.GameSettings.*;

public class GameScreen extends BaseScreen {

    private MapManager mapManager;
    private B2WorldManager b2WorldManager;
    private OrthoCachedTiledMapRenderer mapRenderer;
    private Box2DDebugRenderer debugRenderer;

    public GameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);
        debugRenderer = new Box2DDebugRenderer();
    }

    public void loadLevel(String pathToLevel) {
        mapManager = new MapManager(pathToLevel);
        mapRenderer = new OrthoCachedTiledMapRenderer(mapManager.map, PPI);
        b2WorldManager = new B2WorldManager(mapManager);
        b2WorldManager.getAllActors().forEach(actor -> stage.addActor(actor));
    }

    @Override
    public void render(float delta) {

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
