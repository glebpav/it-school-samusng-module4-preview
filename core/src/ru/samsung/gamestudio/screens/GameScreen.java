package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.utils.B2WorldManager;
import ru.samsung.gamestudio.utils.MapManager;

import static ru.samsung.gamestudio.GameSettings.SCALE;
import static ru.samsung.gamestudio.GameSettings.SCREEN_HEIGHT;

public class GameScreen extends BaseScreen {

    // Tiled Maps Variables
    private MapManager mapManager;
    private B2WorldManager b2WorldManager;
    private OrthoCachedTiledMapRenderer mapRenderer;
    private Box2DDebugRenderer debugRenderer;

    public GameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        debugRenderer = new Box2DDebugRenderer();

        mapManager = new MapManager(1);
        b2WorldManager = new B2WorldManager(myGdxGame.world, mapManager);

        mapRenderer = new OrthoCachedTiledMapRenderer(mapManager.map, SCALE);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // ScreenUtils.clear(51f/255, 50f/255, 60f/255, 1f);
        ScreenUtils.clear(0, 0.5f, 1f, 0.5f);

        mapRenderer.setView(myGdxGame.camera);
        mapRenderer.render();
        debugRenderer.render(myGdxGame.world, myGdxGame.camera.combined);


    }
}
