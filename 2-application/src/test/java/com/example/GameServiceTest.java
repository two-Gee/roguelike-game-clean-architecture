package com.example;

import com.example.application.DungeonRenderer;
import com.example.application.GameService;
import com.example.application.map.FovCache;
import com.example.application.stores.ItemStore;
import com.example.application.stores.MonsterStore;
import com.example.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    private GameService gameService;

    @Mock
    private Player player;
    @Mock
    private Dungeon dungeon;
    @Mock
    private MonsterStore monsterStore;
    @Mock
    private ItemStore itemStore;
    @Mock
    private DungeonRenderer dungeonRenderer;
    @Mock
    private FovCache fovCache;
    @Mock
    private Position position;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(dungeon.getPlayerSpawnPoint()).thenReturn(position);
        when(position.getX_POS()).thenReturn(0);
        when(position.getY_POS()).thenReturn(0);

        gameService = new GameService(player, dungeon, monsterStore, itemStore, dungeonRenderer, fovCache);
    }

    @Test
    public void testHandlePlayer() {
        Direction direction = Direction.NORTH;
        Position newPosition = mock(Position.class);

        when(player.getPosition()).thenReturn(position);
        when(position.getAdjacentPosition(direction)).thenReturn(newPosition);

        gameService.handlePlayer(direction);

        verify(player, atLeastOnce()).getPosition();
        verify(dungeonRenderer).renderDungeon();
    }

    @Test
    public void testPickUpItem() {
        gameService.pickUpItem();

        verify(dungeonRenderer).renderNotification("No item to pick up");
    }

    @Test
    public void testHandleMonsters() {
        gameService.handleMonsters();

        verify(monsterStore, atLeastOnce()).findByRoomNumber(anyInt());
    }

    @Test
    public void testGetDungeonRenderer() {
        DungeonRenderer renderer = gameService.getDungeonRenderer();
        assertNotNull(renderer);
        assertEquals(dungeonRenderer, renderer);
    }

}