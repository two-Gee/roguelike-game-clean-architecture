package com.example.domain.Item;

import com.example.domain.Position;

public class Bread extends Consumables {
    public Bread(Position position, int roomNumber) {
        super("Bread", 5, position, roomNumber);
    }
}
