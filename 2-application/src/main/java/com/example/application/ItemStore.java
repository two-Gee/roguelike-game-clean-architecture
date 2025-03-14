package com.example.application;

import com.example.domain.Item.Consumables;
import com.example.domain.Item.Item;
import com.example.domain.Item.Weapon;

import java.util.List;
import java.util.stream.Collectors;

public class ItemStore {
    private static List<Item> items;

    public ItemStore(List<Item> items) {
        this.items = items;
    }

    public List<Item> findByRoomNumber(int roomNumber) {
        return items.stream()
                .filter(item -> item.getRoomNumber() == roomNumber)
                .collect(Collectors.toList());
    }

    public boolean remove(Item item) {
        return items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }
    public void add(Item item){
        items.add(item);
    }
}