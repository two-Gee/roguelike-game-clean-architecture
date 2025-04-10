package com.example.domain.monster;

import com.example.domain.MovementStrategy;
import com.example.domain.Position;

public class Troll extends Monster{

    public Troll(int roomID, Position position, MovementStrategy movementStrategy) {
        super("Troll", 20, 10, roomID, position, movementStrategy);
    }
}
