package com.example.domain;

import java.util.UUID;

public class LivingEntity {
    private Position position;
    private int health;
    private int attack;
    private boolean isDead;
    private UUID roomID;

    public LivingEntity(int health, int attack) {
        this.position = new Position(0, 0);
        this.health = health;
        this.attack = attack;
        this.isDead = false;
    }

    public Position getPosition() {
        return position;
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

}

