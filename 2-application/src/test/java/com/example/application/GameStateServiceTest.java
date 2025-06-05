package com.example.application;

import com.example.application.stores.MonsterStore;
import com.example.domain.Player;
import com.example.domain.monster.Monster;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class GameStateServiceTest {

    private GameStateService gameStateService;
    @Mock
    private DungeonRenderer dungeonRenderer;
    @Mock
    private MonsterStore monsterStore;
    @Mock
    private Player player;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        gameStateService = new GameStateService(dungeonRenderer, monsterStore);
    }

    @Test
    public void testCheckGameLost_PlayerDead() {
        when(player.isDead()).thenReturn(true);

        boolean result = gameStateService.checkGameLost(player);

        assertTrue(result);
        verify(dungeonRenderer).renderGameLost();
    }

    @Test
    public void testCheckGameLost_PlayerAlive() {
        when(player.isDead()).thenReturn(false);

        boolean result = gameStateService.checkGameLost(player);

        assertFalse(result);
        verify(dungeonRenderer, never()).renderGameLost();
    }

    @Test
    public void testCheckGameWon_AllMonstersDefeated() {
        when(monsterStore.getMonsters()).thenReturn(List.of());

        boolean result = gameStateService.checkGameWon();

        assertTrue(result);
        verify(dungeonRenderer).renderWin();
    }

    @Test
    public void testCheckGameWon_MonstersRemaining() {
        when(monsterStore.getMonsters()).thenReturn(List.of(mock(Monster.class)));

        boolean result = gameStateService.checkGameWon();

        assertFalse(result);
        verify(dungeonRenderer, never()).renderWin();
    }

    @Test
    public void testIsGameOver() {
        assertFalse(gameStateService.isGameOver());
    }
}