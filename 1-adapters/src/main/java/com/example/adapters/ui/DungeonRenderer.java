package com.example.adapters.ui;

import com.example.application.map.DungeonGenerator;
import com.example.domain.Dungeon;
import com.example.domain.LivingEntity;
import com.example.domain.Position;
import com.example.domain.map.DungeonConfiguration;
import com.example.domain.map.DungeonTile;

import java.awt.*;
import java.util.ArrayList;

public class DungeonRenderer {
    private Dungeon dungeon;

    // TODO add Fov

    public DungeonRenderer(Dungeon dungeon) {
        this.dungeon = dungeon;
    }


    public void renderDungeonToConsole() {
        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                DungeonTile t =  dungeon.getTile(new Position(x,y));
                Color fgColor = t.getColour(DungeonTile.TileColorType.Primary);

                System.out.print(getAnsiColor(fgColor) + t.getDisplayCharacter() + "\u001B[0m"); // Reset color
            }
            System.out.println();
        }
    }

    private String getAnsiColor(Color foreground) {
        String fg = "\u001B[37m"; // Default white text
        String bg = "\u001B[40m"; // Default black background

        // Set foreground color
        if (foreground.equals(Color.WHITE)) fg = "\u001B[38:5:15m";  // White text
        if (foreground.equals(Color.YELLOW)) fg = "\u001B[30m"; // Yellow text
        if (foreground.equals(Color.RED)) fg = "\\e[0;31m\t";  // Black text

        // Set background color
        if (foreground.equals(Color.WHITE)) bg = "\u001B[48:5:166m";  // White background
        if (foreground.equals(Color.YELLOW)) bg = "\u001B[48:5:0m";  // Black background

        return bg + fg; // Combine background and foreground
    }


    // Test rendering for now
    public static void main( String[] args )
    {

        DungeonConfiguration config = new DungeonConfiguration(70,35,3,3,5,12,5,5);


        DungeonRenderer rd = new DungeonRenderer(DungeonGenerator.generateDungeon(config, new ArrayList<LivingEntity>()));
        rd.renderDungeonToConsole();
    }

}
