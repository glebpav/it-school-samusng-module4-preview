package ru.samsung.gamestudio.game;

import com.badlogic.gdx.utils.TimeUtils;


public class GameSession {

    private GameState state;
    private long sessionStartTime;
    private long pauseStartTime;
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

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public GameState getState() {
        return state;
    }

    public long getSessionStartTime() {
        return sessionStartTime;
    }

}
