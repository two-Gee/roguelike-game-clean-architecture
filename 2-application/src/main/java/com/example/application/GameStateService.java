package com.example.application;

import com.example.application.stores.MonsterStore;
import com.example.domain.Player;

public class GameStateService {

    private DungeonRenderer dungeonRenderer;
    private MonsterStore monsterStore;

    private boolean gameOver;
    public GameStateService(DungeonRenderer dungeonRenderer, MonsterStore monsterStore) {
        this.dungeonRenderer = dungeonRenderer;
        this.monsterStore = monsterStore;
        this.gameOver = false;
    }

    public boolean checkGameLost(Player player) {
        if (player.isDead()) {
            dungeonRenderer.renderGameLost();
            return true;
        }
        return false;
    }

    public boolean checkGameWon() {
        if (monsterStore.getMonsters().isEmpty()) {
            dungeonRenderer.renderWin();
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return gameOver;
    }

}