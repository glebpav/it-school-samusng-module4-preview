package ru.samsung.gamestudio.ui.components;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class HudUi extends Table {

    private final Label scoreLabel;
    private final Label leftLivesLabel;
    final Image pauseButton;

    public HudUi(Skin skin) {

        scoreLabel = new Label("", skin);
        leftLivesLabel = new Label("" , skin);
        pauseButton = new Image(new Texture("texture/ui/pause_icon.png"));

        add(scoreLabel).padRight(20);
        add(leftLivesLabel).padLeft(20);
        add(pauseButton).size(20).padLeft(100);
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
