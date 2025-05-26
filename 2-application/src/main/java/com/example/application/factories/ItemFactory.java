package com.example.application.factories;

import com.example.domain.item.*;
import com.example.domain.Position;
import com.example.domain.map.DungeonRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemFactory {

    public Item createItem(ItemTypes type, Position position, int roomNumber) {
        return switch (type) {
            case POTION -> new Potion(position, roomNumber);
            case BREAD -> new Bread(position, roomNumber);
            case SWORD -> new Sword(position, roomNumber);
            case AXE -> new Axe(position, roomNumber);
            default -> throw new IllegalArgumentException("Invalid item type: " + type);
        };
    }

    public Item createRandomItem(DungeonRoom room, Set<Position> occupiedPositions) {
        Position position = PositionGenerator.generateRandomPosition(room, occupiedPositions);

        int random = (int) (Math.random() * ItemTypes.values().length);
        return createItem(ItemTypes.values()[random], position, room.getRoomNumber());
    }

    public List<Item> createItemsForRoom(int amount, DungeonRoom room) {
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Set<Position> occupiedPositions = items.stream().map(Item::getPosition).collect(Collectors.toSet());
            items.add(createRandomItem(room, occupiedPositions));
        }
        return items;
    }

    public List<Item> createItems(int maxRoomItems, List<DungeonRoom> dungeonRooms) {
        List<Item> items = new ArrayList<>();
        Random random = new Random();

        for (DungeonRoom room : dungeonRooms) {
            if (room.getRoomNumber() == 0) {
                continue;
            }
            int amount = random.nextInt(maxRoomItems) + 1;
            items.addAll(createItemsForRoom(amount, room));
        }
        return items;
    }
}