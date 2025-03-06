package com.example.domain;


import com.example.domain.map.DungeonTile;

import java.util.List;
import java.util.UUID;

public class Dungeon {
//    private int level;
//    private int difficulty;
    private int enemyAmount;
    private int remainingEnemies;
    private int itemAmount;
    private DungeonTile[][] dungeonTiles;
    private int width;
    private int height;
    private List<LivingEntity> entities;
    private Position playerSpawnPoint;

// TODO add items, level and difficulty
    public Dungeon(int enemyAmount, DungeonTile[][] dungeonTiles, int width, int height, List<LivingEntity> entities, Position playerSpawnPoint) {
//        this.level = level;
//        this.difficulty = difficulty;
        this.enemyAmount = enemyAmount;
        this.remainingEnemies = enemyAmount;
//        this.itemAmount = itemAmount;
        this.dungeonTiles = dungeonTiles;
        this.width = width;
        this.height = height;
        this.entities = entities;
        this.playerSpawnPoint = playerSpawnPoint;
    }

    public Position getPlayerSpawnPoint() {
        return playerSpawnPoint;
    }

    public int getRemainingEnemies() {
        //TODO checken ob remaining enemies ein eigener count sein sollte oder einfach aus der Liste der Entities berechnet wird
        return remainingEnemies;
    }

    public boolean isWalkable(Position position){
        return getTile(position).isWalkable();
    }

    public DungeonTile getTile(Position position){
        return dungeonTiles[position.getxPos()][position.getyPos()];
    }

}
