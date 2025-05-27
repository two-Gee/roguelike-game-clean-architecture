package com.example.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class DirectionTest {
    
    @Test
    public void testEnumValues() {
        // Verify that the enum has exactly the expected values
        Direction[] directions = Direction.values();
        assertEquals(4, directions.length);
        
        // Verify each enum value
        assertEquals(Direction.NORTH, directions[0]);
        assertEquals(Direction.SOUTH, directions[1]);
        assertEquals(Direction.EAST, directions[2]);
        assertEquals(Direction.WEST, directions[3]);
    }
    
    @Test
    public void testValueOf() {
        // Verify that valueOf returns the correct enum value
        assertEquals(Direction.NORTH, Direction.valueOf("NORTH"));
        assertEquals(Direction.SOUTH, Direction.valueOf("SOUTH"));
        assertEquals(Direction.EAST, Direction.valueOf("EAST"));
        assertEquals(Direction.WEST, Direction.valueOf("WEST"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidValueOf() {
        // Should throw IllegalArgumentException for invalid direction
        Direction.valueOf("INVALID_DIRECTION");
    }
}
