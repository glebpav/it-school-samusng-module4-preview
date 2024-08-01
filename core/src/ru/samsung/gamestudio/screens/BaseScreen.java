package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.samsung.gamestudio.MyGdxGame;

public abstract class BaseScreen extends ScreenAdapter {

    Stage stage;
    MyGdxGame myGdxGame;

    public BaseScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        if (myGdxGame.viewport != null)  stage = new Stage(myGdxGame.viewport);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);
        stage.act(delta);
        stage.draw();
        handleInput();
    }

    public void handleInput() {}
}