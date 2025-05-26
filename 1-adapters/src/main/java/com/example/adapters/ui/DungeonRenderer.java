package com.example.adapters.ui;

import com.example.application.GameService;
import com.example.application.GameStateService;
import com.example.application.map.FovCache;
import com.example.application.stores.ItemStore;
import com.example.application.stores.MonsterStore;
import com.example.domain.*;
import com.example.domain.item.Consumables;
import com.example.domain.item.Item;
import com.example.domain.item.Weapon;
import com.example.domain.monster.Monster;
import com.example.domain.map.DungeonTile;

import java.awt.*;
import java.util.*;
import java.util.List;

public class DungeonRenderer implements com.example.application.DungeonRenderer {
    private Dungeon dungeon;
    private Player player;
    private MonsterStore monsterStore;
    private ItemStore itemStore;
    private NotificationContainer notificationContainer;
    private FovCache fovCache;
    private volatile boolean needsRender = true;
    private StringBuilder previousRenderBuffer;
    private static final int UPDATE_FREQUENCY_MS = 50;

    public DungeonRenderer(Dungeon dungeon, Player player, MonsterStore monsterStore, ItemStore itemStore, FovCache fovCache) {
        this.dungeon = dungeon;
        this.player = player;
        this.monsterStore = monsterStore;
        this.itemStore = itemStore;
        this.fovCache = fovCache;
        this.notificationContainer = new NotificationContainer();
        this.previousRenderBuffer = new StringBuilder();
    }


