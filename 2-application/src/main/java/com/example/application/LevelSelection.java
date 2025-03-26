package com.example.application;

import com.example.domain.map.DungeonConfiguration;

public class LevelSelection {
    public static DungeonConfiguration selectLevel(String difficulty) {
        return switch (difficulty) {
            case "easy" -> new DungeonConfiguration(70, 35, 8, 5, 5, 12, 5, 6);
            case "medium" -> new DungeonConfiguration(70, 35, 5, 3, 5, 12, 6, 5);
            case "hard" -> new DungeonConfiguration(70, 35, 5, 3, 5, 12, 7, 4);
            default -> new DungeonConfiguration(70, 35, 5, 3, 5, 12, 1, 1);
        };
    }
}
