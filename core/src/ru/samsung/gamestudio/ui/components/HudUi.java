package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.*;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class HudUi extends Table {

    private final Label scoreLabel;
    private final Label leftLivesLabel;

    public HudUi(Skin skin) {
        scoreLabel = new Label("", skin);
        leftLivesLabel = new Label("", skin);

        add(scoreLabel).padRight(20);
        add(leftLivesLabel).padLeft(20);
    }

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void setLeftLives(int leftLives) {
        leftLivesLabel.setText("Left Lives: " + leftLives);
    }

    public void clearHud() {
        scoreLabel.setText("Score: 0");
        leftLivesLabel.setText("Left Lives: " + PLAYER_LIVES);
    }

}
