package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class PauseDialog extends Dialog {

    private final int PAUSE_WIDTH = 400;
    private final int PAUSE_HEIGHT = 300;
    private final int BUTTON_WIDTH = 120;
    private final int BUTTON_HEIGHT = 70;
    private final int LABEL_HEIGHT = 150;

    public final TextButton resumeButton;
    public final TextButton homeButton;

    public PauseDialog(Skin skin) {
        super("", skin);

        Label label = new Label(localizationBundle.get("pauseDialogText"), skin);
        resumeButton = new TextButton(localizationBundle.get("resumeButtonText"), skin);
        homeButton = new TextButton(localizationBundle.get("homeButtonText"), skin);

        setSize(PAUSE_WIDTH, PAUSE_HEIGHT);
        setPosition(SCREEN_WIDTH / 2f - getWidth() / 2, SCREEN_HEIGHT / 2f - getHeight() / 2);

        columnDefaults(2);
        getContentTable().add(label).align(Align.center).height(LABEL_HEIGHT).colspan(2);
        getContentTable().row();
        getContentTable().add(resumeButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        getContentTable().add(homeButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
    }
}
