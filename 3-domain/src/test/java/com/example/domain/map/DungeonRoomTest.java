package com.example.domain.map;

import com.example.domain.Position;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DungeonRoomTest {
    
    private DungeonRoom room;
    
    @Before
    public void setUp() {
        // Create a room at position (10, 10) with width 5 and height 5
        room = new DungeonRoom(10, 10, 5, 5, 1);
    }
    
    @Test
    public void testRoomCreation() {
        assertEquals(1, room.getRoomNumber());
        assertEquals(new Position(10, 10), room.getTopLeftCorner());
        assertEquals(new Position(15, 15), room.getBottomRightCorner());
    }
    
    @Test
    public void testPositionInRoom() {
        // Test positions inside the room
        assertTrue(room.containsPosition(new Position(10, 10))); // Top left corner
        assertTrue(room.containsPosition(new Position(15, 15))); // Bottom right corner
        assertTrue(room.containsPosition(new Position(12, 12))); // Middle of the room
        
        // Test positions outside the room
        assertFalse(room.containsPosition(new Position(9, 10))); // Left of the room
        assertFalse(room.containsPosition(new Position(10, 9))); // Above the room
        assertFalse(room.containsPosition(new Position(16, 15))); // Right of the room
        assertFalse(room.containsPosition(new Position(15, 16))); // Below the room
        assertFalse(room.containsPosition(new Position(20, 20))); // Far from the room
    }

}
