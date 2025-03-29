package com.example.domain.item;

import com.example.domain.Position;

public class Potion extends Consumables {
    public Potion( Position position, int roomNumber) {
        super("Potion", 10, position, roomNumber);
    }
}
