package ru.samsung.gamestudio.ui.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.samsung.gamestudio.ui.UiComponent;
import ru.samsung.gamestudio.ui.components.LiveBackground;

import static ru.samsung.gamestudio.game.GameSettings.localizationBundle;

public class SettingsUi extends UiComponent {

    private final int LABEL_HEIGHT = 100;
    private final int BUTTON_WIDTH = 160;
    private final int BUTTON_HEIGHT = 70;
    private final int BUTTON_PADDING = 20;

    public TextButton homeButton;
    public TextButton resetLevelsButton;

    public SettingsUi(Skin skin) {
        super();

        homeButton = new TextButton(localizationBundle.get("homeButtonText"), skin);
        resetLevelsButton = new TextButton(localizationBundle.get("resetLevelsButtonText"), skin);
        Label titleLabel = new Label(localizationBundle.get("settingsScreenTitleText"), skin, "labelTitle");
        LiveBackground liveBackground = new LiveBackground();

        addActor(liveBackground);

        add(titleLabel).height(LABEL_HEIGHT);
        row();
        add(resetLevelsButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        row();
        add(homeButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padTop(BUTTON_PADDING);
        row();
    }

}
