package com.example;

import com.example.application.map.FovCache;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FovCacheTest {

    private FovCache fovCache;

    @Before
    public void setUp() {
        fovCache = new FovCache(10, 10);
    }

    @Test
    public void testClearVisibleCache() {
        fovCache.updateFovData(5, 5, true);
        fovCache.clearVisibleCache();

        assertFalse(fovCache.isInFov(5, 5));
    }

    @Test
    public void testInitialiseFovData() {
        fovCache.updateFovData(3, 3, true);
        fovCache.initialiseFovData(3, 3);

        assertFalse(fovCache.isInFov(3, 3));
    }

    @Test
    public void testUpdateFovData() {
        fovCache.updateFovData(2, 2, true);

        assertTrue(fovCache.isInFov(2, 2));
        assertTrue(fovCache.isExplored(2, 2));
    }

    @Test
    public void testIsInFov_InvalidPosition() {
        assertFalse(fovCache.isInFov(-1, -1));
        assertFalse(fovCache.isInFov(10, 10));
    }

    @Test
    public void testIsExplored_InvalidPosition() {
        assertFalse(fovCache.isExplored(-1, -1));
        assertFalse(fovCache.isExplored(10, 10));
    }

    @Test
    public void testIsExplored_DefaultValue() {
        assertFalse(fovCache.isExplored(4, 4));
    }
}