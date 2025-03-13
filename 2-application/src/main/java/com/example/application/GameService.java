package com.example.application;

import com.example.application.Factories.MonsterFactory;
import com.example.domain.*;
import com.example.domain.Monster.Monster;
import com.example.domain.map.DungeonRoom;

import java.util.*;

public class GameService {

    private GameState gameState;
    private Player player;
    private Dungeon dungeon;
    private MonsterStore monsterStore;
    private DungeonRenderer dungeonRenderer;

    public GameService(Player player, Dungeon dungeon, MonsterStore monsterStore, DungeonRenderer dungeonRenderer) {
        this.player = player;
        this.dungeon = dungeon;
        this.monsterStore = monsterStore;
        this.dungeonRenderer = dungeonRenderer;
    }

    public void movePlayer(Direction direction){
        List<Monster> monstersInCurrentRoom;
        if(player.getRoomNumber() != -1){
            monstersInCurrentRoom = monsterStore.findByRoomNumber(player.getRoomNumber());
        }else{
            monstersInCurrentRoom = new ArrayList<>();
        }
        PlayerMovement playerMovement = new PlayerMovement(player, dungeon, monstersInCurrentRoom);
        Position newPos = player.getPosition().getAdjacentPosition(direction);
        for(Monster monster : monstersInCurrentRoom){
            if(monster.getPosition().equals(newPos)){
                player.attack(monster);
                dungeonRenderer.renderAttack(player, monster);
                if(monster.isDead()){
                    monsterStore.remove(monster.getId());
                    dungeonRenderer.renderDeathOfMonster(monster);
                }
                return;
            }
        }
        playerMovement.moveInDirection(direction);
        dungeonRenderer.renderDungeon();
    }

    public void moveMonsters(){
        if(player.getRoomNumber()  != -1){
            boolean renderDungeon = false;
            for (Monster monster : monsterStore.findByRoomNumber(player.getRoomNumber())){
                if(monster.getPosition().isAdjacent(player.getPosition())){
                    monster.attack(player);
                    dungeonRenderer.renderAttack(monster, player);
                }else{
                    MonsterMovement monsterMovement = new MonsterMovement(monster, player, dungeon, monsterStore.findByRoomNumber(player.getRoomNumber()));
                    switch (monster.getMovementType()){
                        case APPROACH:
                            monsterMovement.moveTowardPlayer();
                            renderDungeon = true;
                            break;
                        case STATIONARY:
                            break;
                        case RANDOM:
                            monsterMovement.moveRandom();
                            renderDungeon = true;
                            break;
                    }
                }
            }
            if(renderDungeon) dungeonRenderer.renderDungeon();
        }
    }
}
