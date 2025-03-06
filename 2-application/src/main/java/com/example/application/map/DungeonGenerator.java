package com.example.application.map;

import com.example.domain.Dungeon;
import com.example.domain.LivingEntity;
import com.example.domain.Position;
import com.example.domain.map.DungeonConfiguration;
import com.example.domain.map.DungeonRoom;
import com.example.domain.map.DungeonTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonGenerator {
    private DungeonTile[][] dungeonTiles;
    private DungeonConfiguration dungeonConfiguration;
    private Position playerSpawnPoint;
    private Random rand = new Random();

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

    public static Dungeon generateDungeon(DungeonConfiguration conf, List<LivingEntity> livingEntities) {
        DungeonGenerator generator = new DungeonGenerator(conf);

        generator.generateRooms();

        return new Dungeon(livingEntities.size() - 1, generator.dungeonTiles, conf.getWidth(), conf.getHeight(), livingEntities, generator.getPlayerSpawnPoint());

    }

    private void generateRooms() {
        List<DungeonRoom> dungeonRooms = new ArrayList<DungeonRoom>();

        int numRooms = rand.nextInt(dungeonConfiguration.getMaxRooms() - dungeonConfiguration.getMinRooms() + 1) + dungeonConfiguration.getMinRooms();
        int count = 0;

        // Generate rooms with random sizes and positions
        while (count < numRooms) {
            // Room size
            int width = rand.nextInt(dungeonConfiguration.getMaxRoomSize() - dungeonConfiguration.getMinRoomSize() + 1) + dungeonConfiguration.getMinRoomSize();
            int height = rand.nextInt(dungeonConfiguration.getMaxRoomSize() - dungeonConfiguration.getMinRoomSize() + 1) + dungeonConfiguration.getMinRoomSize();
            
            // Room location
            int x = rand.nextInt(dungeonConfiguration.getWidth() - width);
            int y = rand.nextInt(dungeonConfiguration.getHeight() - height);
            
            DungeonRoom room = new DungeonRoom(x, y, width, height);

            // Check if room is first room
            if(dungeonRooms.isEmpty()) {
                // Set room center of first room as player spawn 
                playerSpawnPoint = room.getRoomCenter();

                generateRoom(room, dungeonRooms, false);
                dungeonRooms.add(room);
                count++;
            }
            else {
                // Check if room intersects one of the previously generated rooms
                boolean intersects = false;
                for (DungeonRoom other : dungeonRooms) {
                    if(room.intersectsOtherRoom(other)) {
                        intersects = true;
                        break;
                    }
                }
                
                if(!intersects) {
                    generateRoom(room, dungeonRooms, true );
                    dungeonRooms.add(room);
                    count++;
                }
            }
        }
    }

    private void generateRoom(DungeonRoom room, List<DungeonRoom> dungeonRooms, boolean spawnMonsters) {
        // Carve room in the map
        for (int y = room.getTopLeftCorner().getyPos() + 1; y < room.getBottomRightCorner().getyPos(); y++) {
            for (int x = room.getTopLeftCorner().getxPos() + 1; x < room.getBottomRightCorner().getxPos(); x++) {
                this.dungeonTiles[y][x] = DungeonTile.Floor;
            }
        }

        if(spawnMonsters) {
            // TODO Monster spawning hinzufügen
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
            generateHTunnel(prevRoomCenter.getxPos(), roomCenter.getxPos(), prevRoomCenter.getyPos());
            generateVTunnel(prevRoomCenter.getyPos(), roomCenter.getyPos(), roomCenter.getxPos());
        } else {
            generateVTunnel(prevRoomCenter.getyPos(), roomCenter.getyPos(), prevRoomCenter.getxPos());
            generateHTunnel(prevRoomCenter.getxPos(), roomCenter.getxPos(), roomCenter.getyPos());
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
