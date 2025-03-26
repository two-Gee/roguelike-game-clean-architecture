package com.example.application;

import com.example.domain.item.Consumables;
import com.example.domain.item.Weapon;
import com.example.domain.LivingEntity;
import com.example.domain.monster.Monster;

public interface DungeonRenderer {
    public void renderDungeon();
    public void renderAttack(LivingEntity attacker, LivingEntity target);
    public void renderDeathOfMonster(Monster monster);
    public void renderGameLost();
    public void renderWin();
    public void renderWeaponPickup(Weapon weapon);
    public void renderUseOfConsumable(Consumables consumable);
    public void startRenderingLoop(GameService gameService);
}
