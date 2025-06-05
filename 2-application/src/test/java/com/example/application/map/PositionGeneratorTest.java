package com.example.application.map;

import com.example.application.factories.PositionGenerator;
import com.example.domain.Position;
import com.example.domain.map.DungeonRoom;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PositionGeneratorTest {

    @Test
    public void testGenerateRandomPositionWithinRoomBounds() {
        DungeonRoom room = new DungeonRoom(0, 0, 5, 5, 1);
        Set<Position> occupiedPositions = new HashSet<>();

        Position position = PositionGenerator.generateRandomPosition(room, occupiedPositions);

        assertTrue(position.getX_POS() >= room.getTopLeftCorner().getX_POS());
        assertTrue(position.getX_POS() <= room.getBottomRightCorner().getX_POS());
        assertTrue(position.getY_POS() >= room.getTopLeftCorner().getY_POS());
        assertTrue(position.getY_POS() <= room.getBottomRightCorner().getY_POS());
    }

    @Test
    public void testGenerateRandomPositionAvoidsOccupiedPositions() {
        DungeonRoom room = new DungeonRoom(0, 0, 5, 5, 1);
        Set<Position> occupiedPositions = new HashSet<>();
        occupiedPositions.add(new Position(1, 1));
        occupiedPositions.add(new Position(2, 2));

        Position position = PositionGenerator.generateRandomPosition(room, occupiedPositions);

        assertFalse(occupiedPositions.contains(position));
    }
}