package com.example.domain;

import com.example.domain.Item.Item;
import com.example.domain.Monster.Monster;

import java.util.UUID;


public class Player extends LivingEntity {
    private Item equippedWeapon;

    public Player(int health, int attack, int roomID, Position position) {
        super(health, attack, roomID, position);
    }
//    public void moveInDirection(Direction direction) {
//        Position newPosition = new Position(this.getPosition().getxPos(), this.getPosition().getyPos());
//        this.move(newPosition);
//    }
}
