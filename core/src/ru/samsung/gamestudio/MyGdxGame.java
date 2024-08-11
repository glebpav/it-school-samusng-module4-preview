package ru.samsung.gamestudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ru.samsung.gamestudio.screens.GameScreen;
import ru.samsung.gamestudio.screens.MenuScreen;
import ru.samsung.gamestudio.screens.SettingsScreen;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class MyGdxGame extends Game {

	public Skin skin;
	public OrthographicCamera camera;

	public GameScreen gameScreen;
	public MenuScreen menuScreen;
	public SettingsScreen settingsScreen;
	
	@Override
	public void create () {

		camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);

		skin = new Skin(Gdx.files.internal(SKIN_PATH));
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		settingsScreen = new SettingsScreen(this);

		setScreen(menuScreen);

	}

	@Override
	public void dispose () {
		skin.dispose();
	}


}
