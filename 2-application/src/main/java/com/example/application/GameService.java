package com.example.application;

import com.example.application.map.FovCache;
import com.example.application.map.FovCalculator;
import com.example.application.monsterService.MonsterService;
import com.example.application.playerService.PlayerService;
import com.example.application.stores.ItemStore;
import com.example.application.stores.MonsterStore;
import com.example.domain.*;

public class GameService {
    private FovCalculator fovCalculator;
    private Player player;
    private DungeonRenderer dungeonRenderer;
    private static ItemInteractionService itemInteractionService;
    private static PlayerService playerService;
    private static MonsterService monsterService;
    private static GameStateService gameStateService;
    public GameService(Player player, Dungeon dungeon, MonsterStore monsterStore,ItemStore itemStore, DungeonRenderer dungeonRenderer, FovCache fovCache) {
        this.player = player;
        this.dungeonRenderer = dungeonRenderer;

        this.fovCalculator = new FovCalculator(dungeon, fovCache);
        this.itemInteractionService = new ItemInteractionService(dungeonRenderer, itemStore, player);
        this.playerService = new PlayerService(player, dungeon, monsterStore, dungeonRenderer);
        this.monsterService = new MonsterService(player, dungeonRenderer, monsterStore, dungeon, itemStore);
        this.gameStateService = new GameStateService(dungeonRenderer, monsterStore);

        Position spawnPoint = dungeon.getPlayerSpawnPoint();
        this.fovCalculator.calculateFov(spawnPoint.getX_POS(), spawnPoint.getY_POS(), 5);
    }

    public void handlePlayer(Direction direction){
        Position newPos = player.getPosition().getAdjacentPosition(direction);
        playerService.playerAttackMonster(newPos);
        gameStateService.checkGameWon();
        playerService.movePlayer(direction);
        fovCalculator.calculateFov(player.getPosition().getX_POS(), player.getPosition().getY_POS(), 5);
        dungeonRenderer.renderDungeon();
    }

    public void pickUpItem(){
        itemInteractionService.pickUpItem();
    }

    public void handleMonsters(){
        monsterService.handleMonster(gameStateService);
    }


    public DungeonRenderer getDungeonRenderer() {
        return dungeonRenderer;
    }

    public static GameStateService getGameStateService() {
        return gameStateService;
    }

    public static void startMonsterMovementLoop(GameService gameService){
        Runnable monsterMovementLoop = () -> {
            while (!gameStateService.isGameOver()) {
                gameService.handleMonsters();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        new Thread(monsterMovementLoop).start();
    }
}
