package com.example.application;

import com.example.application.stores.ItemStore;
import com.example.domain.Player;
import com.example.domain.item.Consumables;
import com.example.domain.item.Item;
import com.example.domain.item.Weapon;

import java.util.ArrayList;
import java.util.List;

public class ItemInteractionService {
    private List<Item> itemsInCurrentRoom;
    private DungeonRenderer dungeonRenderer;
    private ItemStore itemStore;
    private Player player;

    public ItemInteractionService(DungeonRenderer dungeonRenderer, ItemStore itemStore, Player player) {
        this.dungeonRenderer = dungeonRenderer;
        this.itemStore = itemStore;
        this.player = player;
        this.itemsInCurrentRoom = new ArrayList<>();
    }

    public void pickUpItem(){
        itemsInCurrentRoom = getItemsInCurrentRoom();

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

    private List<Item> getItemsInCurrentRoom() {
        int roomNumber = player.getRoomNumber();

        if (roomNumber != -1) {
            return itemStore.findByRoomNumber(roomNumber);
        } else {
            return new ArrayList<>();
        }
    }


}
