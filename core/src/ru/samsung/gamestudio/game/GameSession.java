package ru.samsung.gamestudio.game;

import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class GameSession {

    public GameState state;
    long sessionStartTime;
    long pauseStartTime;
    private int score;

    public void startGame() {
        state = GameState.PLAYING;
        score = 0;
        sessionStartTime = TimeUtils.millis();
    }

    public void pauseGame() {
        state = GameState.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }

    public void resumeGame() {
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
    }

    public void endGame() {
        // todo: implement end case
        state = GameState.ENDED;
    }

}
