package com.example.domain.item;

import com.example.domain.Position;

public class Weapon extends Item {
    private int attack;

    public Weapon(String name, int attack, Position position, int roomNumber) {
        super(name, position, roomNumber);
        this.attack = attack;
    }

    public int getAttack() {
        return this.attack;
    }
}
