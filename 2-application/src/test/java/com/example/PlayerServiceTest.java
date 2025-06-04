package com.example;

import com.example.application.DungeonRenderer;
import com.example.application.playerService.PlayerService;
import com.example.application.stores.MonsterStore;
import com.example.domain.Direction;
import com.example.domain.Dungeon;
import com.example.domain.Player;
import com.example.domain.Position;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {

    @Mock private Player mockPlayer;
    @Mock private Dungeon mockDungeon;
    @Mock private MonsterStore mockMonsterStore;
    @Mock private DungeonRenderer mockDungeonRenderer;

    @Mock private Position mockPlayerCurrentPosition;
    @Mock private Position mockNextPosition;

    private PlayerService playerService;

    @Before
    public void setUp() {
        when(mockPlayer.getPosition()).thenReturn(mockPlayerCurrentPosition);
        when(mockPlayerCurrentPosition.getAdjacentPosition(any(Direction.class))).thenReturn(mockNextPosition);

        playerService = new PlayerService(mockPlayer, mockDungeon, mockMonsterStore, mockDungeonRenderer);
    }

    @Test
    public void testPlayerAttackMonster() {
        Position mockPosition = mock(Position.class);

        playerService.playerAttackMonster(mockPosition);
    }

    @Test
    public void testMovePlayer() {
        Direction mockDirection = Direction.EAST;

        playerService.movePlayer(mockDirection);
    }
}