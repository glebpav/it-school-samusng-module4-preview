package ru.samsung.gamestudio.world.listeners;

public interface WorldEventListener {

    void onLose(String loseText);
    void onWin();
    void onCollectCoin(int coinValue);
    void onHitEnemy(int liveMinus);

}
