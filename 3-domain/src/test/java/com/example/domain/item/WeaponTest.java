package com.example.domain.item;

import com.example.domain.Position;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WeaponTest {
    
    private Weapon weapon;
    private Position position;
    
    @Before
    public void setUp() {
        position = new Position(5, 5);
        weapon = new Weapon("TestSword", 15, position, 1);
    }
    
    @Test
    public void testInitialization() {
        assertEquals(1, weapon.getRoomNumber());
        assertEquals(position, weapon.getPosition());
        assertEquals("TestSword", weapon.getName());
        assertEquals(15, weapon.getAttack());
        assertFalse(weapon.isPickedUp());
    }
}
