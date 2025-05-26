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
        return new Position((topLeftCorner.getX_POS() + bottomRightCorner.getX_POS())/2, (topLeftCorner.getY_POS() + bottomRightCorner.getY_POS())/2);
    }

    public Position getTopLeftCorner() {
        return topLeftCorner;
    }

    public Position getBottomRightCorner() {
        return bottomRightCorner;
    }

    // Returns true if room intersects another room
    public boolean intersectsOtherRoom(DungeonRoom other) {
        return topLeftCorner.getX_POS() <= other.bottomRightCorner.getX_POS() + 1 &&
                bottomRightCorner.getX_POS() >= other.topLeftCorner.getX_POS() - 1 &&
                topLeftCorner.getY_POS() <= other.bottomRightCorner.getY_POS() + 1 &&
                bottomRightCorner.getY_POS() >= other.topLeftCorner.getY_POS() - 1;
    }

    public boolean containsPosition(Position position){
        return position.getX_POS() >= topLeftCorner.getX_POS() && position.getX_POS() <= bottomRightCorner.getX_POS() &&
                position.getY_POS() >= topLeftCorner.getY_POS() && position.getY_POS() <= bottomRightCorner.getY_POS();
    }

}
