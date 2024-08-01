package ru.samsung.gamestudio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ru.samsung.gamestudio.screens.GameScreen;

import static ru.samsung.gamestudio.GameSettings.*;

public class MyGdxGame extends Game {

	public Skin skin;
	public OrthographicCamera camera;
	public FitViewport viewport;

	public World world;
	private float accumulator;

	public GameScreen gameScreen;
	
	@Override
	public void create () {

		Box2D.init();
		world = new World(new Vector2(0, 0), true);

		camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);

		skin = new Skin(Gdx.files.internal(SKIN_PATH));
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

		gameScreen = new GameScreen(this);

		setScreen(gameScreen);

	}

	@Override
	public void dispose () {
		skin.dispose();
	}

	public void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= STEP_TIME) {
			accumulator -= STEP_TIME;
			world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}
	}
}
