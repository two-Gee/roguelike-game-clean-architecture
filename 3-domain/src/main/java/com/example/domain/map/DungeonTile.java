package com.example.domain.map;

import java.awt.*;

public enum DungeonTile {
    Floor(" . ", Color.white, Color.lightGray, true),
    Wall("███", Color.yellow, Color.orange , false),
    Player(" @ ", Color.red, Color.red, true),

    Monster(" m ", Color.white, Color.lightGray, true),
    Weapon(" W ", Color.white, Color.lightGray, true),
    Consumable(" C ", Color.white, Color.lightGray,true),

    Bounds("xxx", Color.black, Color.black, false),
    Unknown("   ", Color.black, Color.black, true);

    private final String displayCharacter;

    private final Color primaryColor;
    private final Color secondaryColor;
    private final boolean walkable;
    private final boolean blocksSight;

    public enum TileColorType { Primary, Secondary }

    DungeonTile(String displayCharacter,Color primaryColor, Color secondaryColor, boolean walkable) {
        this.displayCharacter = displayCharacter;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.walkable = walkable;
        this.blocksSight = !walkable;
    }

    public boolean getBlocksSight() { return blocksSight; }

    public Color getColour(TileColorType tileColorType) {
        return tileColorType == TileColorType.Primary ? primaryColor : secondaryColor;
    }

    public String getDisplayCharacter() {
        return displayCharacter;
    }


    public boolean isWalkable() {
        return walkable;
    }

}
