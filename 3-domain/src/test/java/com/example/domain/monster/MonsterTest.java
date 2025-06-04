package com.example.domain.monster;

import com.example.domain.MovementStrategy;
import com.example.domain.Player;
import com.example.domain.Position;
import com.example.domain.item.Item;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MonsterTest {
    
    @Mock
    private MovementStrategy movementStrategy;
    
    private Monster monster;
    private Player player;
    private Position monsterPosition;
    private Position playerPosition;

    private static class TestMonster extends Monster {
        public TestMonster(String name, int health, int attack, int roomNumber, Position position, MovementStrategy movementStrategy) {
            super(name, health, attack, roomNumber, position, movementStrategy);
        }
    }
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        monsterPosition = new Position(5, 5);
        playerPosition = new Position(10, 5);
        monster = new TestMonster("TestMonster", 10, 5, 1, monsterPosition, movementStrategy);
        player = new Player(1, playerPosition, "TestPlayer");
    }
    
    @Test
    public void testInitialization() {
        assertEquals(10, monster.getHealth());
        assertEquals(5, monster.getAttack());
        assertEquals(1, monster.getRoomNumber());
        assertEquals(monsterPosition, monster.getPosition());
        assertEquals("TestMonster", monster.getName());
    }
    
    @Test
    public void testGetNextPosition() {
        Position nextPosition = new Position(6, 5);
        when(movementStrategy.getNextPosition(monster, player)).thenReturn(nextPosition);

        Position nextPos = monster.getNextPosition(player);
        assertEquals(nextPosition, nextPos);
        verify(movementStrategy).getNextPosition(monster, player);

    }
    
    @Test
    public void testAttack() {
        int initialPlayerHealth = player.getHealth();
        monster.attack(player);
        
        assertEquals(initialPlayerHealth - monster.getAttack(), player.getHealth());
    }
    
    @Test
    public void testMove() {
        Position newPosition = new Position(6, 5);
        monster.move(newPosition);
        assertEquals(newPosition, monster.getPosition());
    }
    
    @Test
    public void testIsDead() {
        assertFalse(monster.isDead());
        
        monster.takeDamage(10);
        assertTrue(monster.isDead());
    }
    
    @Test
    public void testGetId() {
        assertNotNull(monster.getId());
    }
}
