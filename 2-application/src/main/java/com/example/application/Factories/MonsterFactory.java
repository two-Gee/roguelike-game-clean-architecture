package com.example.application.Factories;

import com.example.application.ApproachMovementStrategy;
import com.example.application.RandomMovementStrategy;
import com.example.application.StationaryMovementStrategy;
import com.example.domain.Dungeon;
import com.example.domain.Monster.Monster;
import com.example.domain.Monster.MonsterMovementType;
import com.example.domain.Monster.MonsterTypes;
import com.example.domain.Position;
import com.example.domain.map.DungeonRoom;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MonsterFactory {
    public static Monster createMonster(MonsterTypes type, int roomID, Position position){
        return switch (type) {
            case GOBLIN -> new Monster("Goblin", 10, 5, roomID, position, new ApproachMovementStrategy());
            case ORC -> new Monster("Orc", 15, 8, roomID, position, new RandomMovementStrategy());
            case TROLL -> new Monster("Troll", 20, 10, roomID, position, new StationaryMovementStrategy());
            default -> throw new IllegalArgumentException();
        };
    }

    public static Monster createRandomMonster(DungeonRoom room){
        int x = (int) (Math.random() * (room.getBottomRightCorner().getxPos() - room.getTopLeftCorner().getxPos()) + room.getTopLeftCorner().getxPos());
        int y = (int) (Math.random() * (room.getBottomRightCorner().getyPos() - room.getTopLeftCorner().getyPos()) + room.getTopLeftCorner().getyPos());
        Position position = new Position(x, y);

        int random = (int)(Math.random() * MonsterTypes.values().length);
        return createMonster(MonsterTypes.values()[random], room.getRoomNumber(), position);
    }

    public static Map<UUID, Monster> createMonstersForRoom(int amount, DungeonRoom room){
        Map<UUID, Monster> monsters = new HashMap<>();

        for (int i = 0; i < amount; i++) {
            Monster monster = createRandomMonster(room);
            monsters.put(monster.getId(), monster);
        }
        return monsters;
    }


    public static Map<UUID, Monster> createMonsters(int maxRoomMonsters, Map<Integer, DungeonRoom> dungeonRooms) {
        Map<UUID, Monster> monsters = new HashMap<>();
        Random random = new Random();

        for (DungeonRoom room : dungeonRooms.values()) {
            if (room.getRoomNumber() == 0){
                continue;
            }
            int amount = random.nextInt(maxRoomMonsters)+1;
            monsters.putAll(createMonstersForRoom(amount, room));
        }
        return monsters;
    }
}
