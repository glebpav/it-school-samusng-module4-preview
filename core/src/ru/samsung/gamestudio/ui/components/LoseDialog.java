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

    public TextButton restartButton;
    public TextButton homeButton;
    private final Label textLabel;

    public LoseDialog(Skin skin) {
        super("", skin);

        restartButton = new TextButton(localizationBundle.get("restartButtonText"), skin);
        homeButton = new TextButton(localizationBundle.get("homeButtonText"), skin);
        textLabel = new Label("", skin);
        textLabel.setScale(10);

        setSize(400, 300);
        setPosition(SCREEN_WIDTH / 2f - getWidth() / 2, SCREEN_HEIGHT / 2f - getHeight() / 2);

        getContentTable().columnDefaults(2);

        getContentTable().align(Align.center);
        getContentTable().add(textLabel).height(150).colspan(2);
        getContentTable().row();
        getContentTable().add(restartButton).height(70).width(120);
        getContentTable().add(homeButton).height(70).width(120);
    }

    public void setText(String text) {
        textLabel.setText(text);
    }

}
