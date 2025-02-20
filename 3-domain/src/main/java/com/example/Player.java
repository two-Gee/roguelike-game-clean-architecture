package com.example;

import com.example.Item.Item;

public class Player extends Entity {
    private int health;
    private int attack;
    private Item equippedWeapon;

    public Player(int health, int attack, char symbol, TileType tileType) {
        super(tileType,  symbol);
        this.health = health;
        this.attack = attack;
    }

    public int getHealth() {
        return this.health;
    }

    public int getAttack() {
        return this.attack;
    }


    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

}
