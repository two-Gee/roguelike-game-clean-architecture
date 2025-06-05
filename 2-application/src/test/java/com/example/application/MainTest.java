package com.example.application;

import com.example.application.factories.ItemFactoryTest;
import com.example.application.factories.MonsterFactoryTest;
import com.example.application.map.DungeonGeneratorTest;
import com.example.application.map.FovCacheTest;
import com.example.application.map.FovCalculatorTest;
import com.example.application.map.PositionGeneratorTest;
import com.example.application.monsterService.MonsterMovementTest;
import com.example.application.monsterService.MonsterServiceTest;
import com.example.application.monsterService.monsterMovementStrategies.ApproachMovementStrategyTest;
import com.example.application.monsterService.monsterMovementStrategies.RandomMovementStrategyTest;
import com.example.application.monsterService.monsterMovementStrategies.StationaryMovementStrategyTest;
import com.example.application.playerService.PlayerAttackTest;
import com.example.application.playerService.PlayerMovementTest;
import com.example.application.playerService.PlayerServiceTest;
import com.example.application.stores.ItemStoreTest;
import com.example.application.stores.MonsterStoreTest;
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
        GameStateServiceTest.class,
        LevelSelectionTest.class,
        GameServiceTest.class,
        ItemInteractionServiceTest.class,
        DungeonGeneratorTest.class,
        MonsterMovementTest.class,
        StationaryMovementStrategyTest.class,
        RandomMovementStrategyTest.class,
        ApproachMovementStrategyTest.class,
        MonsterServiceTest.class
})
public class MainTest {
    // No additional code is needed as this is a test suite definition
}