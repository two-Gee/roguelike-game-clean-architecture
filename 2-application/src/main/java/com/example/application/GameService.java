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
    public GameService(Player player, Dungeon dungeon, MonsterStore monsterStore) {
        this.player = player;
        this.dungeon = dungeon;
        this.monsterStore = monsterStore;
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
                if(monster.isDead()){
                    monsterStore.remove(monster.getId());
                }
                return;
            }
        }
        playerMovement.moveInDirection(direction);
    }

    public void moveMonsters(){
        if(player.getRoomNumber()  != -1){
            boolean renderDungeon = false;
            for (Monster monster : monsterStore.findByRoomNumber(player.getRoomNumber())){
                if(monster.getPosition().isAdjacent(player.getPosition())){
                    monster.attack(player);
                }else{
                    MonsterMovement monsterMovement = new MonsterMovement(monster, player, dungeon, monsterStore.findByRoomNumber(player.getRoomNumber()));
                    monsterMovement.move();
                }
            }
            if(renderDungeon){

            }
        }
    }
}
