package com.example.domain;

import com.example.domain.item.ItemTest;
import com.example.domain.item.WeaponTest;
import com.example.domain.map.DungeonConfigurationTest;
import com.example.domain.map.DungeonRoomTest;
import com.example.domain.monster.MonsterTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for the application.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ItemTest.class,
        WeaponTest.class,
        DungeonConfigurationTest.class,
        DungeonRoomTest.class,
        MonsterTest.class,
        DungeonTest.class,
        LivingEntityTest.class,
        PlayerTest.class,
        PositionTest.class,
})
public class DomainMainTest {
    // No additional code is needed as this is a test suite definition
}