package com.example.domain.monster;

import com.example.domain.MovementStrategy;
import com.example.domain.Position;

public class Goblin extends Monster{
    public Goblin(int roomID, Position position, MovementStrategy movementStrategy) {
        super("Goblin", 10, 5, roomID, position, movementStrategy);
    }
}
