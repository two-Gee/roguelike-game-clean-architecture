package com.example.Item;

import domain.TileObject;
import domain.TileType;

public abstract class Item extends TileObject {
    private String name;
    private String description;

    public Item(String name, String description, char symbol) {
        super(TileType.ITEM, symbol);
        this.name = name;
        this.description = description;

    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

}