    public synchronized void renderGame() {
        // Skip rendering if nothing changed
        if (!needsRender) return;

        StringBuilder newRenderBuffer = new StringBuilder();

        // Render the dungeon tiles
        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                Position currentTile = new Position(x, y);
                DungeonTile t = fovCache.isExplored(x,y) ? dungeon.getTile(currentTile) : DungeonTile.Unknown;
                Color tileColor = t.getColour(fovCache.isInFov(x, y) ? DungeonTile.TileColorType.Primary :
                        DungeonTile.TileColorType.Secondary);
                String displayCharacter = t.getDisplayCharacter();

                // Check if player is on current tile
                if (player.getPosition().equals(currentTile)) {
                    displayCharacter = DungeonTile.Player.getDisplayCharacter();
                    tileColor = DungeonTile.Player.getColour(fovCache.isInFov(x, y) ? DungeonTile.TileColorType.Primary :
                            DungeonTile.TileColorType.Secondary);
                } else {
                    // Check if tile is in player Fov, only render monsters and item in player field of view

                    // Check if monster is on current tile
                    List<Monster> monsterList = monsterStore.getMonsters();
                    for (Monster monster : monsterList) {
                        if (monster.getPosition().equals(currentTile)) {
                            if (fovCache.isInFov(x, y)) {
                                displayCharacter = DungeonTile.Monster.getDisplayCharacter();
                            } else if (fovCache.isExplored(x, y)) {
                                displayCharacter = DungeonTile.Floor.getDisplayCharacter();
                            } else {
                                displayCharacter = DungeonTile.Unknown.getDisplayCharacter();
                                tileColor = DungeonTile.Unknown.getColour(DungeonTile.TileColorType.Primary);
                            }
                            break;
                        }
                    }
                    // Check if item is on current tile
                    List<Item> items = itemStore.getItems();
                    for (Item item : items) {
                        if (item.getPosition().equals(currentTile)) {
                            if (item instanceof Weapon) {
                                if (fovCache.isInFov(x, y)) {
                                    displayCharacter = DungeonTile.Weapon.getDisplayCharacter();
                                } else if (fovCache.isExplored(x, y)) {
                                    displayCharacter = DungeonTile.Floor.getDisplayCharacter();
                                } else {
                                    displayCharacter = DungeonTile.Unknown.getDisplayCharacter();
                                    tileColor = DungeonTile.Unknown.getColour(DungeonTile.TileColorType.Primary);
                                }
                            } else if (item instanceof Consumables) {
                                if (fovCache.isInFov(x, y)) {
                                    displayCharacter = DungeonTile.Consumable.getDisplayCharacter();
                                } else if (fovCache.isExplored(x, y)) {
                                    displayCharacter = DungeonTile.Floor.getDisplayCharacter();
                                } else {
                                    displayCharacter = DungeonTile.Unknown.getDisplayCharacter();
                                    tileColor = DungeonTile.Unknown.getColour(DungeonTile.TileColorType.Primary);
                                }
                            }
                        }
                    }
                }

                newRenderBuffer.append(getAnsiColor(tileColor)).append(displayCharacter).append("\u001B[0m"); // Reset color
            }
            newRenderBuffer.append("\n");
        }

        renderStatusBar(newRenderBuffer);
        renderNotifications(newRenderBuffer);

        // Only update if the new render is different
        if (!newRenderBuffer.toString().contentEquals(previousRenderBuffer)) {
            OutputUtils.clearConsole();
            System.out.println(newRenderBuffer);
            previousRenderBuffer.setLength(0);
            previousRenderBuffer.append(newRenderBuffer);
        }

        needsRender = false;
    }

    public void requestRender() {
        needsRender = true;
    }

    private void renderNotifications(StringBuilder newRenderBuffer) {
        newRenderBuffer.append("\n Current notifications: \n");
        if (notificationContainer.getNotifications().isEmpty()) return;

        for (String notification : notificationContainer.getNotifications()) {
            newRenderBuffer.append(" ").append(notification).append("\n");
        }
    }

    private void renderStatusBar(StringBuilder newRenderBuffer) {
        newRenderBuffer.append("\n Player Health: ").append(player.getHealth()).append(" | Attack Damage: ").append(player.getAttack()).append("\n");
    }

    @Override
    public void renderDungeon() {
        requestRender();
    }

    @Override
    public void renderGameLost() {
        OutputUtils.clearConsole();

        String gameOverText = """
         ██████   █████  ███    ███ ███████      ██████  ██    ██ ███████ ██████  
        ██       ██   ██ ████  ████ ██          ██    ██ ██    ██ ██      ██   ██ 
        ██   ███ ███████ ██ ████ ██ █████       ██    ██ ██    ██ █████   ██████  
        ██    ██ ██   ██ ██  ██  ██ ██          ██    ██  ██  ██  ██      ██   ██ 
         ██████  ██   ██ ██      ██ ███████      ██████    ████   ███████ ██   ██""";

        System.out.println(gameOverText);
        System.out.println("\nYou have lost the game!");

    }

    @Override
    public void renderWin() {
        OutputUtils.clearConsole();
        System.out.println("Congratulations! You have won the game!");
    }

    @Override
    public void renderNotification(String notification){
        notificationContainer.addNotification(notification);
        requestRender();
        // Remove notification after 5 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notificationContainer.removeNotification(notification);
                requestRender();
            }
        }, 5000);

    }



    private String getAnsiColor(Color tileColor) {
        String foregroundColor = "\u001B[37m"; // Default white text
        String backgroundColor = "\u001B[40m"; // Default black background

        // Set foreground color
        if (tileColor.equals(Color.WHITE)) foregroundColor = "\u001B[97m";
        if (tileColor.equals(Color.YELLOW)) foregroundColor = "\u001B[93m";
        if (tileColor.equals(Color.ORANGE)) foregroundColor = "\u001B[33m";
        if (tileColor.equals(Color.LIGHT_GRAY)) foregroundColor = "\u001B[90m";
        if (tileColor.equals(Color.BLACK)) foregroundColor = "\u001B[30m";
        if (tileColor.equals(Color.DARK_GRAY)) foregroundColor = "\u001B[90m";
        if (tileColor.equals(Color.RED)) foregroundColor = "\u001B[95m";

        // Set background color
        if (tileColor.equals(Color.BLACK)) backgroundColor = "\u001B[30m";
        if (tileColor.equals(Color.YELLOW)) backgroundColor = "\u001B[93m";
        if (tileColor.equals(Color.ORANGE)) backgroundColor = "\u001B[33m";
        if (tileColor.equals(Color.DARK_GRAY)) backgroundColor = "\u001B[90m";

        return backgroundColor + foregroundColor;
    }

    @Override
    public void startRenderingLoop(GameStateService gameStateService){
        Runnable renderingLoop = () -> {
            while (!gameStateService.isGameOver()) {
                renderGame();
                try {
                    Thread.sleep(UPDATE_FREQUENCY_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(renderingLoop).start();
    }
}
