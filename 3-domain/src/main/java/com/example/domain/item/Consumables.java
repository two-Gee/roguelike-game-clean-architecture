package com.example.domain.item;

import com.example.domain.Position;

public class Consumables extends Item {

    private int healthPoints;
    public Consumables(String name, int healthPoints, Position position, int roomNumber) {
        super(name, position, roomNumber);
        this.healthPoints = healthPoints;
    }
    public int getHealthPoints() {
        return healthPoints;
    }

}
