package ru.samsung.gamestudio.ui.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.samsung.gamestudio.ui.UiComponent;
import ru.samsung.gamestudio.ui.components.LiveBackground;

import static ru.samsung.gamestudio.game.GameSettings.localizationBundle;

public class SettingsUi extends UiComponent {

    public TextButton homeButton;
    public TextButton resetLevelsButton;

    public SettingsUi(Skin skin) {
        super();

        homeButton = new TextButton(localizationBundle.get("homeButtonText"), skin);
        resetLevelsButton = new TextButton(localizationBundle.get("resetLevelsButtonText"), skin);
        Label titleLabel = new Label(localizationBundle.get("settingsScreenTitleText"), skin, "labelTitle");
        LiveBackground liveBackground = new LiveBackground();

        addActor(liveBackground);

        add(titleLabel).height(100);
        row();
        add(resetLevelsButton).width(160).height(70);
        row();
        add(homeButton).width(160).height(70).padTop(20);
        row();
    }

}
