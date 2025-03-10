package com.example.application.Factories;

import com.example.domain.Dungeon;
import com.example.domain.Monster.Monster;
import com.example.domain.Monster.MonsterMovementType;
import com.example.domain.Monster.MonsterTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MonsterFactory {
    public static Monster createMonster(MonsterTypes type, int roomID, Dungeon dungeon){
        return switch (type) {
            case GOBLIN -> new Monster("Goblin", 10, 5, roomID, dungeon.getRandomPositionInRoom(roomID), MonsterMovementType.APPROACH);
            case ORC -> new Monster("Orc", 15, 8, roomID, dungeon.getRandomPositionInRoom(roomID), MonsterMovementType.RANDOM);
            case TROLL -> new Monster("Troll", 20, 10, roomID, dungeon.getRandomPositionInRoom(roomID), MonsterMovementType.STATIONARY);
            default -> throw new IllegalArgumentException();
        };
    }

    public static Monster createRandomMonster(int roomID, Dungeon dungeon){
        int random = (int)(Math.random() * MonsterTypes.values().length);
        return createMonster(MonsterTypes.values()[random], roomID, dungeon);
    }

    public static Map<UUID, Monster> createMonstersForRoom(int amount, int roomID, Dungeon dungeon){
        Map<UUID, Monster> monsters = new HashMap<>();
        for (int i = 0; i < amount; i++) {
            Monster monster = createRandomMonster(roomID, dungeon);
            monsters.put(monster.getId(), monster);
        }
        return monsters;
    }
}
