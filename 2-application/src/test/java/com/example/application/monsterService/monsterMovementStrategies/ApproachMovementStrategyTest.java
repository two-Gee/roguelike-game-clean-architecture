package com.example.application.monsterService.monsterMovementStrategies;

import com.example.domain.monster.Monster;
import com.example.domain.Player;
import com.example.domain.Position;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ApproachMovementStrategyTest {

    @Mock
    private Monster mockMonster;
    @Mock
    private Player mockPlayer;

    private ApproachMovementStrategy strategy;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        strategy = new ApproachMovementStrategy();
    }

    @Test
    public void testGetNextPosition_PlayerToRight_XDiffGreater() {
        Position monsterPos = new Position(5, 5);
        Position playerPos = new Position(7, 5);

        when(mockMonster.getPosition()).thenReturn(monsterPos);
        when(mockPlayer.getPosition()).thenReturn(playerPos);

        Position expectedPosition = new Position(6, 5);
        Position actualPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("Monster should move right by 1 (X diff is greater)", expectedPosition, actualPosition);
    }

    @Test
    public void testGetNextPosition_PlayerToLeft_XDiffGreater() {
        Position monsterPos = new Position(5, 5);
        Position playerPos = new Position(3, 5);

        when(mockMonster.getPosition()).thenReturn(monsterPos);
        when(mockPlayer.getPosition()).thenReturn(playerPos);

        Position expectedPosition = new Position(4, 5);
        Position actualPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("Monster should move left by 1 (X diff is greater)", expectedPosition, actualPosition);
    }

    @Test
    public void testGetNextPosition_PlayerToRightAndSlightlyUp_XDiffGreater() {
        Position monsterPos = new Position(5, 5);
        Position playerPos = new Position(8, 6);

        when(mockMonster.getPosition()).thenReturn(monsterPos);
        when(mockPlayer.getPosition()).thenReturn(playerPos);

        Position expectedPosition = new Position(6, 5);
        Position actualPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("Monster should move right by 1 (X diff is greater)", expectedPosition, actualPosition);
    }

    @Test
    public void testGetNextPosition_PlayerToLeftAndSlightlyDown_XDiffGreater() {
        Position monsterPos = new Position(5, 5);
        Position playerPos = new Position(2, 4);

        when(mockMonster.getPosition()).thenReturn(monsterPos);
        when(mockPlayer.getPosition()).thenReturn(playerPos);

        Position expectedPosition = new Position(4, 5);
        Position actualPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("Monster should move left by 1 (X diff is greater)", expectedPosition, actualPosition);
    }

    @Test
    public void testGetNextPosition_PlayerToUp_YDiffGreater() {
        Position monsterPos = new Position(5, 5);
        Position playerPos = new Position(5, 7);

        when(mockMonster.getPosition()).thenReturn(monsterPos);
        when(mockPlayer.getPosition()).thenReturn(playerPos);

        Position expectedPosition = new Position(5, 6);
        Position actualPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("Monster should move up by 1 (Y diff is greater)", expectedPosition, actualPosition);
    }

    @Test
    public void testGetNextPosition_PlayerToDown_YDiffGreater() {
        Position monsterPos = new Position(5, 5);
        Position playerPos = new Position(5, 3);

        when(mockMonster.getPosition()).thenReturn(monsterPos);
        when(mockPlayer.getPosition()).thenReturn(playerPos);

        Position expectedPosition = new Position(5, 4);
        Position actualPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("Monster should move down by 1 (Y diff is greater)", expectedPosition, actualPosition);
    }

    @Test
    public void testGetNextPosition_PlayerToUpAndSlightlyRight_YDiffGreater() {
        Position monsterPos = new Position(5, 5);
        Position playerPos = new Position(6, 8);

        when(mockMonster.getPosition()).thenReturn(monsterPos);
        when(mockPlayer.getPosition()).thenReturn(playerPos);

        Position expectedPosition = new Position(5, 6);
        Position actualPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("Monster should move up by 1 (Y diff is greater)", expectedPosition, actualPosition);
    }

    @Test
    public void testGetNextPosition_PlayerToDownAndSlightlyLeft_YDiffGreater() {
        Position monsterPos = new Position(5, 5);
        Position playerPos = new Position(4, 2);

        when(mockMonster.getPosition()).thenReturn(monsterPos);
        when(mockPlayer.getPosition()).thenReturn(playerPos);

        Position expectedPosition = new Position(5, 4);
        Position actualPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("Monster should move down by 1 (Y diff is greater)", expectedPosition, actualPosition);
    }

    @Test
    public void testGetNextPosition_PlayerDiagonalUpRight_EqualDiff() {
        Position monsterPos = new Position(5, 5);
        Position playerPos = new Position(7, 7);

        when(mockMonster.getPosition()).thenReturn(monsterPos);
        when(mockPlayer.getPosition()).thenReturn(playerPos);

        Position expectedPosition = new Position(5, 6); // Move up by 1
        Position actualPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("Monster should move up by 1 (Y diff prioritized when equal)", expectedPosition, actualPosition);
    }

    @Test
    public void testGetNextPosition_PlayerDiagonalDownLeft_EqualDiff() {
        Position monsterPos = new Position(5, 5);
        Position playerPos = new Position(3, 3);

        when(mockMonster.getPosition()).thenReturn(monsterPos);
        when(mockPlayer.getPosition()).thenReturn(playerPos);

        // Prioritizes Y movement when equal
        Position expectedPosition = new Position(5, 4);
        Position actualPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("Monster should move down by 1 (Y diff prioritized when equal)", expectedPosition, actualPosition);
    }

    @Test
    public void testGetNextPosition_PlayerAtSamePosition() {
        Position monsterPos = new Position(5, 5);
        Position playerPos = new Position(5, 5);

        when(mockMonster.getPosition()).thenReturn(monsterPos);
        when(mockPlayer.getPosition()).thenReturn(playerPos);

        Position expectedPosition = new Position(5, 5);
        Position actualPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("Monster should stay at current position if at player's position", expectedPosition, actualPosition);
    }
}