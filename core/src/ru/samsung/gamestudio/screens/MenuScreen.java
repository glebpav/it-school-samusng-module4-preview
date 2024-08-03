package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.utils.LevelManager;

public class MenuScreen extends BaseScreen {

    ScrollPane scrollPane;
    List<String> listView;

    public MenuScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        Table rootTable = new Table();
        Button startButton = new TextButton("Start this game", myGdxGame.skin);
        Button exitButton = new TextButton("Exit game", myGdxGame.skin);
        Button settingsButton = new TextButton("Settings", myGdxGame.skin);
        listView = new List<>(myGdxGame.skin);
        scrollPane = new ScrollPane(listView, myGdxGame.skin);
        listView.setItems(LevelManager.getNames().toArray(new String[0]));
        listView.setAlignment(Align.center);
        scrollPane.setActor(listView);
        scrollPane.setSize(400, 150);

        rootTable.setFillParent(true);

        rootTable.columnDefaults(2);
        rootTable.add(scrollPane).width(400).height(150).colspan(2).space(10);
        rootTable.row();
        rootTable.add(startButton).width(400).height(60).colspan(2).space(10);
        rootTable.row();
        rootTable.add(exitButton).width(195).height(60).space(10);
        rootTable.add(settingsButton).width(195).height(60).space(10);

        stage.addActor(rootTable);

        exitButton.addListener(onButtonExitClickedListener);
        startButton.addListener(onButtonStartClickedListener)
;

    }

    ClickListener onButtonExitClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.app.exit();
        }
    };

    ClickListener onButtonStartClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            myGdxGame.gameScreen.loadLevel(LevelManager.getPaths().get(listView.getSelectedIndex()));
            myGdxGame.setScreen(myGdxGame.gameScreen);
        }
    };

}
