package com.example.application.map;

import com.example.domain.Dungeon;
import com.example.domain.Position;
import com.example.domain.map.DungeonConfiguration;
import com.example.domain.map.DungeonRoom;
import com.example.domain.map.DungeonTile;

import java.util.*;

public class DungeonGenerator {
    private DungeonTile[][] dungeonTiles;
    private DungeonConfiguration dungeonConfiguration;
    private Position playerSpawnPoint;
    private Random rand = new Random();
    private Map<Integer, DungeonRoom> dungeonRooms = new HashMap<>();

    public DungeonGenerator(DungeonConfiguration dungeonConfiguration) {
        this.dungeonConfiguration = dungeonConfiguration;

        this.dungeonTiles = new DungeonTile[dungeonConfiguration.getHeight()][dungeonConfiguration.getWidth()];

        // Instantiate the dungeon tiles as floor
        for(int i = 0; i < dungeonConfiguration.getHeight(); i++) {
            for(int j = 0; j < dungeonConfiguration.getWidth(); j++) {
            this.dungeonTiles[i][j] = DungeonTile.Wall;
            }
        }

    }

    public Position getPlayerSpawnPoint() {
        return playerSpawnPoint;
    }

    public static Dungeon generateDungeon(DungeonConfiguration conf) {
        DungeonGenerator generator = new DungeonGenerator(conf);

        generator.generateRooms();

        return new Dungeon(generator.dungeonTiles, conf.getWidth(), conf.getHeight(), generator.dungeonRooms, generator.getPlayerSpawnPoint());

    }

    private void generateRooms() {
        int numRooms = rand.nextInt(dungeonConfiguration.getMaxRooms() - dungeonConfiguration.getMinRooms() + 1) + dungeonConfiguration.getMinRooms();
        int count = 0;

        // Generate rooms with random sizes and positions
        while (count < numRooms) {
            // Room size
            int width = (count == 0) ? 4 : rand.nextInt(dungeonConfiguration.getMaxRoomSize() - dungeonConfiguration.getMinRoomSize() + 1) + dungeonConfiguration.getMinRoomSize();
            int height = (count == 0) ? 4 : rand.nextInt(dungeonConfiguration.getMaxRoomSize() - dungeonConfiguration.getMinRoomSize() + 1) + dungeonConfiguration.getMinRoomSize();
            // Room location

            int x = rand.nextInt(dungeonConfiguration.getWidth() - width - 2) + 1;
            int y = rand.nextInt(dungeonConfiguration.getHeight() - height - 2) + 1;
            
            DungeonRoom room = new DungeonRoom(x, y, width, height, count);

            // Check if room is first room
            if(dungeonRooms.isEmpty()) {
                // Set room center of first room as player spawn 
                playerSpawnPoint = room.getRoomCenter();

                generateRoom(room, dungeonRooms);
                dungeonRooms.put(count, room);
                count++;
            }
            else {
                // Check if room intersects one of the previously generated rooms
                boolean intersects = false;
                for (DungeonRoom other : dungeonRooms.values()) {
                    if(room.intersectsOtherRoom(other)) {
                        intersects = true;
                        break;
                    }
                }
                
                if(!intersects) {
                    generateRoom(room, dungeonRooms);
                    dungeonRooms.put(count, room);
                    count++;
                }
            }
        }
    }

    private void generateRoom(DungeonRoom room, Map<Integer, DungeonRoom> dungeonRooms) {
        // Carve room in the map
        for (int y = room.getTopLeftCorner().getY_POS(); y <= room.getBottomRightCorner().getY_POS(); y++) {
            for (int x = room.getTopLeftCorner().getX_POS(); x <= room.getBottomRightCorner().getX_POS(); x++) {
                this.dungeonTiles[y][x] = DungeonTile.Floor;
            }
        }

        // If this is not the first room, connect to the previous
        if(!dungeonRooms.isEmpty()) {
            DungeonRoom prevRoom = dungeonRooms.get(dungeonRooms.size() - 1);
            connectRooms(room, prevRoom);
        }

    }

    private void connectRooms(DungeonRoom room, DungeonRoom prevRoom) {
        Position roomCenter = room.getRoomCenter();
        Position prevRoomCenter = prevRoom.getRoomCenter();

        if(rand.nextInt(2) == 1) {
            generateHTunnel(prevRoomCenter.getX_POS(), roomCenter.getX_POS(), prevRoomCenter.getY_POS());
            generateVTunnel(prevRoomCenter.getY_POS(), roomCenter.getY_POS(), roomCenter.getX_POS());
        } else {
            generateVTunnel(prevRoomCenter.getY_POS(), roomCenter.getY_POS(), prevRoomCenter.getX_POS());
            generateHTunnel(prevRoomCenter.getX_POS(), roomCenter.getX_POS(), roomCenter.getY_POS());
        }
    }

    // Carve a vertical tunnel between two rooms
    private void generateVTunnel(int y1, int y2, int x1) {
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);

        for (int y = minY; y <= maxY; y++) {
            this.dungeonTiles[y][x1] = DungeonTile.Floor;
        }
    }

    // Carve a horizontal tunnel between two rooms
    private void generateHTunnel(int x1, int x2, int y1) {
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);

        for (int x = minX; x <= maxX; x++) {
            this.dungeonTiles[y1][x] = DungeonTile.Floor;
        }
    }
}
