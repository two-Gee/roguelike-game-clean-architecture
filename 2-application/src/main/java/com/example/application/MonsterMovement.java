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


    public void move() {
        Position newPos = monster.getNextPosition(player);
        for (Monster otherMonster : otherMonsters) {
            if (otherMonster.getPosition().equals(newPos)) {
                return;
            }
        }
        if (newPos.equals(player.getPosition())) {
            monster.attack(player);
            return;
        }
        if (dungeon.getTile(newPos).isWalkable() && dungeon.getRoomForPosition(newPos) != null) {
            monster.move(newPos);
        }
    }
}
