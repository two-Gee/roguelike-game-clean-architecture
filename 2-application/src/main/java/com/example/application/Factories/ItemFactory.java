package com.example.application.factories;

import com.example.domain.item.*;
import com.example.domain.Position;
import com.example.domain.map.DungeonRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemFactory {
    public static Item createItem(ItemTypes type, Position position, int roomNumber) {
        return switch (type) {
            case POTION -> new Potion(position, roomNumber);
            case BREAD -> new Bread(position, roomNumber);
            case SWORD -> new Sword(position, roomNumber);
            case AXE -> new Axe(position, roomNumber);
            default -> throw new IllegalArgumentException("Invalid item type: " + type);
        };
    }

    public static Item createRandomItem(DungeonRoom room, List<Item> existingItems) {
        Position position;
        boolean positionOccupied;

        do {
            int x = (int) (Math.random() * (room.getBottomRightCorner().getxPos() - room.getTopLeftCorner().getxPos()) + room.getTopLeftCorner().getxPos());
            int y = (int) (Math.random() * (room.getBottomRightCorner().getyPos() - room.getTopLeftCorner().getyPos()) + room.getTopLeftCorner().getyPos());
            position = new Position(x, y);
            final Position finalPosition = position;
            positionOccupied = existingItems.stream().anyMatch(item -> item.getPosition().equals(finalPosition));
        } while (positionOccupied);

        int random = (int) (Math.random() * ItemTypes.values().length);
        return createItem(ItemTypes.values()[random], position, room.getRoomNumber());
    }

    public static List<Item> createItemsForRoom(int amount, DungeonRoom room) {
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            items.add(createRandomItem(room, items));
        }
        return items;
    }

    public static List<Item> createItems(int maxRoomItems, List<DungeonRoom> dungeonRooms) {
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