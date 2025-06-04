package com.example;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for the application.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MonsterStoreTest.class,
        ItemStoreTest.class,
        PlayerMovementTest.class,
        PlayerAttackTest.class,
        PlayerServiceTest.class,
        FovCacheTest.class,
        FovCalculatorTest.class,
        ItemFactoryTest.class,
        MonsterFactoryTest.class,
        PositionGeneratorTest.class,

})
public class MainTest {
    // No additional code is needed as this is a test suite definition
}