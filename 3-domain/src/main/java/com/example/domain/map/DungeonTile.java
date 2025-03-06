package com.example.domain.map;

import java.awt.*;

public enum DungeonTile {
    Floor('.', Color.white, Color.LIGHT_GRAY, true),
    Wall('#', Color.yellow, Color.orange, false),
    Bounds('x', Color.black, Color.black, false),
    Unknown(' ', Color.black, Color.black, true);

    private final char displayCharacter;

    private final Color primaryColor;
    private final Color secondaryColor;
    private final boolean walkable;
    private final boolean blocksSight;

    DungeonTile(char displayCharacter, Color primaryColor, Color secondaryColor, boolean walkable) {
        this.displayCharacter = displayCharacter;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.walkable = walkable;
        this.blocksSight = !walkable;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
