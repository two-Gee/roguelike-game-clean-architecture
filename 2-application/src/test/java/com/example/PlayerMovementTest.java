package com.example;

import com.example.application.playerService.PlayerMovement;
import com.example.domain.Direction;
import com.example.domain.Dungeon;
import com.example.domain.Player;
import com.example.domain.Position;
import com.example.domain.map.DungeonRoom;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class PlayerMovementTest {

    @Mock private Dungeon mockDungeon;
    @Mock private Player mockPlayer;
    @Mock private Position mockCurrentPosition;
    @Mock private Position mockNewPosition;
    @Mock private DungeonRoom mockRoom;

    private PlayerMovement playerMovement;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockPlayer.getPosition()).thenReturn(mockCurrentPosition);
        when(mockCurrentPosition.getAdjacentPosition(any(Direction.class))).thenReturn(mockNewPosition);

        playerMovement = new PlayerMovement(mockPlayer, mockDungeon);
    }

    @Test
    public void testMoveInDirection_WalkablePosition() {
        when(mockDungeon.isWalkable(mockNewPosition)).thenReturn(true);
        when(mockDungeon.getRoomForPosition(mockNewPosition)).thenReturn(mockRoom);
        when(mockRoom.getRoomNumber()).thenReturn(2);

        playerMovement.moveInDirection(Direction.NORTH);

        verify(mockPlayer).move(mockNewPosition);
        verify(mockPlayer).setRoomNumber(2);
    }

    @Test
    public void testMoveInDirection_NonWalkablePosition() {
        when(mockDungeon.isWalkable(mockNewPosition)).thenReturn(false);

        playerMovement.moveInDirection(Direction.NORTH);

        verify(mockPlayer, never()).move(any(Position.class));
        verify(mockPlayer, never()).setRoomNumber(anyInt());
    }

    @Test
    public void testMoveInDirection_NoRoomForPosition() {
        when(mockDungeon.isWalkable(mockNewPosition)).thenReturn(true);
        when(mockDungeon.getRoomForPosition(mockNewPosition)).thenReturn(null);

        playerMovement.moveInDirection(Direction.NORTH);

        verify(mockPlayer).move(mockNewPosition);
        verify(mockPlayer).setRoomNumber(-1);
    }

    @Test
    public void testMoveInDirection_SameRoomNumber() {
        when(mockDungeon.isWalkable(mockNewPosition)).thenReturn(true);
        when(mockDungeon.getRoomForPosition(mockNewPosition)).thenReturn(mockRoom);
        when(mockRoom.getRoomNumber()).thenReturn(1);
        when(mockPlayer.getRoomNumber()).thenReturn(1);

        playerMovement.moveInDirection(Direction.NORTH);

        verify(mockPlayer).move(mockNewPosition);
        verify(mockPlayer, never()).setRoomNumber(anyInt());
    }
}