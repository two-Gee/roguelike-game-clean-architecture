package com.example.application;

import com.example.application.Factories.MonsterFactory;
import com.example.domain.Direction;
import com.example.domain.Dungeon;
import com.example.domain.GameState;
import com.example.domain.Monster.Monster;
import com.example.domain.Player;
import com.example.domain.map.DungeonRoom;

import java.util.*;

public class GameService {

    private GameState gameState;
    private Player player;
    private Dungeon dungeon;
    private DungeonRoom currentRoom;
    private MonsterStore monsterStore;
    private List<Monster> monstersInCurrentRoom;

    public GameService(Player player, Dungeon dungeon, Map<UUID, Monster> monsterList) {
        this.player = player;
        this.dungeon = dungeon;
        this.currentRoom = dungeon.getRoomForPosition(player.getPosition());
        this.monsterStore = new MonsterStore(monsterList);
        this.monstersInCurrentRoom = monsterStore.findByRoomID(currentRoom.getRoomNumber());
    }

    public void movePlayer(Direction direction){
        if(currentRoom != null){
            monstersInCurrentRoom = monsterStore.findByRoomID(currentRoom.getRoomNumber());
        }else{
            monstersInCurrentRoom = new ArrayList<>();
        }
        PlayerMovement playerMovement = new PlayerMovement(player, dungeon, monstersInCurrentRoom);
        playerMovement.moveInDirection(direction);
        if(playerEnteredNewRoom()){
            changeRoom();
        }
    }

    public void moveMonsters(){
        for (Monster monster : monstersInCurrentRoom){
            MonsterMovement monsterMovement = new MonsterMovement(monster, player, dungeon, monsterStore.findByRoomID(currentRoom.getRoomNumber()));
            monsterMovement.moveTowardPlayer();
        }
    }

    private void changeRoom(){
        currentRoom = dungeon.getRoomForPosition(player.getPosition());
        monstersInCurrentRoom = monsterStore.findByRoomID(currentRoom.getRoomNumber());
    }

    private boolean playerEnteredNewRoom() {
        return dungeon.getRoomForPosition(player.getPosition()) != currentRoom && dungeon.getRoomForPosition(player.getPosition()) != null;

    }


}
