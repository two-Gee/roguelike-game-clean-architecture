package com.example.domain;


import com.example.domain.map.DungeonRoom;
import com.example.domain.map.DungeonTile;

import java.util.Map;

public class Dungeon {
//    private int level;
//    private int difficulty;
    private int itemAmount;
    private DungeonTile[][] dungeonTiles;
    private int width;
    private int height;
    private Map<Integer, DungeonRoom> dungeonRooms;
    private Position playerSpawnPoint;

// TODO add items, level and difficulty
    public Dungeon(DungeonTile[][] dungeonTiles, int width, int height, Map<Integer, DungeonRoom> dungeonRooms, Position playerSpawnPoint) {
//        this.level = level;
//        this.difficulty = difficulty;
//        this.itemAmount = itemAmount;
        this.dungeonTiles = dungeonTiles;
        this.width = width;
        this.height = height;
        this.dungeonRooms = dungeonRooms;
        this.playerSpawnPoint = playerSpawnPoint;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getPlayerSpawnPoint() {
        return playerSpawnPoint;
    }

    public DungeonRoom getRoomForPosition(Position position){
        for(DungeonRoom room : dungeonRooms.values()){
            if(room.containsPosition(position)){
                return room;
            }
        }
        return null;
    }

    public boolean isWalkable(Position position){
        return getTile(position).isWalkable();
    }

    public DungeonTile getTile(Position position){
        return dungeonTiles[position.getyPos()][position.getxPos()];
    }

    public Map<Integer, DungeonRoom> getDungeonRooms() {
        return dungeonRooms;
    }

    public DungeonRoom getRoomByRoomNumber(int roomNumber) {
        return dungeonRooms.get(roomNumber);
    }

    public Position getRandomPositionInRoom(int roomNumber){
        DungeonRoom room = dungeonRooms.get(roomNumber);
        int x = (int) (Math.random() * (room.getBottomRightCorner().getxPos() - room.getTopLeftCorner().getxPos()) + room.getTopLeftCorner().getxPos());
        int y = (int) (Math.random() * (room.getBottomRightCorner().getyPos() - room.getTopLeftCorner().getyPos()) + room.getTopLeftCorner().getyPos());
        return new Position(x, y);
    }
}
