package com.example.application.playerService;

import com.example.application.DungeonRenderer;
import com.example.application.stores.MonsterStore;
import com.example.domain.Player;
import com.example.domain.Position;
import com.example.domain.monster.Monster;

import java.util.ArrayList;
import java.util.List;

public class PlayerAttack {
    private Player player;
    private MonsterStore monsterStore;
    private DungeonRenderer dungeonRenderer;

    public PlayerAttack(Player player, MonsterStore monsterStore, DungeonRenderer dungeonRenderer) {
        this.player = player;
        this.monsterStore = monsterStore;
        this.dungeonRenderer = dungeonRenderer;
    }

    public void attackMonster(Position position){
        List<Monster> monstersInCurrentRoom = monsterStore.findByRoomNumber(player.getRoomNumber());

        for(Monster monster : monstersInCurrentRoom){
            if(monster.getPosition().equals(position)){
                player.attack(monster);
                dungeonRenderer.renderNotification(player.getName() + " attacks " + monster.getName() + "! " + monster.getName() + " took " + player.getAttack() + " damage.");

                if (monster.isDead()) {
                    removeDeadMonster(monster);
                }

                return;
            }
        }
    }

    private void removeDeadMonster(Monster monster){
            monsterStore.remove(monster.getId());
            dungeonRenderer.renderNotification(player.getName() + " killed " + monster.getName() + "!");

    }
}
