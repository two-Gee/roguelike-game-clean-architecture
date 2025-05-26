package com.example.application.factories;

import com.example.domain.Position;
import com.example.domain.map.DungeonRoom;

import java.util.Set;

public class PositionGenerator {
    public static Position generateRandomPosition(DungeonRoom room, Set<Position> occupiedPositions) {
        Position position;
        boolean positionOccupied;

        do {
            int x = (int) (Math.random() * (room.getBottomRightCorner().getX_POS() - room.getTopLeftCorner().getX_POS()) + room.getTopLeftCorner().getX_POS());
            int y = (int) (Math.random() * (room.getBottomRightCorner().getY_POS() - room.getTopLeftCorner().getY_POS()) + room.getTopLeftCorner().getY_POS());
            position = new Position(x, y);
            positionOccupied = occupiedPositions.contains(position);
        } while (positionOccupied);

        return position;
    }
}
