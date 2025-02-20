package com.example.Item;

public class Consumables extends Item {
    public int getHealthPoints() {
        return healthPoints;
    }

    private int healthPoints;
    public Consumables(String name, String description, int healthPoints, char symbol) {
        super(name, description, symbol);
        this.healthPoints = healthPoints;
    }
}
