package com.example.application;

import com.example.application.monsterMovement.MonsterMovement;
import com.example.application.playerMovement.PlayerMovement;
import com.example.application.stores.ItemStore;
import com.example.application.stores.MonsterStore;
import com.example.domain.*;
import com.example.domain.item.Consumables;
import com.example.domain.item.Item;
import com.example.domain.item.Weapon;
import com.example.domain.monster.Monster;

import java.util.*;

public class GameService {
    private Player player;
    private Dungeon dungeon;
    private MonsterStore monsterStore;
    private ItemStore itemStore;
    private DungeonRenderer dungeonRenderer;
    private boolean gameOver;
    public GameService(Player player, Dungeon dungeon, MonsterStore monsterStore,ItemStore itemStore, DungeonRenderer dungeonRenderer) {
        this.player = player;
        this.dungeon = dungeon;
        this.monsterStore = monsterStore;
        this.itemStore = itemStore;
        this.dungeonRenderer = dungeonRenderer;
        gameOver=false;
    }

    public void movePlayer(Direction direction){
        List<Monster> monstersInCurrentRoom;
        List<Item> itemsInCurrentRoom;
        if(player.getRoomNumber() != -1){
            monstersInCurrentRoom = monsterStore.findByRoomNumber(player.getRoomNumber());
            itemsInCurrentRoom = itemStore.findByRoomNumber(player.getRoomNumber());
        }else{
            monstersInCurrentRoom = new ArrayList<>();
            itemsInCurrentRoom = new ArrayList<>();
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
                checkForWin();
                return;
            }
        }

        for(Item item : itemsInCurrentRoom){
            if(item.getPosition().equals(newPos)){
                if(item instanceof Weapon){
                    if(player.getEquippedWeapon() != null){
                        Weapon weapon = player.unEquipWeapon();
                        weapon.setPosition(item.getPosition());
                        weapon.setRoomNumber(item.getRoomNumber());
                        itemStore.add(weapon);
                    }
                    player.equipWeapon((Weapon) item);
                    itemStore.remove(item);
                    dungeonRenderer.renderWeaponPickup((Weapon) item);
                }else if(item instanceof Consumables){
                    player.heal(((Consumables) item).getHealthPoints());
                    itemStore.remove(item);
                    dungeonRenderer.renderUseOfConsumable((Consumables) item);
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
                    if(checkGameLost(player)) return;
                }else{
                    MonsterMovement monsterMovement = new MonsterMovement(monster, player, dungeon, monsterStore.findByRoomNumber(player.getRoomNumber()), itemStore.findByRoomNumber(player.getRoomNumber()));
                    monsterMovement.move();
                    renderDungeon= true;
                }
            }
            if(renderDungeon) dungeonRenderer.renderDungeon();
        }
    }

    private boolean checkGameLost(Player player){
        if(player.isDead()){
            dungeonRenderer.renderGameLost();
            gameOver=true;
            return true;
        }
        return false;
    }


    private void checkForWin(){
        if(monsterStore.getMonsters().isEmpty()){
            dungeonRenderer.renderWin();
            gameOver=true;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void startMonsterMovementLoop(){
        Runnable monsterMovementLoop = () -> {
            while (!isGameOver()) {
                moveMonsters();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
    }

    public DungeonRenderer getDungeonRenderer() {
        return dungeonRenderer;
    }
}
