package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.samsung.gamestudio.MyGdxGame;

import static ru.samsung.gamestudio.GameSettings.SCALE;
import static ru.samsung.gamestudio.GameSettings.SCREEN_HEIGHT;

public class GameScreen extends BaseScreen {

    // Tiled Maps Variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthoCachedTiledMapRenderer mapRenderer;

    public GameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        // Load tmx map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/level2.tmx");
        mapRenderer = new OrthoCachedTiledMapRenderer(map, SCREEN_HEIGHT / 15f / 32f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // ScreenUtils.clear(51f/255, 50f/255, 60f/255, 1f);
        ScreenUtils.clear(0, 0.5f, 1f, 0.5f);

        mapRenderer.setView(myGdxGame.camera);
        mapRenderer.render();

    }
}
