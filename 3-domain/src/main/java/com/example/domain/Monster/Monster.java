package com.example.domain.Monster;



import com.example.domain.Direction;
import com.example.domain.LivingEntity;
import com.example.domain.Position;


import java.util.List;
import java.util.Random;
import java.util.UUID;


public class Monster extends LivingEntity {
    private String name;
    private UUID id;
    private MonsterMovementType movementType;

    public UUID getId() {
        return id;
    }

    public Monster(String name, int health, int damage, int roomID, Position position, MonsterMovementType movementType) {
        super(health, damage, roomID, position);
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }


//    public void moveRandom(){
//        Random rnd = new Random();
//        int randomNumber = rnd.nextInt(4);
//        super.move(Direction.values()[randomNumber]);
//    }
}
