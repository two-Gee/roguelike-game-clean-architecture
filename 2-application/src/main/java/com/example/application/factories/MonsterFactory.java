package com.example.application.factories;

import com.example.application.monsterMovement.ApproachMovementStrategy;
import com.example.application.monsterMovement.RandomMovementStrategy;
import com.example.application.monsterMovement.StationaryMovementStrategy;
import com.example.domain.monster.*;
import com.example.domain.Position;
import com.example.domain.map.DungeonRoom;

import java.util.*;
import java.util.stream.Collectors;

public class MonsterFactory {
    public static Monster createMonster(MonsterTypes type, int roomID, Position position){
        return switch (type) {
            case GOBLIN -> new Goblin(roomID, position, new ApproachMovementStrategy());
            case ORC -> new Orc(roomID, position, new RandomMovementStrategy());
            case TROLL -> new Troll(roomID, position, new StationaryMovementStrategy());
        };
    }

    public static Monster createRandomMonster(DungeonRoom room, Set<Position> occupiedPositions){
        Position position;
        boolean positionOccupied;

        do {
            int x = (int) (Math.random() * (room.getBottomRightCorner().getxPos() - room.getTopLeftCorner().getxPos()) + room.getTopLeftCorner().getxPos());
            int y = (int) (Math.random() * (room.getBottomRightCorner().getyPos() - room.getTopLeftCorner().getyPos()) + room.getTopLeftCorner().getyPos());
            position = new Position(x, y);
            positionOccupied = occupiedPositions.contains(position);
        } while (positionOccupied);

        int random = (int)(Math.random() * MonsterTypes.values().length);
        return createMonster(MonsterTypes.values()[random], room.getRoomNumber(), position);
    }

    public static Map<UUID, Monster> createMonstersForRoom(int amount, DungeonRoom room){
        Map<UUID, Monster> monsters = new HashMap<>();

        for (int i = 0; i < amount; i++) {
            Set<Position> occupiedPositions = monsters.values().stream().map(Monster::getPosition).collect(Collectors.toSet());
            Monster monster = createRandomMonster(room, occupiedPositions);
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
