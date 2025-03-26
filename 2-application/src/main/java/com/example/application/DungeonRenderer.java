package com.example.application;

import com.example.domain.Item.Consumables;
import com.example.domain.Item.Weapon;
import com.example.domain.LivingEntity;
import com.example.domain.Monster.Monster;

public interface DungeonRenderer {
    public void renderDungeon();
    public void renderAttack(LivingEntity attacker, LivingEntity target);
    public void renderDeathOfMonster(Monster monster);
    public void renderGameLost();
    public void renderWin();
    public void renderWeaponPickup(Weapon weapon);
    public void renderUseOfConsumable(Consumables consumable);
}
