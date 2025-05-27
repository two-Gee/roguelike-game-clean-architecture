package com.example.domain;

import com.example.domain.map.DungeonConfiguration;
import com.example.domain.map.DungeonRoom;
import com.example.domain.map.DungeonTile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DungeonTest {
    
    private DungeonTile[][] tiles;
    private DungeonTile walkableTile;
    private DungeonTile unwalkableTile;
    
    private Map<Integer, DungeonRoom> rooms;
    private Dungeon dungeon;
    private Position playerSpawnPoint;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        // Statt Arrays zu mocken, erstellen wir echte Arrays
        tiles = new DungeonTile[10][10]; // Größeres Array für alle Testpositionen
        
        // Mock einzelne Tiles
        walkableTile = mock(DungeonTile.class);
        unwalkableTile = mock(DungeonTile.class);
        when(walkableTile.isWalkable()).thenReturn(true);
        when(unwalkableTile.isWalkable()).thenReturn(false);
        
        // Testpositionen im Array setzen
        tiles[1][1] = walkableTile;    // Position(1,1) ist begehbar
        tiles[2][2] = unwalkableTile;  // Position(2,2) ist nicht begehbar
        
        // Create rooms
        rooms = new HashMap<>();
        DungeonRoom room1 = mock(DungeonRoom.class);
        DungeonRoom room2 = mock(DungeonRoom.class);
        when(room1.getRoomNumber()).thenReturn(1);
        when(room2.getRoomNumber()).thenReturn(2);
        rooms.put(1, room1);
        rooms.put(2, room2);
        
        // Setup player spawn point
        playerSpawnPoint = new Position(5, 5);
        
        // Create dungeon with real tiles and mocked rooms
        dungeon = new Dungeon(tiles, 5, 3, rooms, playerSpawnPoint);
    }
    
    @Test
    public void testGetPlayerSpawnPoint() {
        assertEquals(playerSpawnPoint, dungeon.getPlayerSpawnPoint());
    }
    
    @Test
    public void testGetWidth() {
        assertEquals(5, dungeon.getWidth());
    }
    
    @Test
    public void testGetHeight() {
        assertEquals(3, dungeon.getHeight());
    }
    
    @Test
    public void testGetDungeonRooms() {
        assertEquals(rooms, dungeon.getDungeonRooms());
    }
    
    @Test
    public void testIsWalkable() {
        Position walkablePosition = new Position(1, 1);
        Position unwalkablePosition = new Position(2, 2);
        
        assertTrue(dungeon.isWalkable(walkablePosition));
        assertFalse(dungeon.isWalkable(unwalkablePosition));
    }
    
    @Test
    public void testGetTile() {
        Position position = new Position(1, 1);
        assertEquals(walkableTile, dungeon.getTile(position));
    }
    
    @Test
    public void testGetRoomForPosition() {
        Position posInRoom1 = new Position(1, 1);
        Position posInRoom2 = new Position(2, 2);
        Position posOutsideRooms = new Position(3, 3);
        
        // Setup room behavior
        when(rooms.get(1).containsPosition(posInRoom1)).thenReturn(true);
        when(rooms.get(1).containsPosition(posInRoom2)).thenReturn(false);
        when(rooms.get(1).containsPosition(posOutsideRooms)).thenReturn(false);
        
        when(rooms.get(2).containsPosition(posInRoom1)).thenReturn(false);
        when(rooms.get(2).containsPosition(posInRoom2)).thenReturn(true);
        when(rooms.get(2).containsPosition(posOutsideRooms)).thenReturn(false);
        
        assertEquals(rooms.get(1), dungeon.getRoomForPosition(posInRoom1));
        assertEquals(rooms.get(2), dungeon.getRoomForPosition(posInRoom2));
        assertNull(dungeon.getRoomForPosition(posOutsideRooms));
    }
}
