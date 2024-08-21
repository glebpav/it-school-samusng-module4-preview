package ru.samsung.gamestudio.ui.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ru.samsung.gamestudio.ui.UiComponent;
import ru.samsung.gamestudio.ui.components.*;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class GameUi extends UiComponent {

    public final LoseDialog loseDialog;
    public final WinDialog winDialog;
    public final PauseDialog pauseDialog;

    public final HudUi hudUi;
    public MobileController mobileController;

    public GameUi(Skin skin) {
        hudUi = new HudUi(skin);
        loseDialog = new LoseDialog(skin);
        winDialog = new WinDialog(skin);
        pauseDialog = new PauseDialog(skin);

        addActor(hudUi);

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            mobileController = new MobileController(skin);
            addActor(mobileController);
        }

        hudUi.setPosition(SCREEN_WIDTH / 2f - hudUi.getWidth() / 2, SCREEN_HEIGHT - 50);
    }

    public void showLoseDialog(String loseText) {
        loseDialog.setText(loseText);
        addActor(loseDialog);
    }

    public void showWinDialog(long sessionTime, int score) {
        winDialog.setTime(sessionTime);
        winDialog.setScore(score);
        addActor(winDialog);
    }

    public void showPauseDialog() {
        addActor(pauseDialog);
    }

    public void hideDialogs() {
        winDialog.remove();
        loseDialog.remove();
        pauseDialog.remove();
    }
}
