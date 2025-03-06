package com.example.domain.Monster;


import com.example.domain.Direction;
import com.example.domain.LivingEntity;
import com.example.domain.Position;

import java.util.Random;


public class Monster extends LivingEntity {
    private String name;
    private int health;
    private int damage;
    private Position position;
    private int speed;

    public Monster(String name, int health, int damage, int speed) {
        super(health, damage);
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }

//    public void moveRandom(){
//        Random rnd = new Random();
//        int randomNumber = rnd.nextInt(4);
//        super.move(Direction.values()[randomNumber]);
//    }
}
