package com.example.application;

import com.example.domain.Dungeon;
import com.example.domain.Monster.Monster;
import com.example.domain.MovementStrategy;
import com.example.domain.Player;
import com.example.domain.Position;

public class ApproachMovementStrategy implements MovementStrategy {
    @Override
    public Position getNextPosition(Monster monster, Player player) {
        Position playerPos = player.getPosition();
        Position monsterPos = monster.getPosition();
        int xDiff = playerPos.getxPos() - monsterPos.getxPos();
        int yDiff = playerPos.getyPos() - monsterPos.getyPos();

        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            return new Position(monsterPos.getxPos() + Integer.signum(xDiff), monsterPos.getyPos());
        } else {
            return new Position(monsterPos.getxPos(), monsterPos.getyPos() + Integer.signum(yDiff));
        }
    }
}