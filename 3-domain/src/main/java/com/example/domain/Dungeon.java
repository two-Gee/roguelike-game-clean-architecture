package com.example.domain;


import com.example.domain.map.DungeonRoom;
import com.example.domain.map.DungeonTile;

import java.util.Map;

public class Dungeon {
    private DungeonTile[][] dungeonTiles;
    private int width;
    private int height;
    private Map<Integer, DungeonRoom> dungeonRooms;
    private Position playerSpawnPoint;

    public Dungeon(DungeonTile[][] dungeonTiles, int width, int height, Map<Integer, DungeonRoom> dungeonRooms, Position playerSpawnPoint) {
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

    public DungeonTile[][] getDungeonTiles() {
        return dungeonTiles;
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
        return dungeonTiles[position.getY_POS()][position.getX_POS()];
    }

    public Map<Integer, DungeonRoom> getDungeonRooms() {
        return dungeonRooms;
    }
}
