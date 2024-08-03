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

    // Tiled Maps Variables
    private MapManager mapManager;
    private B2WorldManager b2WorldManager;
    private OrthoCachedTiledMapRenderer mapRenderer;
    private Box2DDebugRenderer debugRenderer;

    public GameScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        debugRenderer = new Box2DDebugRenderer();

        mapManager = new MapManager(2);
        b2WorldManager = new B2WorldManager(myGdxGame.world, mapManager);

        stage.addActor(b2WorldManager.player);
        b2WorldManager.enemiesList.forEach(enemy -> stage.addActor(enemy));

        mapRenderer = new OrthoCachedTiledMapRenderer(mapManager.map, PPI);

    }

    @Override
    public void render(float delta) {

        myGdxGame.camera.position.x =
                Math.min(Math.max(b2WorldManager.player.getX(), SCREEN_WIDTH / 2f),
                        PPI * mapManager.prop.get("width", Integer.class)
                                * mapManager.prop.get("tilewidth", Integer.class) - SCREEN_WIDTH / 2f
                );
        b2WorldManager.stepWorld(myGdxGame.world);
        b2WorldManager.update(delta);

        // ScreenUtils.clear(51f/255, 50f/255, 60f/255, 1f);
       // ScreenUtils.clear(0, 0.5f, 1f, 0.5f);
        ScreenUtils.clear(0, 0, 0, 0);

        mapRenderer.setView(myGdxGame.camera);
        mapRenderer.render();
        debugRenderer.render(myGdxGame.world, myGdxGame.camera.combined);

        super.render(delta, false);

    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            b2WorldManager.player.moveUp();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            b2WorldManager.player.moveLeft();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            b2WorldManager.player.moveRight();
        } else if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            b2WorldManager.player.attack();
        }

        /*// Virtual Controls
        if ((vpad.isPressLeft()) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        else if ((vpad.isPressRight()) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        else if (vpad.isPressJump() && player.b2body.getLinearVelocity().y == 0) {
            player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);
        }*/
    }
}
