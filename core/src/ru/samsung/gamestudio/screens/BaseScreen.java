package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.game.GameSettings;

import static ru.samsung.gamestudio.game.GameSettings.SCREEN_HEIGHT;
import static ru.samsung.gamestudio.game.GameSettings.SCREEN_WIDTH;

public abstract class BaseScreen extends ScreenAdapter {

    protected Stage baseStage;
    protected Viewport baseViewport;
    protected MyGdxGame myGdxGame;

    public BaseScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        baseViewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, myGdxGame.camera);
        baseStage = new Stage(baseViewport);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(baseStage);
    }

    @Override
    public void resize(int width, int height) {
        baseStage.getViewport().update(width, height, false);
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void render(float delta) {
        render(delta, true);
    }

    void render(float delta, boolean clearScreen) {
        if (clearScreen) ScreenUtils.clear(Color.GRAY);
        baseStage.act(delta);
        baseStage.draw();
        handleInput();
    }

    public void handleInput() {
    }

    @Override
    public void dispose() {
        baseStage.dispose();
    }
}