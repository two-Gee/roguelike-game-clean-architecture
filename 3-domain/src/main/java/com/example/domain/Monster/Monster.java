package com.example.domain.Monster;



import com.example.domain.*;


import java.util.List;
import java.util.Random;
import java.util.UUID;


public class Monster extends LivingEntity {
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
    }
    //    public void moveRandom(){
//        Random rnd = new Random();
//        int randomNumber = rnd.nextInt(4);
//        super.move(Direction.values()[randomNumber]);
//    }
}
