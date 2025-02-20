package com.example.domain.Item;


public abstract class Item {
    private String name;
    private String description;

    public Item(String name, String description, char symbol) {
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
