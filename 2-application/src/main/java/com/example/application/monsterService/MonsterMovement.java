package com.example.application.monsterService;

import com.example.domain.Dungeon;
import com.example.domain.item.Item;
import com.example.domain.monster.Monster;
import com.example.domain.Player;
import com.example.domain.Position;

import java.util.List;

public class MonsterMovement {
    private Dungeon dungeon;
    private Monster monster;
    private List<Monster> otherMonsters;
    private Player player;
    private List<Item> items;

    public MonsterMovement(Monster monster, Player player, Dungeon dungeon, List<Monster> otherMonsters, List<Item> items) {
        this.dungeon = dungeon;
        this.monster = monster;
        this.player = player;
        this.otherMonsters = otherMonsters;
        this.items = items;
    }


    public void move() {
        Position newPos = monster.getNextPosition(player);

        // Check if newPos is null and return early to prevent NullPointerException
        if (newPos == null) {
            return;
        }

        for (Monster otherMonster : otherMonsters) {
            if (otherMonster.getPosition().equals(newPos)) {
                return;
            }
        }
        if (newPos.equals(player.getPosition())) {
            return;
        }
        for(Item item : items){
            if(item.getPosition().equals(newPos)){
                return;
            }
        }
        if (dungeon.getTile(newPos).isWalkable() && dungeon.getRoomForPosition(newPos) != null) {
            monster.move(newPos);
        }
    }
}
