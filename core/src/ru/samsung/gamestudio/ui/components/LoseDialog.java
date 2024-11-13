package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import static ru.samsung.gamestudio.game.GameSettings.SCREEN_HEIGHT;
import static ru.samsung.gamestudio.game.GameSettings.SCREEN_WIDTH;
import static ru.samsung.gamestudio.game.GameSettings.localizationBundle;

public class LoseDialog extends Dialog {

    private final int DIALOG_WIDTH = 400;
    private final int DIALOG_HEIGHT = 300;
    private final int LABEL_HEIGHT = 150;
    private final int BUTTON_WIDTH = 120;
    private final int BUTTON_HEIGHT = 70;

    private final int LABEL_SCALE = 10;

    public TextButton restartButton;
    public TextButton homeButton;
    private final Label textLabel;

    public LoseDialog(Skin skin) {
        super("", skin);

        restartButton = new TextButton(localizationBundle.get("restartButtonText"), skin);
        homeButton = new TextButton(localizationBundle.get("homeButtonText"), skin);
        textLabel = new Label("", skin);
        textLabel.setScale(LABEL_SCALE);

        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setPosition(SCREEN_WIDTH / 2f - getWidth() / 2, SCREEN_HEIGHT / 2f - getHeight() / 2);

        getContentTable().columnDefaults(2);

        getContentTable().align(Align.center);
        getContentTable().add(textLabel).height(LABEL_HEIGHT).colspan(2);
        getContentTable().row();
        getContentTable().add(restartButton).height(BUTTON_HEIGHT).width(BUTTON_WIDTH);
        getContentTable().add(homeButton).height(BUTTON_HEIGHT).width(BUTTON_WIDTH);
    }

    public void setText(String text) {
        textLabel.setText(text);
    }

}
