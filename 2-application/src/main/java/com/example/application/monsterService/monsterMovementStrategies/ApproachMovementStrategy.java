package com.example.application.monsterService.monsterMovementStrategies;

import com.example.domain.monster.Monster;
import com.example.domain.MovementStrategy;
import com.example.domain.Player;
import com.example.domain.Position;

public class ApproachMovementStrategy implements MovementStrategy {
    @Override
    public Position getNextPosition(Monster monster, Player player) {
        Position playerPos = player.getPosition();
        Position monsterPos = monster.getPosition();
        int xDiff = playerPos.getX_POS() - monsterPos.getX_POS();
        int yDiff = playerPos.getY_POS() - monsterPos.getY_POS();

        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            return new Position(monsterPos.getX_POS() + Integer.signum(xDiff), monsterPos.getY_POS());
        } else {
            return new Position(monsterPos.getX_POS(), monsterPos.getY_POS() + Integer.signum(yDiff));
        }
    }
}