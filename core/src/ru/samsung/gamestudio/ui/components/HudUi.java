package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.*;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class HudUi extends Table {

    private final int HUD_PADDING = 30;
    private final int HUD_WIDTH = 100;

    private final Label scoreLabel;
    private final Label leftLivesLabel;
    public final TextButton pauseButton;

    public HudUi(Skin skin) {
        scoreLabel = new Label("", skin);
        leftLivesLabel = new Label("", skin);
        pauseButton = new TextButton(localizationBundle.get("pauseButtonText"), skin);

        setWidth(SCREEN_WIDTH);
        add(scoreLabel).padLeft(HUD_PADDING);
        add(leftLivesLabel).padLeft(HUD_PADDING);
        add().expand();
        add(pauseButton).width(HUD_WIDTH).padRight(HUD_PADDING);
    }

    public void setScore(int score) {
        scoreLabel.setText(localizationBundle.format("scoreLabelText", score));
    }

    public void setLeftLives(int leftLives) {
        leftLivesLabel.setText(localizationBundle.format("leftLivesLabelText", leftLives));
    }

    public void clearHud() {
        scoreLabel.setText(localizationBundle.format("scoreLabelText", 0));
        leftLivesLabel.setText(localizationBundle.format("leftLivesLabelText", PLAYER_LIVES));
    }

}
