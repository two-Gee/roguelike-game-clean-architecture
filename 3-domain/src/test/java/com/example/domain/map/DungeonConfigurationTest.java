package com.example.domain.map;

import org.junit.Test;
import static org.junit.Assert.*;

public class DungeonConfigurationTest {
    
    @Test
    public void testConfigurationCreation() {
        DungeonConfiguration config = new DungeonConfiguration(
            80, 40, 6, 8, 10, 15, 5, 7
        );
        
        assertEquals(80, config.getWidth());
        assertEquals(40, config.getHeight());
        assertEquals(6, config.getMaxRooms());
        assertEquals(8, config.getMinRooms());
        assertEquals(10, config.getMinRoomSize());
        assertEquals(15, config.getMaxRoomSize());
        assertEquals(15, config.getMaxRoomSize());
        assertEquals(5, config.getMaxRoomMonsters());
        assertEquals(7, config.getMaxRoomItems());
    }


}
