package com.example.domain;

import com.example.domain.item.Weapon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    
    private Player player;
    private Position position;
    private Weapon weapon;
    
    @Before
    public void setUp() {
        position = new Position(5, 5);
        player = new Player(1, position, "TestPlayer");
        weapon = new Weapon("TestSword",1, new Position(5, 6),  15);
    }
    
    @Test
    public void testEquipWeapon() {
        // Initial attack value
        int initialAttack = player.getAttack();
        
        // Equip weapon
        player.equipWeapon(weapon);
        
        // Check if weapon is equipped
        assertNotNull(player.getEquippedWeapon());
        assertEquals(weapon, player.getEquippedWeapon());
        
        // Check if attack value increased
        assertEquals(initialAttack + weapon.getAttack(), player.getAttack());
        
        // Check if weapon state changed
        assertTrue(weapon.isPickedUp());
        assertNull(weapon.getPosition());
        assertEquals(-1, weapon.getRoomNumber());
    }
    
    @Test
    public void testUnEquipWeapon() {
        // Equip weapon first
        player.equipWeapon(weapon);
        int attackWithWeapon = player.getAttack();
        
        // Unequip weapon
        Weapon returnedWeapon = player.unEquipWeapon();
        
        // Check if weapon is unequipped
        assertNull(player.getEquippedWeapon());
        assertEquals(weapon, returnedWeapon);
        
        // Check if attack value decreased
        assertEquals(attackWithWeapon - weapon.getAttack(), player.getAttack());
        
        // Check if weapon state changed
        assertFalse(weapon.isPickedUp());
    }
    
    @Test
    public void testUnEquipWhenNoWeaponEquipped() {
        // Try to unequip when no weapon is equipped
        Weapon returnedWeapon = player.unEquipWeapon();
        
        // Should return null
        assertNull(returnedWeapon);
        assertNull(player.getEquippedWeapon());
    }
    
    @Test
    public void testHeal() {
        // Reduce health first
        player.setHealth(50);
        assertEquals(50, player.getHealth());
        
        // Heal partially
        player.heal(20);
        assertEquals(70, player.getHealth());
        
        // Heal to max
        player.heal(50);
        assertEquals(100, player.getHealth());
    }
    
    @Test
    public void testHealBeyondMax() {
        // Set to half health
        player.setHealth(50);
        
        // Heal beyond max health
        player.heal(100);
        
        // Should be capped at max health
        assertEquals(100, player.getHealth());
    }
}
