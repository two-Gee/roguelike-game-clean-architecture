package com.example.domain.Item;


import com.example.domain.Position;

public abstract class Item {
    private String name;
    private Position position;
    private boolean isPickedUp;
    public int roomNumber;

    public Item(String name, Position position, int roomNumber) {
        this.name = name;
        this.position = position;
        isPickedUp = false;
        this.roomNumber = roomNumber;
    }

    public String getName() {
        return this.name;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
