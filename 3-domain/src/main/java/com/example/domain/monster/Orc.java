package com.example.domain.monster;

import com.example.domain.MovementStrategy;
import com.example.domain.Position;

public class Orc extends Monster{
    public Orc(int roomID, Position position, MovementStrategy movementStrategy) {
        super("Orc", 15, 8, roomID, position, movementStrategy);
    }
}
