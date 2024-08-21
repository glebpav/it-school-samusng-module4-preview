package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class PauseDialog extends Dialog {

    public final TextButton resumeButton;
    public final TextButton homeButton;

    public PauseDialog(Skin skin) {
        super("", skin);

        Label label = new Label(localizationBundle.get("pauseDialogText"), skin);
        resumeButton = new TextButton(localizationBundle.get("resumeButtonText"), skin);
        homeButton = new TextButton(localizationBundle.get("homeButtonText"), skin);

        setSize(400, 300);
        setPosition(SCREEN_WIDTH / 2f - getWidth() / 2, SCREEN_HEIGHT / 2f - getHeight() / 2);

        columnDefaults(2);
        getContentTable().add(label).align(Align.center).height(150).colspan(2);
        getContentTable().row();
        getContentTable().add(resumeButton).width(120).height(70);
        getContentTable().add(homeButton).width(120).height(70);
    }
}
