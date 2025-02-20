package com.example;

import com.example.Monster.MonsterFactory;

import java.util.Random;

public class Dungeon {
    private int level;
    private int difficulty;
    private int monsterCount;
    private int itemCount;
    private Map map;
    public Dungeon(int level, int difficulty, int monsterCount, int itemCount, Map map) {
        this.level = level;
        this.difficulty = difficulty;
        this.monsterCount = monsterCount;
        this.itemCount = itemCount;
        this.map = map;
//        for(int i=0; i<monsterCount; i++) {
//            Random rnd = new Random();
//            int x = rnd.nextInt(grid.length);
//            int y = rnd.nextInt(grid[0].length);
//            if(grid[x][y].getTileType() == TileType.NOTHING) {
//                this.grid[x][y] = MonsterFactory.createMonsterRandom();
//            }
//        }
//        for(int i=0; i<monsterCount; i++) {
//            Random rnd = new Random();
//            int x = rnd.nextInt(grid.length);
//            int y = rnd.nextInt(grid[0].length);
//            if(grid[x][y].getTileType() == TileType.NOTHING) {
//                this.grid[x][y] = MonsterFactory.createMonsterRandom();
//            }
//        }

    }

}
