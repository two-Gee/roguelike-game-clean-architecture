package com.example.Item;

public class Weapon extends Item {
    private int damage;

    public Weapon(String name, String description, int damage, char symbol) {
        super(name, description, symbol);
        this.damage = damage;
    }

    public int getDamage() {
        return this.damage;
    }
}
