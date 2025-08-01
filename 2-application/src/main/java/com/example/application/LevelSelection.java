package com.example.application;

import com.example.domain.map.DungeonConfiguration;

public class LevelSelection {
    public static DungeonConfiguration selectLevel(String difficulty) {
        return switch (difficulty) {
            case "1" -> new DungeonConfiguration(70, 35, 5, 5, 7, 12, 5, 6);
            case "2" -> new DungeonConfiguration(70, 35, 6, 6, 7, 12, 6, 5);
            case "3" -> new DungeonConfiguration(70, 35, 7, 7, 7, 12, 7, 4);
            default -> throw new IllegalArgumentException("Invalid difficulty");
        };
    }
}
