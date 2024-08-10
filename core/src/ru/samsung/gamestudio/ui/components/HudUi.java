package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.w3c.dom.ls.LSOutput;
import ru.samsung.gamestudio.ui.UiComponent;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class HudUi extends Table {

    private final Label scoreLabel;
    private final Label leftLivesLabel;

    public HudUi(Skin skin) {
        
        scoreLabel = new Label("", skin);
        leftLivesLabel = new Label("" , skin);

        add(scoreLabel).padRight(50);
        add(leftLivesLabel).padLeft(50);
        setPosition((SCREEN_WIDTH - getWidth()) / 2, SCREEN_HEIGHT - 50);
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
