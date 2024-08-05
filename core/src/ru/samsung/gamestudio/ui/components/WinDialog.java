package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WinDialog extends Dialog {

    public TextButton homeButton;
    private Label timeLabel;

    public WinDialog(Skin skin) {
        super("You won!", skin);
        homeButton = new TextButton("home", skin);
        timeLabel = new Label("00:00", skin);

        setSize(400, 300);

        getContentTable().columnDefaults(2);
        getContentTable().align(Align.center);
        getContentTable().add(new Label("Our congratulations:", skin)).padTop(40).colspan(2);
        getContentTable().row();
        getContentTable().add(new Label("Time:", skin)).align(Align.right);
        getContentTable().add(timeLabel).align(Align.left);
        getContentTable().row();
        getContentTable().add(homeButton).height(70).width(120).padTop(40).colspan(2);
    }

    public void setTime(long time) {
        String timeColonPattern = "mm:ss";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        LocalTime colonTime = LocalTime.of(0, (int) (time / 1000 / 60), (int) (time / 1000 % 60));
        timeLabel.setText(timeColonFormatter.format(colonTime));
    }

}
