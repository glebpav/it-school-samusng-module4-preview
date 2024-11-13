package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class WinDialog extends Dialog {

    private final int DIALOG_WIDTH = 400;
    private final int DIALOG_HEIGHT = 300;
    private final int PADDING_TOP = 40;
    private final int BUTTON_HEIGHT = 70;
    private final int BUTTON_WIDTH = 120;

    public TextButton homeButton;
    private final Label timeLabel;
    private final Label scoreLabel;

    public WinDialog(Skin skin) {
        super("", skin);
        homeButton = new TextButton(localizationBundle.get("homeButtonText"), skin);
        timeLabel = new Label("00:00", skin);
        scoreLabel = new Label("0", skin);

        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setPosition(SCREEN_WIDTH / 2f - getWidth() / 2, SCREEN_HEIGHT / 2f - getHeight() / 2);

        getContentTable().columnDefaults(2);
        getContentTable().align(Align.center);
        getContentTable().add(new Label(localizationBundle.get("winDialogText"), skin)).padTop(PADDING_TOP).colspan(2);
        getContentTable().row();
        getContentTable().add(new Label("", skin)).align(Align.right);
        getContentTable().add(timeLabel).align(Align.left);
        getContentTable().row();
        getContentTable().add(new Label("", skin)).align(Align.right);
        getContentTable().add(scoreLabel).align(Align.left);
        getContentTable().row();
        getContentTable().add(homeButton).height(BUTTON_HEIGHT).width(BUTTON_WIDTH).padTop(PADDING_TOP).colspan(2);

    }

    public void setTime(long time) {
        String timeColonPattern = "mm:ss";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        LocalTime colonTime = LocalTime.of(0, (int) (time / 1000 / 60 % 60), (int) (time / 1000 % 60));
        timeLabel.setText(localizationBundle.format("timeLabelText", timeColonFormatter.format(colonTime)));
    }

    public void setScore(int score) {
        scoreLabel.setText(localizationBundle.format("scoreLabelText", score));
    }

}
