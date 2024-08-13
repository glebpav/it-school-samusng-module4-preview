package ru.samsung.gamestudio.ui.screens;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import ru.samsung.gamestudio.ui.UiComponent;

public class MenuUi extends UiComponent {

    public ScrollPane scrollPane;
    public List<String> listView;

    public TextButton startButton;
    public TextButton exitButton;
    public TextButton settingsButton;

    public MenuUi(Skin skin) {

        startButton = new TextButton("Start this game", skin);
        exitButton = new TextButton("Exit game", skin);
        settingsButton = new TextButton("Settings", skin);

        listView = new List<>(skin);
        scrollPane = new ScrollPane(listView, skin);

        listView.setAlignment(Align.center);
        listView.setWidth(300);
        scrollPane.setActor(listView);
        // scrollPane.setSize(400, 250);

        columnDefaults(2);
        add(scrollPane).width(400).height(250).colspan(2).space(10);
        row();
        add(startButton).width(400).height(60).colspan(2).space(10);
        row();
        add(exitButton).width(195).height(60).space(10);
        add(settingsButton).width(195).height(60).space(10);

    }

}
