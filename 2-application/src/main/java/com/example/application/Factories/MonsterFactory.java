package com.example.application.Factories;

import com.example.domain.Monster.Monster;
import com.example.domain.Monster.MonsterTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MonsterFactory {
    public static Monster createMonster(MonsterTypes type, int roomID){
        return switch (type) {
            case GOBLIN -> new Monster("Goblin", 10, 5, roomID);
            case ORC -> new Monster("Orc", 20, 10, roomID);
            case TROLL -> new Monster("Troll", 15, 8, roomID);
            default -> throw new IllegalArgumentException();
        };
    }

    public static Monster createRandomMonster(int roomID){
        int random = (int)(Math.random() * MonsterTypes.values().length);
        return createMonster(MonsterTypes.values()[random], roomID);
    }

    public static Map<UUID, Monster> createMonstersForRoom(int amount, int roomID){
        Map<UUID, Monster> monsters = new HashMap<>();
        for (int i = 0; i < amount; i++) {
            Monster monster = createRandomMonster(roomID);
            monsters.put(monster.getId(), monster);
        }
        return monsters;
    }
}
