package com.example.domain.map;

import com.example.domain.Position;

public class DungeonRoom {
    private int roomNumber;
    private Position topLeftCorner;
    private Position bottomRightCorner;

    public DungeonRoom(int x, int y, int width, int height, int roomNumber) {
        this.roomNumber = roomNumber;
        topLeftCorner = new Position(x, y);
        bottomRightCorner = new Position(x + width, y + height);
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public Position getRoomCenter() {
        return new Position((topLeftCorner.getxPos() + bottomRightCorner.getxPos())/2, (topLeftCorner.getyPos() + bottomRightCorner.getyPos())/2);
    }

    public Position getTopLeftCorner() {
        return topLeftCorner;
    }

    public Position getBottomRightCorner() {
        return bottomRightCorner;
    }

    // Returns true if room intersects another room
    public boolean intersectsOtherRoom(DungeonRoom other) {
        return topLeftCorner.getxPos() <= other.bottomRightCorner.getxPos() + 1 &&
                bottomRightCorner.getxPos() >= other.topLeftCorner.getxPos() - 1 &&
                topLeftCorner.getyPos() <= other.bottomRightCorner.getyPos() + 1 &&
                bottomRightCorner.getyPos() >= other.topLeftCorner.getyPos() - 1;
    }

    public boolean containsPosition(Position position){
        return position.getxPos() >= topLeftCorner.getxPos() && position.getxPos() <= bottomRightCorner.getxPos() &&
                position.getyPos() >= topLeftCorner.getyPos() && position.getyPos() <= bottomRightCorner.getyPos();
    }

}
