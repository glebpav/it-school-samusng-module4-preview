package ru.samsung.gamestudio.ui.screens;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import ru.samsung.gamestudio.ui.UiComponent;
import ru.samsung.gamestudio.ui.components.LiveBackground;

import static ru.samsung.gamestudio.game.GameSettings.localizationBundle;

public class MenuUi extends UiComponent {

    public ScrollPane scrollPane;
    public List<String> listView;

    public TextButton startButton;
    public TextButton exitButton;
    public TextButton settingsButton;

    public MenuUi(Skin skin) {
        LiveBackground liveBackground = new LiveBackground();
        startButton = new TextButton(localizationBundle.get("startButtonText"), skin);
        exitButton = new TextButton(localizationBundle.get("exitButtonText"), skin);
        settingsButton = new TextButton(localizationBundle.get("settingsButtonText"), skin);
        Label title = new Label(localizationBundle.get("menuScreenTitleText"), skin, "labelTitle");

        listView = new List<>(skin);
        scrollPane = new ScrollPane(listView, skin);

        listView.setAlignment(Align.center);
        listView.setWidth(300);
        scrollPane.setActor(listView);

        addActor(liveBackground);

        columnDefaults(2);
        add(title).colspan(2).padBottom(30);
        row();
        add(scrollPane).width(400).height(250).colspan(2).space(10);
        row();
        add(startButton).width(400).height(60).colspan(2).space(10);
        row();
        add(exitButton).width(195).height(60).space(10);
        add(settingsButton).width(195).height(60).space(10);
    }

}
