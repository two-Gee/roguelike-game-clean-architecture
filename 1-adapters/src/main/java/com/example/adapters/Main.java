package com.example.adapters;

import com.example.adapters.ui.DungeonRenderer;
import com.example.adapters.ui.InputHandler;
import com.example.adapters.ui.OutputUtils;
import com.example.application.factories.ItemFactory;
import com.example.application.factories.MonsterFactory;
import com.example.application.GameService;
import com.example.application.map.FovCache;
import com.example.application.stores.ItemStore;
import com.example.application.LevelSelection;
import com.example.application.stores.MonsterStore;
import com.example.application.map.DungeonGenerator;
import com.example.domain.Dungeon;
import com.example.domain.item.Item;
import com.example.domain.monster.Monster;
import com.example.domain.Player;
import com.example.domain.map.DungeonConfiguration;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        OutputUtils.renderStartScreen();
        DungeonConfiguration config = LevelSelection.selectLevel(InputHandler.getLevelDifficulty());

        GameService gameService = initGameService(config);

        gameService.getDungeonRenderer().startRenderingLoop(gameService.getGameStateService());
        InputHandler.startPlayerInputLoop(gameService);
        GameService.startMonsterMovementLoop(gameService);
    }

    public static GameService initGameService(DungeonConfiguration config) {
        Dungeon dungeon = DungeonGenerator.generateDungeon(config);

        Player player = new Player(dungeon.getRoomForPosition(dungeon.getPlayerSpawnPoint()).getRoomNumber(), dungeon.getPlayerSpawnPoint(), "Player");
        MonsterFactory monsterFactory = new MonsterFactory();
        Map<UUID, Monster> monsters = monsterFactory.createMonsters(config.getMaxRoomMonsters(), dungeon.getDungeonRooms());
        ItemFactory itemFactory = new ItemFactory();
        List<Item> items = itemFactory.createItems(config.getMaxRoomItems(), dungeon.getDungeonRooms().values().stream().toList());
        ItemStore itemStore = new ItemStore(items);
        MonsterStore monsterStore = new MonsterStore(monsters);
        FovCache fovCache = new FovCache(dungeon.getWidth(), dungeon.getHeight());

        DungeonRenderer dungeonRenderer = new DungeonRenderer(dungeon, player, monsterStore, itemStore, fovCache);
        return new GameService(player, dungeon, monsterStore, itemStore, dungeonRenderer, fovCache);
    }
}
