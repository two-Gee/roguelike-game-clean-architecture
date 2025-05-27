package com.example.domain;
import org.junit.Test;
import static org.junit.Assert.*;

public class PositionTest {
    
    @Test
    public void testConstructor() {
        Position position = new Position(5, 10);
        assertEquals(5, position.getX_POS());
        assertEquals(10, position.getY_POS());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeX() {
        new Position(-1, 10);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeY() {
        new Position(5, -1);
    }

    @Test
    public void testEqualsWithSameValues() {
        Position pos1 = new Position(5, 10);
        Position pos2 = new Position(5, 10);
        assertTrue(pos1.equals(pos2));
    }

    @Test
    public void testEqualsWithDifferentValues() {
        Position pos1 = new Position(5, 10);
        Position pos3 = new Position(6, 10);
        assertFalse(pos1.equals(pos3));
    }

    @Test
    public void testEqualsWithNull() {
        Position pos1 = new Position(5, 10);
        assertFalse(pos1.equals(null));
    }

    @Test
    public void testEqualsWithDifferentType() {
        Position pos1 = new Position(5, 10);
        assertFalse(pos1.equals("Not a position"));
    }
    
    @Test
    public void testHashCode() {
        Position pos1 = new Position(5, 10);
        Position pos2 = new Position(5, 10);
        
        assertEquals(pos1.hashCode(), pos2.hashCode());
    }
    
    @Test
    public void testIsAdjacent() {
        Position center = new Position(5, 5);
        
        // Orthogonally adjacent positions
        Position north = new Position(5, 4);
        Position south = new Position(5, 6);
        Position east = new Position(6, 5);
        Position west = new Position(4, 5);
        
        // Diagonally adjacent positions (should not be considered adjacent)
        Position northeast = new Position(6, 4);
        Position northwest = new Position(4, 4);
        Position southeast = new Position(6, 6);
        Position southwest = new Position(4, 6);
        
        // Non-adjacent positions
        Position far = new Position(10, 10);
        
        // Test orthogonal adjacency
        assertTrue(center.isAdjacent(north));
        assertTrue(center.isAdjacent(south));
        assertTrue(center.isAdjacent(east));
        assertTrue(center.isAdjacent(west));
        
        // Test diagonal adjacency (should be false)
        assertFalse(center.isAdjacent(northeast));
        assertFalse(center.isAdjacent(northwest));
        assertFalse(center.isAdjacent(southeast));
        assertFalse(center.isAdjacent(southwest));
        
        // Test non-adjacency
        assertFalse(center.isAdjacent(far));
    }
    
    @Test
    public void testGetAdjacentPosition() {
        Position position = new Position(5, 5);
        
        assertEquals(new Position(5, 4), position.getAdjacentPosition(Direction.NORTH));
        assertEquals(new Position(5, 6), position.getAdjacentPosition(Direction.SOUTH));
        assertEquals(new Position(6, 5), position.getAdjacentPosition(Direction.EAST));
        assertEquals(new Position(4, 5), position.getAdjacentPosition(Direction.WEST));
    }

}