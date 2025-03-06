package com.example.domain.Monster;


import com.example.domain.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;


public class Monster extends LivingEntity {
    private String name;
    private UUID id;


    public Monster(String name, int health, int damage) {
        super(health, damage);
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

//    public void moveTowardPlayer(Player target, List<Monster> otherMonsters){
//        Position playerPos = target.getPosition();
//        Position monsterPos = this.getPosition();
//        int xDiff = playerPos.getxPos() - monsterPos.getxPos();
//        int yDiff = playerPos.getyPos() - monsterPos.getyPos();
//        if (Math.abs(xDiff) == 1 && Math.abs(yDiff) == 1) {
//            this.attack(target);
//        }else {
//            Position newPos;
//            if (Math.abs(xDiff) > Math.abs(yDiff)){
//                if (xDiff > 0){
//                    newPos = new Position(monsterPos.getxPos() + 1, monsterPos.getyPos());
//                } else {
//                    newPos = new Position(monsterPos.getxPos() - 1, monsterPos.getyPos());
//                }
//            } else {
//                if (yDiff > 0){
//                    newPos = new Position(monsterPos.getxPos(), monsterPos.getyPos() + 1);
//                } else {
//                    newPos = new Position(monsterPos.getxPos(), monsterPos.getyPos() - 1);
//                }
//            }
//            for(Monster monster : otherMonsters){
//                if (monster.getPosition().equals(newPos)){
//                    return;
//                }
//            }
//            this.setPosition(newPos);
//
//        }
//    }
}
