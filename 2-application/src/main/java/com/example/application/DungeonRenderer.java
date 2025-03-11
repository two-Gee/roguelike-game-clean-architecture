package com.example.application;

import com.example.domain.LivingEntity;
import com.example.domain.Monster.Monster;

public interface DungeonRenderer {
    public void renderDungeon();
    public void renderAttack(LivingEntity attacker, LivingEntity target);
    public void renderDeathOfMonster(Monster monster);
    public void renderGameOver();
}
