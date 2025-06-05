package com.example.application.map;

import com.example.domain.Dungeon;
import com.example.domain.Position;
import com.example.domain.map.DungeonConfiguration;
import com.example.domain.map.DungeonRoom;
import com.example.domain.map.DungeonTile;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DungeonGeneratorTest {

    private DungeonConfiguration smallConfig;
    private DungeonConfiguration mediumConfig;

    @Before
    public void setUp() {
        smallConfig = new DungeonConfiguration(20, 20, 5, 3, 2, 3, 5, 5);
        mediumConfig = new DungeonConfiguration(100, 100, 10, 5, 5, 10, 6, 6);
    }

    @Test
    public void testDungeonGeneratorInitialization() {
        DungeonGenerator generator = new DungeonGenerator(smallConfig);
        try {
            Field dungeonTilesField = DungeonGenerator.class.getDeclaredField("dungeonTiles");
            dungeonTilesField.setAccessible(true);
            DungeonTile[][] dungeonTiles = (DungeonTile[][]) dungeonTilesField.get(generator);

            assertNotNull(dungeonTiles);
            assertEquals(smallConfig.getHeight(), dungeonTiles.length);
            assertEquals(smallConfig.getWidth(), dungeonTiles[0].length);

            for (int i = 0; i < smallConfig.getHeight(); i++) {
                for (int j = 0; j < smallConfig.getWidth(); j++) {
                    assertEquals(DungeonTile.Wall, dungeonTiles[i][j]);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Reflection error: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateDungeonReturnsDungeon() {
        Dungeon dungeon = DungeonGenerator.generateDungeon(mediumConfig);
        assertNotNull(dungeon);
        assertEquals(mediumConfig.getWidth(), dungeon.getWidth());
        assertEquals(mediumConfig.getHeight(), dungeon.getHeight());
        assertNotNull(dungeon.getDungeonTiles());
        assertNotNull(dungeon.getDungeonRooms());
        assertNotNull(dungeon.getPlayerSpawnPoint());
    }

    @Test
    public void testFirstRoomSpawnPoint() {
        Dungeon dungeon = DungeonGenerator.generateDungeon(smallConfig);
        Position playerSpawnPoint = dungeon.getPlayerSpawnPoint();
        assertNotNull(playerSpawnPoint);

        try {
            Field dungeonRoomsField = DungeonGenerator.class.getDeclaredField("dungeonRooms");
            dungeonRoomsField.setAccessible(true);

            boolean spawnInRoom = false;
            for (DungeonRoom room : dungeon.getDungeonRooms().values()) {
                if (playerSpawnPoint.getX_POS() >= room.getTopLeftCorner().getX_POS() &&
                        playerSpawnPoint.getX_POS() <= room.getBottomRightCorner().getX_POS() &&
                        playerSpawnPoint.getY_POS() >= room.getTopLeftCorner().getY_POS() &&
                        playerSpawnPoint.getY_POS() <= room.getBottomRightCorner().getY_POS()) {
                    spawnInRoom = true;
                    break;
                }
            }
            assertTrue(spawnInRoom);

        } catch (NoSuchFieldException e) {
            fail("Reflection error: " + e.getMessage());
        }
    }

    @Test
    public void testRoomsDoNotIntersectInitially() {
        Dungeon dungeon = DungeonGenerator.generateDungeon(mediumConfig);

        Map<Integer, DungeonRoom> rooms = dungeon.getDungeonRooms();
        for (DungeonRoom room1 : rooms.values()) {
            for (DungeonRoom room2 : rooms.values()) {
                if (room1 != room2) {
                    assertFalse(room1.intersectsOtherRoom(room2));
                }
            }
        }
    }

    @Test
    public void testGenerateRoomCarvesFloor() {
        DungeonGenerator generator = new DungeonGenerator(smallConfig);
        DungeonRoom room = new DungeonRoom(2, 2, 3, 3, 0);

        try {
            Field dungeonTilesField = DungeonGenerator.class.getDeclaredField("dungeonTiles");
            dungeonTilesField.setAccessible(true);
            DungeonTile[][] dungeonTiles = (DungeonTile[][]) dungeonTilesField.get(generator);

            java.lang.reflect.Method generateRoomMethod = DungeonGenerator.class.getDeclaredMethod("generateRoom", DungeonRoom.class, Map.class);
            generateRoomMethod.setAccessible(true);
            generateRoomMethod.invoke(generator, room, new HashMap<>());

            for (int y = room.getTopLeftCorner().getY_POS(); y <= room.getBottomRightCorner().getY_POS(); y++) {
                for (int x = room.getTopLeftCorner().getX_POS(); x <= room.getBottomRightCorner().getX_POS(); x++) {
                    assertEquals(DungeonTile.Floor, dungeonTiles[y][x]);
                }
            }
        } catch (Exception e) {
            fail("Reflection or invocation error: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateVTunnelCarvesFloor() {
        DungeonGenerator generator = new DungeonGenerator(smallConfig);
        try {
            Field dungeonTilesField = DungeonGenerator.class.getDeclaredField("dungeonTiles");
            dungeonTilesField.setAccessible(true);
            DungeonTile[][] dungeonTiles = (DungeonTile[][]) dungeonTilesField.get(generator);

            java.lang.reflect.Method generateVTunnelMethod = DungeonGenerator.class.getDeclaredMethod("generateVTunnel", int.class, int.class, int.class);
            generateVTunnelMethod.setAccessible(true);

            generateVTunnelMethod.invoke(generator, 2, 5, 3);

            for (int y = 2; y <= 5; y++) {
                assertEquals(DungeonTile.Floor, dungeonTiles[y][3]);
            }
        } catch (Exception e) {
            fail("Reflection or invocation error: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateHTunnelCarvesFloor() {
        DungeonGenerator generator = new DungeonGenerator(smallConfig);
        try {
            Field dungeonTilesField = DungeonGenerator.class.getDeclaredField("dungeonTiles");
            dungeonTilesField.setAccessible(true);
            DungeonTile[][] dungeonTiles = (DungeonTile[][]) dungeonTilesField.get(generator);

            java.lang.reflect.Method generateHTunnelMethod = DungeonGenerator.class.getDeclaredMethod("generateHTunnel", int.class, int.class, int.class);
            generateHTunnelMethod.setAccessible(true);

            generateHTunnelMethod.invoke(generator, 2, 5, 3);

            for (int x = 2; x <= 5; x++) {
                assertEquals(DungeonTile.Floor, dungeonTiles[3][x]);
            }
        } catch (Exception e) {
            fail("Reflection or invocation error: " + e.getMessage());
        }
    }

    @Test
    public void testConnectRoomsCreatesTunnels() {
        DungeonGenerator generator = new DungeonGenerator(smallConfig);
        DungeonRoom room1 = new DungeonRoom(2, 2, 3, 3, 0);
        DungeonRoom room2 = new DungeonRoom(7, 7, 3, 3, 1);

        try {
            Field dungeonTilesField = DungeonGenerator.class.getDeclaredField("dungeonTiles");
            dungeonTilesField.setAccessible(true);
            DungeonTile[][] dungeonTiles = (DungeonTile[][]) dungeonTilesField.get(generator);

            for (int y = room1.getTopLeftCorner().getY_POS(); y <= room1.getBottomRightCorner().getY_POS(); y++) {
                for (int x = room1.getTopLeftCorner().getX_POS(); x <= room1.getBottomRightCorner().getX_POS(); x++) {
                    dungeonTiles[y][x] = DungeonTile.Floor;
                }
            }
            for (int y = room2.getTopLeftCorner().getY_POS(); y <= room2.getBottomRightCorner().getY_POS(); y++) {
                for (int x = room2.getTopLeftCorner().getX_POS(); x <= room2.getBottomRightCorner().getX_POS(); x++) {
                    dungeonTiles[y][x] = DungeonTile.Floor;
                }
            }

            java.lang.reflect.Method connectRoomsMethod = DungeonGenerator.class.getDeclaredMethod("connectRooms", DungeonRoom.class, DungeonRoom.class);
            connectRoomsMethod.setAccessible(true);
            connectRoomsMethod.invoke(generator, room2, room1);

            int floorTiles = 0;
            for (int i = 0; i < smallConfig.getHeight(); i++) {
                for (int j = 0; j < smallConfig.getWidth(); j++) {
                    if (dungeonTiles[i][j] == DungeonTile.Floor) {
                        floorTiles++;
                    }
                }
            }
            assertTrue(floorTiles > (room1.getWidth() * room1.getHeight()) + (room2.getWidth() * room2.getHeight()));


        } catch (Exception e) {
            fail("Reflection or invocation error: " + e.getMessage());
        }
    }

    @Test
    public void testDungeonIntegrityAfterGeneration() {
        Dungeon dungeon = DungeonGenerator.generateDungeon(mediumConfig);
        DungeonTile[][] tiles = dungeon.getDungeonTiles();

        for (DungeonRoom room : dungeon.getDungeonRooms().values()) {
            for (int y = room.getTopLeftCorner().getY_POS(); y <= room.getBottomRightCorner().getY_POS(); y++) {
                for (int x = room.getTopLeftCorner().getX_POS(); x <= room.getBottomRightCorner().getX_POS(); x++) {
                    assertEquals(DungeonTile.Floor, tiles[y][x]);
                }
            }
        }

        int floorCount = 0;
        for (int i = 0; i < mediumConfig.getHeight(); i++) {
            for (int j = 0; j < mediumConfig.getWidth(); j++) {
                if (tiles[i][j] == DungeonTile.Floor) {
                    floorCount++;
                }
            }
        }
        assertTrue(floorCount > 0);
    }
}