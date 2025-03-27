package com.example.application;

public interface DungeonRenderer {
    public void renderDungeon();
    public void renderGameLost();
    public void renderWin();
    public void renderNotification(String message);
    public void startRenderingLoop(GameService gameService);
}
