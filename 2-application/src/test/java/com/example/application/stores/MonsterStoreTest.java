package com.example.application.stores;

import com.example.domain.MovementStrategy;
import com.example.domain.Position;
import com.example.domain.monster.Monster;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MonsterStoreTest {

    private Map<UUID, Monster> monstersMap;
    private MonsterStore monsterStore;

    private Monster monster1;
    private Monster monster2;
    private Monster monster3;
    private Monster monster4;

    private MovementStrategy mockMovementStrategy;

    @Before
    public void setUp() {
        monstersMap = new HashMap<>();

        mockMovementStrategy = mock(MovementStrategy.class);

        monster1 = mock(Monster.class, withSettings()
                .useConstructor("Goblin", 10, 5, 1, new Position(0, 0), mockMovementStrategy)
                .defaultAnswer(CALLS_REAL_METHODS));
        when(monster1.getId()).thenReturn(UUID.randomUUID());

        monster2 = mock(Monster.class, withSettings()
                .useConstructor("Orc", 20, 10, 2, new Position(1, 1), mockMovementStrategy)
                .defaultAnswer(CALLS_REAL_METHODS));
        when(monster2.getId()).thenReturn(UUID.randomUUID());

        monster3 = mock(Monster.class, withSettings()
                .useConstructor("Troll", 100, 50, 1, new Position(2, 2), mockMovementStrategy)
                .defaultAnswer(CALLS_REAL_METHODS));
        when(monster3.getId()).thenReturn(UUID.randomUUID());

        monster4 = mock(Monster.class, withSettings()
                .useConstructor("Goblin", 5, 2, 3, new Position(3, 3), mockMovementStrategy)
                .defaultAnswer(CALLS_REAL_METHODS));
        when(monster4.getId()).thenReturn(UUID.randomUUID());

        monstersMap.put(monster1.getId(), monster1);
        monstersMap.put(monster2.getId(), monster2);
        monstersMap.put(monster3.getId(), monster3);
        monstersMap.put(monster4.getId(), monster4);

        monsterStore = new MonsterStore(monstersMap);
    }

    @Test
    public void testFindByRoomNumber_ExistingRoom() {
        List<Monster> foundMonsters = monsterStore.findByRoomNumber(1);
        assertNotNull("List of monsters should not be null", foundMonsters);
        assertEquals("Should find 2 monsters in room 1", 2, foundMonsters.size());
        assertTrue("List should contain monster1", foundMonsters.contains(monster1));
        assertTrue("List should contain monster3", foundMonsters.contains(monster3));
        assertFalse("List should not contain monster2", foundMonsters.contains(monster2));
    }

    @Test
    public void testFindByRoomNumber_NonExistingRoom() {
        List<Monster> foundMonsters = monsterStore.findByRoomNumber(999);
        assertNotNull("List of monsters should not be null", foundMonsters);
        assertTrue("Should find no monsters in a non-existing room", foundMonsters.isEmpty());
    }

    @Test
    public void testFindByRoomNumber_EmptyStore() {
        monsterStore = new MonsterStore(new HashMap<UUID, Monster>());
        List<Monster> foundMonsters = monsterStore.findByRoomNumber(1);
        assertNotNull("List of monsters should not be null", foundMonsters);
        assertTrue("Should find no monsters in an empty store", foundMonsters.isEmpty());
    }

    @Test
    public void testFindByRoomNumber_NegativeRoomNumber() {
        List<Monster> foundMonsters = monsterStore.findByRoomNumber(-1);
        assertNotNull("List of monsters should not be null", foundMonsters);
        assertTrue("Should return an empty list for negative room numbers", foundMonsters.isEmpty());
    }

    @Test
    public void testRemove_ExistingId() {
        assertTrue("Should return true when removing an existing monster", monsterStore.remove(monster1.getId()));
        assertFalse("Monster should be removed from the map", monstersMap.containsKey(monster1.getId()));
        assertEquals("Map size should decrease by 1", 3, monstersMap.size());
    }

    @Test
    public void testRemove_NonExistingId() {
        assertFalse("Should return false when removing a non-existing monster", monsterStore.remove(UUID.randomUUID()));
        assertEquals("Map size should remain unchanged", 4, monstersMap.size());
    }

    @Test
    public void testRemove_EmptyStore() {
        monsterStore = new MonsterStore(new HashMap<UUID, Monster>());
        assertFalse("Should return false when removing from an empty store", monsterStore.remove(monster1.getId()));
        assertTrue("Map should remain empty", monsterStore.getMonsters().isEmpty());
    }

    @Test
    public void testGetMonsters_PopulatedStore() {
        List<Monster> allMonsters = monsterStore.getMonsters();
        assertNotNull("List of monsters should not be null", allMonsters);
        assertEquals("Should return all 4 monsters", 4, allMonsters.size());
        assertTrue("List should contain monster1", allMonsters.contains(monster1));
        assertTrue("List should contain monster2", allMonsters.contains(monster2));
        assertTrue("List should contain monster3", allMonsters.contains(monster3));
        assertTrue("List should contain monster4", allMonsters.contains(monster4));
    }

    @Test
    public void testGetMonsters_EmptyStore() {
        monsterStore = new MonsterStore(new HashMap<UUID, Monster>());
        List<Monster> allMonsters = monsterStore.getMonsters();
        assertNotNull("List of monsters should not be null", allMonsters);
        assertTrue("Should return an empty list for an empty store", allMonsters.isEmpty());
    }

    @Test
    public void testGetMonsters_Immutability() {
        List<Monster> allMonsters = monsterStore.getMonsters();
        try {
            allMonsters.remove(0);
            fail("Expected UnsupportedOperationException, but none was thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (IndexOutOfBoundsException e) {
            fail("Unexpected IndexOutOfBoundsException. The list was likely empty before modification attempt.");
        }
    }
}