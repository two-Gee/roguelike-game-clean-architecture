package com.example.domain;

import com.example.domain.Item.Item;
import com.example.domain.Monster.Monster;

import java.util.UUID;


public class Player extends LivingEntity {
    private Item equippedWeapon;

    public Player(int health, int attack, int roomID, Position position, String name) {
        super(health, attack, roomID, position, name);
    }
}
