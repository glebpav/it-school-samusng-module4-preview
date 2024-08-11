package ru.samsung.gamestudio.ui.screens;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ru.samsung.gamestudio.ui.UiComponent;
import ru.samsung.gamestudio.ui.components.HudUi;
import ru.samsung.gamestudio.ui.components.LoseDialog;
import ru.samsung.gamestudio.ui.components.WinDialog;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class GameUi extends UiComponent {

    public final LoseDialog loseDialog;
    public final WinDialog winDialog;

    public final HudUi hudUi;

    public GameUi(Skin skin) {

        hudUi = new HudUi(skin);
        loseDialog = new LoseDialog(skin);
        winDialog = new WinDialog(skin);

        addActor(hudUi);
        hudUi.setPosition(SCREEN_WIDTH / 2f - hudUi.getWidth() / 2, SCREEN_HEIGHT - 50);
    }

    public void makeHudCentered(float cameraX) {
        hudUi.setPosition(cameraX - hudUi.getWidth() / 2, SCREEN_HEIGHT - 50);
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

    public void hideDialogs() {
        winDialog.remove();
        loseDialog.remove();
    }

}
