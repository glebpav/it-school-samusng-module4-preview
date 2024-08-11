package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.ui.screens.MenuUi;
import ru.samsung.gamestudio.utils.Level;
import ru.samsung.gamestudio.utils.LevelManager;

public class MenuScreen extends BaseScreen {

    private MenuUi menuUi;

    public MenuScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        menuUi = new MenuUi(myGdxGame.skin);
        baseStage.addActor(menuUi);

        menuUi.exitButton.addListener(onButtonExitClickedListener);
        menuUi.startButton.addListener(onButtonStartClickedListener);
        menuUi.settingsButton.addListener(onButtonSettingsClickedListener);

    }

    private void updateList() {
        String[] levelList = new String[LevelManager.getAllLevels().length];
        for (int i = 0; i < LevelManager.getAllLevels().length; i++) {
            Level level = LevelManager.getAllLevels()[i];
            levelList[i] = level.getName() + (LevelManager.isLevelAvailable(i) ? "" : " (unavailable)");
        }
        menuUi.listView.setItems(levelList);
    }

    @Override
    public void show() {
        super.show();
        updateList();
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
            if (!LevelManager.isLevelAvailable(menuUi.listView.getSelectedIndex())) return;
            myGdxGame.gameScreen.setLevel(LevelManager.getLevel(menuUi.listView.getSelectedIndex()));
            myGdxGame.setScreen(myGdxGame.gameScreen);
        }
    };

    ClickListener onButtonSettingsClickedListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            myGdxGame.setScreen(myGdxGame.settingsScreen);
        }
    };
}
