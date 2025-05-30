package com.example.application.stores;

import com.example.domain.item.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemStore {
    private List<Item> items;

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
        if(item != null && !items.contains(item)) {
            items.add(item);
        }
    }
}