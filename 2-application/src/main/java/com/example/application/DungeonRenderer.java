package com.example.application;

import com.example.domain.item.Consumables;
import com.example.domain.item.Weapon;
import com.example.domain.LivingEntity;
import com.example.domain.monster.Monster;

public interface DungeonRenderer {
    public void renderDungeon();
    public void renderGameLost();
    public void renderWin();
    public void renderNotification(String message);
    public void startRenderingLoop(GameService gameService);
}
