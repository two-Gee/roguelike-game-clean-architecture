package com.example.application;

import com.example.domain.map.DungeonConfiguration;
import org.junit.Test;

import static org.junit.Assert.*;

public class LevelSelectionTest {

    @Test
    public void testSelectLevelEasy() {
        DungeonConfiguration config = LevelSelection.selectLevel("1");
        assertNotNull(config);
        assertEquals(70, config.getWidth());
        assertEquals(35, config.getHeight());
        assertEquals(5, config.getMinRooms());
        assertEquals(5, config.getMaxRooms());
        assertEquals(7, config.getMinRoomSize());
        assertEquals(12, config.getMaxRoomSize());
        assertEquals(5, config.getMaxRoomMonsters());
        assertEquals(6, config.getMaxRoomItems());
    }

    @Test
    public void testSelectLevelMedium() {
        DungeonConfiguration config = LevelSelection.selectLevel("2");
        assertNotNull(config);
        assertEquals(70, config.getWidth());
        assertEquals(35, config.getHeight());
        assertEquals(6, config.getMinRooms());
        assertEquals(6, config.getMaxRooms());
        assertEquals(7, config.getMinRoomSize());
        assertEquals(12, config.getMaxRoomSize());
        assertEquals(6, config.getMaxRoomMonsters());
        assertEquals(5,config.getMaxRoomItems());
    }

    @Test
    public void testSelectLevelHard() {
        DungeonConfiguration config = LevelSelection.selectLevel("3");
        assertNotNull(config);
        assertEquals(70, config.getWidth());
        assertEquals(35, config.getHeight());
        assertEquals(7, config.getMinRooms());
        assertEquals(7, config.getMaxRooms());
        assertEquals(7, config.getMinRoomSize());
        assertEquals(12, config.getMaxRoomSize());
        assertEquals(7, config.getMaxRoomMonsters());
        assertEquals(4, config.getMaxRoomItems());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSelectLevelInvalidDifficulty() {
        LevelSelection.selectLevel("invalid");
    }
}