package com.example.domain.monster;



import com.example.domain.*;


import java.util.UUID;


public abstract class Monster extends LivingEntity {
    private UUID id;
    private MovementStrategy movementStrategy;

    public UUID getId() {
        return id;
    }

    public Monster(String name, int health, int damage, int roomID, Position position, MovementStrategy movementStrategy) {
        super(health, damage, roomID, position, name);
        this.id = UUID.randomUUID();
        this.movementStrategy=movementStrategy;
    }

    public Position getNextPosition(Player player) {
        return movementStrategy.getNextPosition(this, player);
    };
}
