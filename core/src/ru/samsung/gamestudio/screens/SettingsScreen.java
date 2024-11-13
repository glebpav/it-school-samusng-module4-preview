package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.ui.screens.SettingsUi;
import ru.samsung.gamestudio.utils.Level;
import ru.samsung.gamestudio.utils.LevelManager;
import ru.samsung.gamestudio.utils.MemoryManager;

public class SettingsScreen extends BaseScreen {

    private final SettingsUi ui;

    public SettingsScreen(MyGdxGame myGdxGame) {
        super(myGdxGame);

        ui = new SettingsUi(myGdxGame.skin);
        baseStage.addActor(ui);

        setListeners();
    }

    private void setListeners() {
        ui.homeButton.addListener(onButtonHomeClicked);
        ui.resetLevelsButton.addListener(onButtonResetLevelsClicked);
    }

    ClickListener onButtonHomeClicked = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }
    };

    ClickListener onButtonResetLevelsClicked = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            for (Level level : LevelManager.getAllLevels()) {
                MemoryManager.saveLevelState(level.getName(), false);
            }
        }
    };

}

