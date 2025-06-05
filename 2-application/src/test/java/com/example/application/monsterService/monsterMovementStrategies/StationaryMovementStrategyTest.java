package com.example.application.monsterService.monsterMovementStrategies;

import com.example.domain.monster.Monster;
import com.example.domain.Player;
import com.example.domain.Position;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StationaryMovementStrategyTest {

    @Mock
    private Monster mockMonster;
    @Mock
    private Player mockPlayer;

    private StationaryMovementStrategy strategy;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        strategy = new StationaryMovementStrategy();
    }

    @Test
    public void testGetNextPosition_ReturnsMonstersCurrentPosition() {
        Position monsterCurrentPos = new Position(5, 5);

        when(mockMonster.getPosition()).thenReturn(monsterCurrentPos);

        Position nextPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("The next position should be the monster's current position", monsterCurrentPos, nextPosition);
    }

    @Test
    public void testGetNextPosition_ReturnsMonstersCurrentPositionDifferentCoordinates() {
        Position monsterCurrentPos = new Position(1, 2);

        when(mockMonster.getPosition()).thenReturn(monsterCurrentPos);

        Position nextPosition = strategy.getNextPosition(mockMonster, mockPlayer);

        assertEquals("The next position should be the monster's current position", monsterCurrentPos, nextPosition);
    }

}