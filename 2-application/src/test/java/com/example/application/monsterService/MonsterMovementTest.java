package com.example.application.monsterService;

import com.example.domain.Dungeon;
import com.example.domain.item.Item;
import com.example.domain.map.DungeonRoom;
import com.example.domain.map.DungeonTile;
import com.example.domain.monster.Monster;
import com.example.domain.Player;
import com.example.domain.Position;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MonsterMovementTest {

    @Mock
    private Dungeon mockDungeon;
    @Mock
    private Monster mockMonster;
    @Mock
    private Player mockPlayer;
    @Mock
    private DungeonTile mockTile;
    @Mock
    private DungeonRoom mockRoom;
    @Mock
    private Item mockItem;

    private MonsterMovement monsterMovement;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        monsterMovement = new MonsterMovement(mockMonster, mockPlayer, mockDungeon, Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void testMove_SuccessfulMove() {
        Position newMonsterPos = new Position(2, 1);

        when(mockMonster.getNextPosition(mockPlayer)).thenReturn(newMonsterPos);
        when(mockDungeon.getTile(newMonsterPos)).thenReturn(mockTile);
        when(mockTile.isWalkable()).thenReturn(true);
        when(mockDungeon.getRoomForPosition(newMonsterPos)).thenReturn(mockRoom);

        monsterMovement = new MonsterMovement(mockMonster, mockPlayer, mockDungeon, Collections.emptyList(), Collections.emptyList());
        monsterMovement.move();

        verify(mockMonster, times(1)).move(newMonsterPos);
    }

    @Test
    public void testMove_NewPositionOccupiedByAnotherMonster() {
        Position newMonsterPos = new Position(2, 1);

        Monster otherMonster = mock(Monster.class);
        when(otherMonster.getPosition()).thenReturn(newMonsterPos);

        when(mockMonster.getNextPosition(mockPlayer)).thenReturn(newMonsterPos);

        monsterMovement = new MonsterMovement(mockMonster, mockPlayer, mockDungeon, Arrays.asList(otherMonster), Collections.emptyList());
        monsterMovement.move();

        verify(mockMonster, never()).move(any(Position.class));
    }

    @Test
    public void testMove_NewPositionIsPlayersPosition() {
        Position newMonsterPos = new Position(2, 1);

        when(mockMonster.getNextPosition(mockPlayer)).thenReturn(newMonsterPos);
        when(mockDungeon.getTile(newMonsterPos)).thenReturn(mockTile);

        monsterMovement = new MonsterMovement(mockMonster, mockPlayer, mockDungeon, Collections.emptyList(), Collections.emptyList());
        monsterMovement.move();

        verify(mockMonster, never()).move(any(Position.class));
    }

    @Test
    public void testMove_NewPositionOccupiedByItem() {
        Position newMonsterPos = new Position(2, 1);

        when(mockItem.getPosition()).thenReturn(newMonsterPos);
        when(mockMonster.getNextPosition(mockPlayer)).thenReturn(newMonsterPos);

        monsterMovement = new MonsterMovement(mockMonster, mockPlayer, mockDungeon, Collections.emptyList(), Arrays.asList(mockItem));
        monsterMovement.move();

        verify(mockMonster, never()).move(any(Position.class));
    }

    @Test
    public void testMove_NewPositionNotWalkable() {
        Position newMonsterPos = new Position(2, 1);

        when(mockMonster.getNextPosition(mockPlayer)).thenReturn(newMonsterPos);
        when(mockDungeon.getTile(newMonsterPos)).thenReturn(mockTile);
        when(mockTile.isWalkable()).thenReturn(false);

        monsterMovement.move();

        verify(mockMonster, never()).move(any(Position.class));
    }

    @Test
    public void testMove_NewPositionNotInAnyRoom() {
        Position newMonsterPos = new Position(2, 1);

        when(mockMonster.getNextPosition(mockPlayer)).thenReturn(newMonsterPos);
        when(mockDungeon.getTile(newMonsterPos)).thenReturn(mockTile);

        monsterMovement.move();

        verify(mockMonster, never()).move(any(Position.class));
    }
}