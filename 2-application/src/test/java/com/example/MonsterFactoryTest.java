package com.example;

import com.example.application.factories.MonsterFactory;
import com.example.domain.Position;
import com.example.domain.map.DungeonRoom;
import com.example.domain.monster.Goblin;
import com.example.domain.monster.Monster;
import com.example.domain.monster.MonsterTypes;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

public class MonsterFactoryTest {

    private final MonsterFactory monsterFactory = new MonsterFactory();

    @Test
    public void testCreateMonster() {
        Position position = new Position(2, 3);
        Monster monster = monsterFactory.createMonster(MonsterTypes.GOBLIN, 1, position);

        assertNotNull(monster);
        assertTrue(monster instanceof Goblin);
        assertEquals(1, monster.getRoomNumber());
        assertEquals(position, monster.getPosition());
    }

    @Test
    public void testCreateRandomMonster() {
        DungeonRoom room = new DungeonRoom(0, 0, 5, 5, 1);
        Set<Position> occupiedPositions = Set.of(new Position(1, 1), new Position(2, 2));

        Monster monster = monsterFactory.createRandomMonster(room, occupiedPositions);

        assertNotNull(monster);
        assertTrue(room.containsPosition(monster.getPosition()));
        assertFalse(occupiedPositions.contains(monster.getPosition()));
    }

    @Test
    public void testCreateMonstersForRoom() {
        DungeonRoom room = new DungeonRoom(0, 0, 5, 5, 1);
        int amount = 3;

        Map<UUID, Monster> monsters = monsterFactory.createMonstersForRoom(amount, room);

        assertNotNull(monsters);
        assertEquals(amount, monsters.size());
        for (Monster monster : monsters.values()) {
            assertTrue(room.containsPosition(monster.getPosition()));
        }
    }

    @Test
    public void testCreateMonsters() {
        DungeonRoom room1 = new DungeonRoom(0, 0, 5, 5, 1);
        DungeonRoom room2 = new DungeonRoom(6, 6, 10, 10, 2);
        Map<Integer, DungeonRoom> dungeonRooms = new HashMap<>();
        dungeonRooms.put(1, room1);
        dungeonRooms.put(2, room2);

        int maxRoomMonsters = 3;
        Map<UUID, Monster> monsters = monsterFactory.createMonsters(maxRoomMonsters, dungeonRooms);

        assertNotNull(monsters);
        assertFalse(monsters.isEmpty());
        for (Monster monster : monsters.values()) {
            assertTrue(dungeonRooms.containsKey(monster.getRoomNumber()));
        }
    }
}