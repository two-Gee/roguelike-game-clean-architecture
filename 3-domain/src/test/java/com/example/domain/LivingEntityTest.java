package com.example.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LivingEntityTest {
    
    private TestLivingEntity entity;
    private Position initialPosition;
    
    // Concrete subclass of LivingEntity for testing
    private static class TestLivingEntity extends LivingEntity {
        public TestLivingEntity(int health, int attack, int roomNumber, Position position, String name) {
            super(health, attack, roomNumber, position, name);
        }
    }
    
    @Before
    public void setUp() {
        initialPosition = new Position(5, 5);
        entity = new TestLivingEntity(100, 10, 1, initialPosition, "TestEntity");
    }
    
    @Test
    public void testInitialization() {
        assertEquals(100, entity.getHealth());
        assertEquals(10, entity.getAttack());
        assertEquals(1, entity.getRoomNumber());
        assertEquals(initialPosition, entity.getPosition());
        assertEquals("TestEntity", entity.getName());
    }
    
    @Test
    public void testSetHealth() {
        entity.setHealth(50);
        assertEquals(50, entity.getHealth());
    }
    
    @Test
    public void testSetAttack() {
        entity.setAttack(20);
        assertEquals(20, entity.getAttack());
    }
    
    @Test
    public void testSetRoomNumber() {
        entity.setRoomNumber(2);
        assertEquals(2, entity.getRoomNumber());
    }
    
    @Test
    public void testMove() {
        Position newPosition = new Position(6, 6);
        entity.move(newPosition);
        assertEquals(newPosition, entity.getPosition());
    }
    
    @Test
    public void testAttack() {
        // Create target entity
        TestLivingEntity target = new TestLivingEntity(100, 5, 1, new Position(6, 5), "Target");
        
        // Entity attacks target
        entity.attack(target);
        
        // Target's health should be reduced by attacker's attack value
        assertEquals(90, target.getHealth());
    }
    
    @Test
    public void testAttackKillsTarget() {
        // Create target entity with low health
        TestLivingEntity target = new TestLivingEntity(10, 5, 1, new Position(6, 5), "Target");
        
        // Entity attacks target
        entity.attack(target);
        
        // Target's health should be reduced to 0 (not negative)
        assertEquals(0, target.getHealth());
    }
}
