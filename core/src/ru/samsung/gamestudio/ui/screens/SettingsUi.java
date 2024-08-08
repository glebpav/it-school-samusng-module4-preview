package ru.samsung.gamestudio.ui.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.samsung.gamestudio.ui.UiComponent;

public class SettingsUi extends UiComponent {

    public TextButton homeButton;
    public TextButton resetLevelsButton;

    public SettingsUi(Skin skin) {
        super();

        homeButton = new TextButton("home", skin);
        resetLevelsButton = new TextButton("reset levels", skin);
        Label titleLabel = new Label("Settings", skin);

        add(titleLabel).height(100);
        row();
        add(resetLevelsButton). width(120).height(70);
        row();
        add(homeButton).width(120).height(70).padTop(20);
        row();

    }

}
