package com.example.domain.map;

public final class DungeonConfiguration {
    private final int width;
    private final int height;
    private final int maxRooms;
    private final int minRooms;
    private final int minRoomSize;
    private final int maxRoomSize;
    private final int maxRoomMonsters;
    private final int maxRoomItems;

    public DungeonConfiguration(int width, int height, int maxRooms, int minRooms, int minRoomSize, int maxRoomSize, int maxRoomMonsters, int maxRoomItems) {
        this.width = width;
        this.height = height;
        this.maxRooms = maxRooms;
        this.minRooms = minRooms;
        this.minRoomSize = minRoomSize;
        this.maxRoomSize = maxRoomSize;
        this.maxRoomMonsters = maxRoomMonsters;
        this.maxRoomItems = maxRoomItems;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxRooms() {
        return maxRooms;
    }

    public int getMinRooms() {
        return minRooms;
    }

    public int getMinRoomSize() {
        return minRoomSize;
    }

    public int getMaxRoomSize() {
        return maxRoomSize;
    }

    public int getMaxRoomMonsters() {
        return maxRoomMonsters;
    }

    public int getMaxRoomItems() {
        return maxRoomItems;
    }
}
