package com.example.application.stores;

import com.example.domain.Position;
import com.example.domain.item.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ItemStoreTest {

    private List<Item> itemsList;
    private ItemStore itemStore;

    private Item item1;
    private Item item2;
    private Item item3;
    private Item item4;

    @Before
    public void setUp() {
        itemsList = new ArrayList<>();

        item1 = mock(Item.class, withSettings()
                .useConstructor("Sword", new Position(0, 0), 1)
                .defaultAnswer(CALLS_REAL_METHODS));
        when(item1.getRoomNumber()).thenReturn(1);

        item2 = mock(Item.class, withSettings()
                .useConstructor("Axe", new Position(1, 1), 2)
                .defaultAnswer(CALLS_REAL_METHODS));
        when(item2.getRoomNumber()).thenReturn(2);

        item3 = mock(Item.class, withSettings()
                .useConstructor("Potion", new Position(2, 2), 1)
                .defaultAnswer(CALLS_REAL_METHODS));
        when(item3.getRoomNumber()).thenReturn(1);

        item4 = mock(Item.class, withSettings()
                .useConstructor("Bread", new Position(3, 3), 3)
                .defaultAnswer(CALLS_REAL_METHODS));
        when(item4.getRoomNumber()).thenReturn(3);

        itemsList.add(item1);
        itemsList.add(item2);
        itemsList.add(item3);
        itemsList.add(item4);

        itemStore = new ItemStore(itemsList);
    }

    @Test
    public void testFindByRoomNumber_ExistingRoom() {
        List<Item> foundItems = itemStore.findByRoomNumber(1);
        assertNotNull("List of items should not be null", foundItems);
        assertEquals("Should find 2 items in room 1", 2, foundItems.size());
        assertTrue("List should contain item1", foundItems.contains(item1));
        assertTrue("List should contain item3", foundItems.contains(item3));
        assertFalse("List should not contain item2", foundItems.contains(item2));
    }

    @Test
    public void testFindByRoomNumber_NonExistingRoom() {
        List<Item> foundItems = itemStore.findByRoomNumber(999);
        assertNotNull("List of items should not be null", foundItems);
        assertTrue("Should find no items in a non-existing room", foundItems.isEmpty());
    }

    @Test
    public void testFindByRoomNumber_EmptyStore() {
        itemStore = new ItemStore(new ArrayList<Item>());
        List<Item> foundItems = itemStore.findByRoomNumber(1);
        assertNotNull("List of items should not be null", foundItems);
        assertTrue("Should find no items in an empty store", foundItems.isEmpty());
    }

    @Test
    public void testFindByRoomNumber_NegativeRoomNumber() {
        List<Item> foundItems = itemStore.findByRoomNumber(-5);
        assertNotNull("List of items should not be null", foundItems);
        assertTrue("Should return an empty list for negative room numbers", foundItems.isEmpty());
    }

    @Test
    public void testRemove_ExistingItem() {
        assertTrue("Should return true when removing an existing item", itemStore.remove(item1));
        assertFalse("Item should be removed from the list", itemsList.contains(item1));
        assertEquals("List size should decrease by 1", 3, itemsList.size());
    }

    @Test
    public void testRemove_NonExistingItem() {
        Item nonExistingItem = mock(Item.class);
        when(nonExistingItem.getRoomNumber()).thenReturn(-5);

        assertFalse("Should return false when removing a non-existing item", itemStore.remove(nonExistingItem));
        assertEquals("List size should remain unchanged", 4, itemsList.size());
    }

    @Test
    public void testRemove_NullItem() {
        assertFalse("Should return false when attempting to remove null", itemStore.remove(null));
        assertEquals("List size should remain unchanged", 4, itemsList.size());
    }

    @Test
    public void testRemove_EmptyStore() {
        itemStore = new ItemStore(new ArrayList<Item>());
        assertFalse("Should return false when removing from an empty store", itemStore.remove(item1));
        assertTrue("List should remain empty", itemStore.getItems().isEmpty());
    }

    @Test
    public void testGetItems_PopulatedStore() {
        List<Item> allItems = itemStore.getItems();
        assertNotNull("List of items should not be null", allItems);
        assertEquals("Should return all 4 items", 4, allItems.size());
        assertTrue("List should contain item1", allItems.contains(item1));
        assertTrue("List should contain item2", allItems.contains(item2));
        assertTrue("List should contain item3", allItems.contains(item3));
        assertTrue("List should contain item4", allItems.contains(item4));
    }

    @Test
    public void testGetItems_EmptyStore() {
        itemStore = new ItemStore(new ArrayList<Item>());
        List<Item> allItems = itemStore.getItems();
        assertNotNull("List of items should not be null", allItems);
        assertTrue("Should return an empty list for an empty store", allItems.isEmpty());
    }

    @Test
    public void testAdd_NewItem() {
        Item newItem = mock(Item.class);
        when(newItem.getRoomNumber()).thenReturn(4);

        itemStore.add(newItem);
        assertEquals("List should contain 5 items", 5, itemsList.size());
        assertTrue("List should contain the new item", itemsList.contains(newItem));
    }

    @Test
    public void testAdd_DuplicateItem() {
        itemStore.add(item1);
        assertEquals("List should contain 4 items (duplicate not added)", 4, itemsList.size());
    }

    @Test
    public void testAdd_NullItem() {
        itemStore.add(null);
        assertEquals("List should contain 4 items (null not added)", 4, itemsList.size());
        assertFalse("List should not contain null", itemsList.contains(null));
    }
}