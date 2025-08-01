package com.example.domain;

public class LivingEntity {

    private String name;
    private Position position;
    private int health;
    private int attack;
    private boolean isDead;
    private int roomNumber;


    public LivingEntity(int health, int attack, int roomID, Position position, String name) {
        this.roomNumber = roomID;
        this.health = health;
        this.attack = attack;
        this.isDead = false;
        this.position = position;
        this.name=name;
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public void move(Position position) {
        this.position = position;
    }

    public void attack(LivingEntity target) {
        target.takeDamage(this.attack);
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.isDead = true;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}

