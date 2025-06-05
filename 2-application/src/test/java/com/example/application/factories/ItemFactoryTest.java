package com.example.application.factories;

import com.example.domain.Position;
import com.example.domain.item.Item;
import com.example.domain.item.ItemTypes;
import com.example.domain.item.Potion;
import com.example.domain.map.DungeonRoom;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ItemFactoryTest {

    private final ItemFactory itemFactory = new ItemFactory();

    @Test
    public void testCreateItem() {
        Position position = new Position(2, 3);
        int roomNumber = 1;
        Item item = itemFactory.createItem(ItemTypes.POTION, position, roomNumber);

        assertNotNull(item);
        assertTrue(item instanceof Potion);
        assertEquals(position, item.getPosition());
        assertEquals(roomNumber, item.getRoomNumber());
    }

    @Test
    public void testCreateRandomItem() {
        DungeonRoom room = new DungeonRoom(0, 0, 5, 5, 1);
        Set<Position> occupiedPositions = Set.of(new Position(1, 1), new Position(2, 2));

        Item item = itemFactory.createRandomItem(room, occupiedPositions);

        assertNotNull(item);
        assertTrue(room.containsPosition(item.getPosition()));
        assertFalse(occupiedPositions.contains(item.getPosition()));
    }

    @Test
    public void testCreateItemsForRoom() {
        DungeonRoom room = new DungeonRoom(0, 0, 5, 5, 1);
        int amount = 3;

        List<Item> items = itemFactory.createItemsForRoom(amount, room);

        assertNotNull(items);
        assertEquals(amount, items.size());
        for (Item item : items) {
            assertTrue(room.containsPosition(item.getPosition()));
        }
    }

    @Test
    public void testCreateItems() {
        DungeonRoom room1 = new DungeonRoom(0, 0, 5, 5, 1);
        DungeonRoom room2 = new DungeonRoom(6, 6, 10, 10, 2);
        List<DungeonRoom> dungeonRooms = List.of(room1, room2);

        int maxRoomItems = 5;
        List<Item> items = itemFactory.createItems(maxRoomItems, dungeonRooms);

        assertNotNull(items);
        assertFalse(items.isEmpty());
        for (Item item : items) {
            assertTrue(dungeonRooms.stream().anyMatch(room -> room.containsPosition(item.getPosition())));
        }
    }
}