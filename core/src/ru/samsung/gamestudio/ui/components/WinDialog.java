package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static ru.samsung.gamestudio.game.GameSettings.SCREEN_HEIGHT;
import static ru.samsung.gamestudio.game.GameSettings.SCREEN_WIDTH;

public class WinDialog extends Dialog {

    public TextButton homeButton;
    private Label timeLabel;
    private Label scoreLabel;

    public WinDialog(Skin skin) {
        super("", skin);
        homeButton = new TextButton("home", skin);
        timeLabel = new Label("00:00", skin);
        scoreLabel = new Label("0", skin);

        setSize(400, 300);
        setPosition(SCREEN_WIDTH / 2f - getWidth() / 2, SCREEN_HEIGHT / 2f - getHeight() / 2);

        getContentTable().columnDefaults(2);
        getContentTable().align(Align.center);
        getContentTable().add(new Label("Our congratulations:", skin)).padTop(40).colspan(2);
        getContentTable().row();
        getContentTable().add(new Label("Time:", skin)).align(Align.right);
        getContentTable().add(timeLabel).align(Align.left);
        getContentTable().row();
        getContentTable().add(new Label("Score:", skin)).align(Align.right);
        getContentTable().add(scoreLabel).align(Align.left);
        getContentTable().row();
        getContentTable().add(homeButton).height(70).width(120).padTop(40).colspan(2);

    }

    public void setTime(long time) {
        String timeColonPattern = "mm:ss";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        LocalTime colonTime = LocalTime.of(0, (int) (time / 1000 / 60), (int) (time / 1000 % 60));
        System.out.println("time: " + timeColonFormatter.format(colonTime));
        timeLabel.setText(timeColonFormatter.format(colonTime));
    }

    public void setScore(int score) {
        System.out.println("score: " + score);
        scoreLabel.setText(String.valueOf(score));
    }

}
