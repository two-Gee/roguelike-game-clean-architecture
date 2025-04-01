package com.example.application.map;

import com.example.domain.Dungeon;
import com.example.domain.Position;
import com.example.domain.map.DungeonTile;

public class FovCalculator {
    private final Dungeon dungeon;
    private final FovCache cache;

    public FovCalculator(Dungeon dungeon, FovCache cache) {
        this.dungeon = dungeon;
        this.cache = cache;

    }

    public void calculateFov(int playerX, int playerY, int maxRadius) {
        // first clear our visible cache
        cache.clearVisibleCache();

        // then recalculate it
        for(int x = -maxRadius; x < maxRadius; x++) {
            for(int y = -maxRadius; y < maxRadius; y++) {

                if(x*x + y*y > maxRadius * maxRadius) continue;

                if((playerX + x < 0) || (playerX + x >= dungeon.getWidth()) ||
                        (playerY + y < 0) || (playerY + y >= dungeon.getHeight())) continue;

                Line line = Line.createLine(playerX, playerY, playerX + x, playerY + y);

                for(Position point : line.getPoints()) {
                    DungeonTile tile = dungeon.getTile(point);
                    cache.updateFovData(point.getxPos(), point.getyPos(), true);

                    if(tile.getBlocksSight()) break;
                }
            }
        }

    }
}