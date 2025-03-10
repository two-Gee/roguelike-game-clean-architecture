package com.example.application;

import com.example.domain.Dungeon;
import com.example.domain.Monster.Monster;
import com.example.domain.Player;
import com.example.domain.Position;

import java.util.List;

public class MonsterMovement {
    private Dungeon dungeon;
    private Monster monster;
    private List<Monster> otherMonsters;
    private Player player;

    public MonsterMovement(Monster monster, Player player, Dungeon dungeon, List<Monster> otherMonsters) {
        this.dungeon = dungeon;
        this.monster = monster;
        this.player = player;
        this.otherMonsters = otherMonsters;
    }

    public void moveTowardPlayer() {
        Position playerPos = player.getPosition();
        Position monsterPos = monster.getPosition();
        int xDiff = playerPos.getxPos() - monsterPos.getxPos();
        int yDiff = playerPos.getyPos() - monsterPos.getyPos();

        Position newPos;
        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            if (xDiff > 0) {
                newPos = new Position(monsterPos.getxPos() + 1, monsterPos.getyPos());
            } else {
                newPos = new Position(monsterPos.getxPos() - 1, monsterPos.getyPos());
            }
        } else {
            if (yDiff > 0) {
                newPos = new Position(monsterPos.getxPos(), monsterPos.getyPos() + 1);
            } else {
                newPos = new Position(monsterPos.getxPos(), monsterPos.getyPos() - 1);
            }
        }
        for (Monster otherMonster : otherMonsters) {
            if (otherMonster.getPosition().equals(newPos)) {
                return;
            }
        }
        if(newPos.equals(playerPos)){
            monster.attack(player);
            return;
        }
        if(dungeon.getTile(newPos).isWalkable() && dungeon.getRoomForPosition(newPos) != null){
            monster.move(newPos);
        }
    }
}
