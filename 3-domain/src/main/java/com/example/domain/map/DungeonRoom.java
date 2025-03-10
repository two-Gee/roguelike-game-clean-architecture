package com.example.domain.map;

import com.example.domain.Item.Item;
import com.example.domain.Monster.Monster;
import com.example.domain.Position;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DungeonRoom {
    private Position topLeftCorner;
    private Position bottomRightCorner;
    private List<Item> items;
    private List<Monster> monsters;
    private int getRoomID;

    public DungeonRoom(int x, int y, int width, int height) {
        topLeftCorner = new Position(x, y);
        bottomRightCorner = new Position(x + width, y + height);
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
        return topLeftCorner.getxPos() <= other.bottomRightCorner.getxPos() && bottomRightCorner.getxPos() >= other.topLeftCorner.getxPos() &&
                topLeftCorner.getyPos() <= other.bottomRightCorner.getyPos() && bottomRightCorner.getyPos() >= other.topLeftCorner.getyPos();
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public int getGetRoomID() {
        return getRoomID;
    }

    public boolean containsPosition(Position position){
        return position.getxPos() >= topLeftCorner.getxPos() && position.getxPos() <= bottomRightCorner.getxPos() &&
                position.getyPos() >= topLeftCorner.getyPos() && position.getyPos() <= bottomRightCorner.getyPos();
    }
}
