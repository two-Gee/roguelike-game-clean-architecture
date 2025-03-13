package com.example.domain.map;

import java.awt.*;

public enum DungeonTile {
    Floor(" . ", Color.white, true),
    Wall("███", Color.black, false),
    Player(" @ ", Color.white, true),

    Monster(" m ", Color.white, true),
    Bounds("xxx", Color.black, false),
    Unknown("   ", Color.black, true);

    private final String displayCharacter;

    private final Color color;
    private final boolean walkable;

    DungeonTile(String displayCharacter, Color color, boolean walkable) {
        this.displayCharacter = displayCharacter;
        this.color = color;
        this.walkable = walkable;
    }

    public Color getColour() {
        return color;
    }

    public String getDisplayCharacter() {
        return displayCharacter;
    }


    public boolean isWalkable() {
        return walkable;
    }

}
