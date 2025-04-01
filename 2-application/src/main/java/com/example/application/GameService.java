package com.example.application;

import com.example.application.map.FovCache;
import com.example.application.map.FovCalculator;
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
    private FovCalculator fovCalculator;
    private Player player;
    private Dungeon dungeon;
    private MonsterStore monsterStore;
    private ItemStore itemStore;
    private DungeonRenderer dungeonRenderer;
    private boolean gameOver;
    public GameService(Player player, Dungeon dungeon, MonsterStore monsterStore,ItemStore itemStore, DungeonRenderer dungeonRenderer, FovCache fovCache) {
        this.player = player;
        this.dungeon = dungeon;
        this.monsterStore = monsterStore;
        this.itemStore = itemStore;
        this.dungeonRenderer = dungeonRenderer;
        gameOver=false;
        this.fovCalculator = new FovCalculator(dungeon, fovCache);

        Position spawnPoint = dungeon.getPlayerSpawnPoint();
        this.fovCalculator.calculateFov(spawnPoint.getxPos(), spawnPoint.getyPos(), 5);
    }

    public void movePlayer(Direction direction){
        List<Monster> monstersInCurrentRoom;
        List<Item> itemsInCurrentRoom;
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
                dungeonRenderer.renderNotification(player.getName() + " attacks " + monster.getName() + "! " + monster.getName() + " took " + player.getAttack() + " damage.");
                if(monster.isDead()){
                    monsterStore.remove(monster.getId());
                    dungeonRenderer.renderNotification(player.getName() + " killed " + monster.getName() + "!");
                }
                checkForWin();
                return;
            }
        }
        playerMovement.moveInDirection(direction);
        fovCalculator.calculateFov(player.getPosition().getxPos(), player.getPosition().getyPos(), 5);

        dungeonRenderer.renderDungeon();

    }

    public void pickUpItem(){
        List<Item> itemsInCurrentRoom;
        if(player.getRoomNumber() != -1){
            itemsInCurrentRoom = itemStore.findByRoomNumber(player.getRoomNumber());
        }else{
            itemsInCurrentRoom = new ArrayList<>();
        }
        for(Item item : itemsInCurrentRoom){
            if(item.getPosition().isAdjacent(player.getPosition())){
                if(item instanceof Weapon weaponNew){
                    if(player.getEquippedWeapon() != null){
                        Weapon weapon = player.unEquipWeapon();
                        weapon.setPosition(item.getPosition());
                        weapon.setRoomNumber(item.getRoomNumber());
                        itemStore.add(weapon);
                        dungeonRenderer.renderNotification("Player switched weapon! " + weaponNew.getName() + " adds " + weaponNew.getAttack() + " attack damage.");
                    }else{
                        dungeonRenderer.renderNotification("Player picked up a weapon! " + weaponNew.getName() + " adds " + weaponNew.getAttack() + " attack damage.");
                    }
                    player.equipWeapon(weaponNew);
                    itemStore.remove(weaponNew);
                }else if(item instanceof Consumables consumables){
                    player.heal(consumables.getHealthPoints());
                    itemStore.remove(item);
                    dungeonRenderer.renderNotification("Player used a consumable! " + consumables.getName() + " heals " + consumables.getHealthPoints() + " health.");
                }
                return;
            }
        }
        dungeonRenderer.renderNotification("No item to pick up");
    }

    public void moveMonsters(){
        if(player.getRoomNumber()  != -1){
            boolean renderDungeon = false;
            for (Monster monster : monsterStore.findByRoomNumber(player.getRoomNumber())){
                if(monster.getPosition().isAdjacent(player.getPosition())){
                    monster.attack(player);
                    dungeonRenderer.renderNotification(monster.getName() + " attacks " + player.getName() + "! " + player.getName() + " took " + monster.getAttack() + " damage.");
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



    public DungeonRenderer getDungeonRenderer() {
        return dungeonRenderer;
    }

    public static void startMonsterMovementLoop(GameService gameService){
        Runnable monsterMovementLoop = () -> {
            while (!gameService.isGameOver()) {
                gameService.moveMonsters();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        new Thread(monsterMovementLoop).start();
    }
}
