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
    private DungeonRoom currentRoom;
    private MonsterStore monsterStore;
    private DungeonRenderer dungeonRenderer;

    public GameService(Player player, Dungeon dungeon, MonsterStore monsterStore, DungeonRenderer dungeonRenderer) {
        this.player = player;
        this.dungeon = dungeon;
        this.currentRoom = dungeon.getRoomForPosition(player.getPosition());
        this.monsterStore = monsterStore;
        this.dungeonRenderer = dungeonRenderer;
    }

    public void movePlayer(Direction direction){
        List<Monster> monstersInCurrentRoom;
        if(currentRoom != null){
            monstersInCurrentRoom = monsterStore.findByRoomNumber(currentRoom.getRoomNumber());
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
        if(playerEnteredNewRoom()){
            changeRoom();
        }
    }

    public void moveMonsters(){
        for (Monster monster : monsterStore.findByRoomNumber(player.getRoomNumber())){
            if(monster.getPosition().isAdjacent(player.getPosition())){
                dungeonRenderer.renderAttack(monster, player);
                monster.attack(player);
            }else{
                MonsterMovement monsterMovement = new MonsterMovement(monster, player, dungeon, monsterStore.findByRoomNumber(currentRoom.getRoomNumber()));
                switch (monster.getMovementType()){
                    case APPROACH:
                        monsterMovement.moveTowardPlayer();
                        dungeonRenderer.renderDungeon();
                        break;
                    case STATIONARY:
                        break;
                    case RANDOM:
                        monsterMovement.moveRandom();
                        dungeonRenderer.renderDungeon();
                        break;
                }
            }

        }
    }
    public boolean playerEnteredNewRoom(){
        return currentRoom != dungeon.getRoomForPosition(player.getPosition()) && dungeon.getRoomForPosition(player.getPosition()) != null;
    }

    private void changeRoom(){
        currentRoom = dungeon.getRoomForPosition(player.getPosition());
    }



}
