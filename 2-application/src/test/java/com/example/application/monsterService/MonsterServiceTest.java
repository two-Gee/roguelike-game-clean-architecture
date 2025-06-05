package com.example.application.monsterService;

import com.example.application.DungeonRenderer;
import com.example.application.GameStateService;
import com.example.application.stores.ItemStore;
import com.example.application.stores.MonsterStore;
import com.example.domain.Dungeon;
import com.example.domain.Player;
import com.example.domain.Position;
import com.example.domain.monster.Monster;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class MonsterServiceTest {

    @Mock
    private Player player;
    
    @Mock
    private DungeonRenderer dungeonRenderer;
    
    @Mock
    private MonsterStore monsterStore;
    
    @Mock
    private Dungeon dungeon;
    
    @Mock
    private ItemStore itemStore;
    
    @Mock
    private GameStateService gameStateService;
    
    @Mock
    private Monster adjacentMonster;
    
    @Mock
    private Monster distantMonster;
    
    private MonsterService monsterService;
    private Position playerPosition;
    private Position adjacentPosition;
    private Position distantPosition;
    private List<Monster> monsters;
    private List<Monster> emptyMonsters;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Create real Position objects
        playerPosition = new Position(5, 5);
        adjacentPosition = new Position(5, 6);  // Adjacent to playerPosition
        distantPosition = new Position(10, 10); // Not adjacent to playerPosition
        
        // Setup player mock
        when(player.getPosition()).thenReturn(playerPosition);
        when(player.getRoomNumber()).thenReturn(1);
        when(player.getName()).thenReturn("Player");
        
        // Setup monster mocks
        when(adjacentMonster.getPosition()).thenReturn(adjacentPosition);
        when(adjacentMonster.getName()).thenReturn("AdjacentMonster");
        when(adjacentMonster.getAttack()).thenReturn(10);
        
        when(distantMonster.getPosition()).thenReturn(distantPosition);
        when(distantMonster.getName()).thenReturn("DistantMonster");
        
        // No need to mock isAdjacent method as we're using real Position objects
        // that have the real implementation of isAdjacent
        
        monsters = new ArrayList<>();
        monsters.add(adjacentMonster);
        monsters.add(distantMonster);
        
        emptyMonsters = new ArrayList<>();
        
        when(monsterStore.findByRoomNumber(1)).thenReturn(monsters);
        when(itemStore.findByRoomNumber(anyInt())).thenReturn(new ArrayList<>());
        
        monsterService = new MonsterService(player, dungeonRenderer, monsterStore, dungeon, itemStore);
    }

    @Test
    public void testHandleMonsterWithAdjacentMonster() {
        when(gameStateService.checkGameLost(player)).thenReturn(false);
        
        monsterService.handleMonster(gameStateService);
        
        verify(adjacentMonster).attack(player);
        verify(dungeonRenderer).renderNotification(argThat(message -> message.contains("AdjacentMonster attacks Player")));
        verify(gameStateService).checkGameLost(player);
        verify(dungeonRenderer).renderDungeon();
    }
    
    @Test
    public void testHandleMonsterWithGameLost() {
        when(gameStateService.checkGameLost(player)).thenReturn(true);
        
        monsterService.handleMonster(gameStateService);
        
        verify(adjacentMonster).attack(player);
        verify(dungeonRenderer).renderNotification(argThat(message -> message.contains("AdjacentMonster attacks Player")));
        verify(gameStateService).checkGameLost(player);
        verify(distantMonster, never()).getPosition();
        verify(dungeonRenderer, never()).renderDungeon();
    }
    
    @Test
    public void testHandleMonsterWithOnlyDistantMonsters() {
        List<Monster> distantMonstersList = new ArrayList<>();
        distantMonstersList.add(distantMonster);
        when(monsterStore.findByRoomNumber(anyInt())).thenReturn(distantMonstersList);
        
        monsterService.handleMonster(gameStateService);
        
        verify(distantMonster, never()).attack(any());
        verify(dungeonRenderer).renderDungeon();
    }
    
    @Test
    public void testHandleMonsterWithEmptyMonsterList() {
        when(monsterStore.findByRoomNumber(anyInt())).thenReturn(emptyMonsters);
        
        monsterService.handleMonster(gameStateService);
        
        verify(dungeonRenderer, never()).renderNotification(any());
        verify(dungeonRenderer, never()).renderDungeon();
    }
}
