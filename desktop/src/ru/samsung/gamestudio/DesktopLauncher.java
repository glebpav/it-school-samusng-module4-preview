package ru.samsung.gamestudio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import static ru.samsung.gamestudio.game.GameSettings.SCREEN_HEIGHT;
import static ru.samsung.gamestudio.game.GameSettings.SCREEN_WIDTH;


public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(1280, 720);
		config.setTitle("it-school-samsung-module4-preview");
		// config.setResizable(false);
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
