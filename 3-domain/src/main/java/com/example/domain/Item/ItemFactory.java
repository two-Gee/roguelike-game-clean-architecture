package com.example.domain.Item;

public class ItemFactory {
    public Item createItem(ItemTypes type) {
        return switch (type) {
            case POTION -> new Consumables("Potion", "A potion that heals you", 20, 'p');
            case BREAD -> new Consumables("Bread", "A loaf of bread that heals you", 10, 'b');
            case SWORD -> new Weapon("Sword", "A sword that deals damage", 15, 's');
            case AXE -> new Weapon("Axe", "An axe that deals damage", 10, 'a');
            default -> throw new IllegalArgumentException("Invalid item type" + type);
        };
    }
    public Item createItemRandom() {
        int random = (int)(Math.random() * ItemTypes.values().length);
        return createItem(ItemTypes.values()[random]);
    }

}
