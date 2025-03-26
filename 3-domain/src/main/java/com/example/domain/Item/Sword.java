package com.example.domain.item;

import com.example.domain.Position;

public class Sword extends Weapon {
    public Sword(Position position, int roomNumber) {
        super("Sword", 10, position, roomNumber);
    }
}
