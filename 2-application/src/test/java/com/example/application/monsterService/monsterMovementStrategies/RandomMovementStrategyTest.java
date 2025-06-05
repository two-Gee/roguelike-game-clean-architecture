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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class RandomMovementStrategyTest {

    @Mock
    private Monster mockMonster;
    @Mock
    private Player mockPlayer;

    private RandomMovementStrategy strategy;
    private Position initialMonsterPos;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        initialMonsterPos = new Position(5, 5);
        when(mockMonster.getPosition()).thenReturn(initialMonsterPos);

        strategy = new RandomMovementStrategy();
    }

    @Test
    public void testGetNextPosition_ReturnsOneOfValidAdjacentPositions() {
        Set<Position> validNextPositions = new HashSet<>();
        validNextPositions.add(new Position(initialMonsterPos.getX_POS() + 1, initialMonsterPos.getY_POS()));
        validNextPositions.add(new Position(initialMonsterPos.getX_POS() - 1, initialMonsterPos.getY_POS()));
        validNextPositions.add(new Position(initialMonsterPos.getX_POS(), initialMonsterPos.getY_POS() + 1));
        validNextPositions.add(new Position(initialMonsterPos.getX_POS(), initialMonsterPos.getY_POS() - 1));
        validNextPositions.add(initialMonsterPos);

        for (int i = 0; i < 100; i++) {
            Position nextPosition = strategy.getNextPosition(mockMonster, mockPlayer);

            assertTrue("The next position (" + nextPosition + ") must be one of the expected adjacent positions or current position.",
                    validNextPositions.contains(nextPosition));
        }
    }
}