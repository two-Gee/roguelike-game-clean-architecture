package com.example.application.monsterService;

import com.example.application.DungeonRenderer;
import com.example.application.GameStateService;
import com.example.application.stores.ItemStore;
import com.example.application.stores.MonsterStore;
import com.example.domain.Dungeon;
import com.example.domain.Player;
import com.example.domain.monster.Monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterService {
    private List<Monster> monstersInCurrentRoom;
    private Player player;
    private DungeonRenderer dungeonRenderer;
    private MonsterStore monsterStore;
    private Dungeon dungeon;
    private ItemStore itemStore;

    public MonsterService(Player player, DungeonRenderer dungeonRenderer, MonsterStore monsterStore, Dungeon dungeon, ItemStore itemStore) {
        this.player = player;
        this.dungeonRenderer = dungeonRenderer;
        this.monstersInCurrentRoom = new ArrayList<>();
        this.monsterStore = monsterStore;
        this.dungeon = dungeon;
        this.itemStore = itemStore;
    }

    public void handleMonster(GameStateService gameStateService) {
        monstersInCurrentRoom = getMonstersInCurrentRoom();

        if(!monstersInCurrentRoom.isEmpty()) {
            boolean renderDungeon = false;
            for (Monster monster : monstersInCurrentRoom) {
                if (monster.getPosition().isAdjacent(player.getPosition())) {
                    monster.attack(player);
                    dungeonRenderer.renderNotification(monster.getName() + " attacks " + player.getName() + "! " + player.getName() + " took " + monster.getAttack() + " damage.");
                    if (gameStateService.checkGameLost(player)) return;
                } else {
                    MonsterMovement monsterMovement = new MonsterMovement(monster, player, dungeon, monsterStore.findByRoomNumber(player.getRoomNumber()), itemStore.findByRoomNumber(player.getRoomNumber()));
                    monsterMovement.move();
                    renderDungeon = true;
                }
            }
            if (renderDungeon) dungeonRenderer.renderDungeon();
        }
    }

    private List<Monster> getMonstersInCurrentRoom() {
        if (player.getRoomNumber() != -1) {
            return monsterStore.findByRoomNumber(player.getRoomNumber());
        } else {
            return new ArrayList<>();
        }
    }
}
