package com.example.application.monsterMovement;

import com.example.domain.monster.Monster;
import com.example.domain.MovementStrategy;
import com.example.domain.Player;
import com.example.domain.Position;

public class StationaryMovementStrategy implements MovementStrategy {
    @Override
    public Position getNextPosition(Monster monster, Player player) {
        return monster.getPosition();
    }
}