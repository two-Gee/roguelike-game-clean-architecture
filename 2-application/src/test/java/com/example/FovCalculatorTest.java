package com.example;

import com.example.application.map.FovCache;
import com.example.application.map.FovCalculator;
import com.example.domain.Dungeon;
import com.example.domain.Position;
import com.example.domain.map.DungeonTile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class FovCalculatorTest {

    @Mock private Dungeon mockDungeon;
    @Mock private FovCache mockCache;
    @Mock private DungeonTile mockTile;

    private FovCalculator fovCalculator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fovCalculator = new FovCalculator(mockDungeon, mockCache);
    }

    @Test
    public void testCalculateFov_ClearsCache() {
        fovCalculator.calculateFov(5, 5, 3);

        verify(mockCache).clearVisibleCache();
    }

    @Test
    public void testCalculateFov_UpdatesFovDataForVisibleTiles() {
        when(mockDungeon.getWidth()).thenReturn(10);
        when(mockDungeon.getHeight()).thenReturn(10);
        when(mockDungeon.getTile(any(Position.class))).thenReturn(mockTile);
        when(mockTile.getBlocksSight()).thenReturn(false);

        fovCalculator.calculateFov(5, 5, 3);

        verify(mockCache, atLeastOnce()).updateFovData(anyInt(), anyInt(), eq(true));
    }

    @Test
    public void testCalculateFov_StopsAtBlockingTile() {
        when(mockDungeon.getWidth()).thenReturn(10);
        when(mockDungeon.getHeight()).thenReturn(10);
        when(mockDungeon.getTile(any(Position.class))).thenReturn(mockTile);
        when(mockTile.getBlocksSight()).thenReturn(true);

        fovCalculator.calculateFov(5, 5, 3);

        verify(mockCache, atLeastOnce()).updateFovData(anyInt(), anyInt(), eq(true));
        verify(mockTile, atLeastOnce()).getBlocksSight();
    }

}