package com.example.domain;

import java.util.UUID;

public class LivingEntity {


    private Position position;
    private int health;
    private int attack;
    private boolean isDead;
    private int roomID;


    public LivingEntity(int health, int attack, int roomID, Position position) {
        this.roomID = roomID;
        this.health = health;
        this.attack = attack;
        this.isDead = false;
        this.position = position;
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

//    public void move(Direction direction) {
//        switch (direction) {
//            case NORTH:
//                this.move(new Position(this.position.getxPos(), this.position.getyPos() - 1));
//                break;
//            case SOUTH:
//                this.move(new Position(this.position.getxPos(), this.position.getyPos() + 1));
//                break;
//            case EAST:
//                this.move(new Position(this.position.getxPos() + 1, this.position.getyPos()));
//                break;
//            case WEST:
//                this.move(new Position(this.position.getxPos() - 1, this.position.getyPos()));
//                break;
//        }
//    }

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

    public boolean isAtPosition(Position position) {
            return this.position.equals(position);
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
}

