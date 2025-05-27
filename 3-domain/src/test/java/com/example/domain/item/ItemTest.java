package com.example.domain.item;

import com.example.domain.Position;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {
    
    private TestItem item;
    private Position position;
    
    // Concrete implementation of the abstract Item class for testing
    private static class TestItem extends Item {
        public TestItem(String name, Position position, int roomNumber) {
            super(name, position, roomNumber);
        }
    }
    
    @Before
    public void setUp() {
        position = new Position(5, 5);
        item = new TestItem("TestItem", position, 1);
    }
    
    @Test
    public void testInitialization() {
        assertEquals(1, item.getRoomNumber());
        assertEquals(position, item.getPosition());
        assertEquals("TestItem", item.getName());
        assertFalse(item.isPickedUp());
    }
    
    @Test
    public void testSetRoomNumber() {
        item.setRoomNumber(2);
        assertEquals(2, item.getRoomNumber());
    }
    
    @Test
    public void testSetPosition() {
        Position newPosition = new Position(6, 6);
        item.setPosition(newPosition);
        assertEquals(newPosition, item.getPosition());
    }
    
    @Test
    public void testSetPickedUp() {
        assertFalse(item.isPickedUp());
        
        item.setPickedUp(true);
        assertTrue(item.isPickedUp());
        
        item.setPickedUp(false);
        assertFalse(item.isPickedUp());
    }

}
