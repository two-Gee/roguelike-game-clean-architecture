package com.example.application.monsterMovement;

import com.example.domain.monster.Monster;
import com.example.domain.MovementStrategy;
import com.example.domain.Player;
import com.example.domain.Position;

import java.util.Random;

public class RandomMovementStrategy implements MovementStrategy {
    private static final Random random = new Random();

    @Override
    public Position getNextPosition(Monster monster, Player player) {
        Position monsterPos = monster.getPosition();
        int direction = random.nextInt(4);
        switch (direction) {
            case 0: return new Position(monsterPos.getX_POS() + 1, monsterPos.getY_POS());
            case 1: return new Position(monsterPos.getX_POS() - 1, monsterPos.getY_POS());
            case 2: return new Position(monsterPos.getX_POS(), monsterPos.getY_POS() + 1);
            case 3: return new Position(monsterPos.getX_POS(), monsterPos.getY_POS() - 1);
            default: return monsterPos;
        }
    }
}