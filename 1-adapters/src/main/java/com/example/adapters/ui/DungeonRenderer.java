package com.example.adapters.ui;

import com.example.application.Factories.ItemFactory;
import com.example.application.Factories.MonsterFactory;
import com.example.application.GameService;
import com.example.application.ItemStore;
import com.example.application.MonsterStore;
import com.example.application.map.DungeonGenerator;
import com.example.domain.*;
import com.example.domain.Item.Consumables;
import com.example.domain.Item.Item;
import com.example.domain.Item.Weapon;
import com.example.domain.Monster.Monster;
import com.example.domain.map.DungeonConfiguration;
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
    private volatile boolean needsRender = true;
    private StringBuilder previousRenderBuffer;
    private static final int UPDATE_FREQUENCY_MS = 50;

    public DungeonRenderer(Dungeon dungeon, Player player, MonsterStore monsterStore, ItemStore itemStore) {
        this.dungeon = dungeon;
        this.player = player;
        this.monsterStore = monsterStore;
        this.itemStore = itemStore;
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
                DungeonTile t = dungeon.getTile(currentTile);
                Color tileColor = t.getColour();
                String displayCharacter = t.getDisplayCharacter();

                // Check if player is on current tile
                if (player.getPosition().equals(currentTile)) {
                    displayCharacter = DungeonTile.Player.getDisplayCharacter();
                    tileColor = DungeonTile.Player.getColour();
                } else {
                    // Check if monster is on current tile
                    List<Monster> monsterList = monsterStore.getMonsters();
                    for (Monster monster : monsterList) {
                        if (monster.getPosition().equals(currentTile)) {
                            displayCharacter = DungeonTile.Monster.getDisplayCharacter();
                            tileColor = DungeonTile.Monster.getColour();
                            break;
                        }
                    }
                    // Check if item is on current tile
                    List<Item> items = itemStore.getItems();
                    for (Item item : items) {
                        if (item.getPosition().equals(currentTile)) {
                            if (item instanceof Weapon) {
                                displayCharacter = DungeonTile.Weapon.getDisplayCharacter();
                                tileColor = DungeonTile.Weapon.getColour();
                            } else if (item instanceof Consumables) {
                                displayCharacter = DungeonTile.Consumable.getDisplayCharacter();
                                tileColor = DungeonTile.Consumable.getColour();
                            }
                            break;
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
            clearConsole();
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
    public void renderAttack(LivingEntity attacker, LivingEntity target) {
        String attackNotification = attacker.getName() + " attacks " + target.getName() + "! " + target.getName() + " took " + attacker.getAttack() + " damage.";
        notificationContainer.addNotification(attackNotification);

        requestRender();

        // Remove notification after 5 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notificationContainer.removeNotification(attackNotification);
                requestRender();
            }
        }, 5000);
    }

    @Override
    public void renderDeathOfMonster(Monster monster) {
        // TODO
    }

    @Override
    public void renderGameOver() {
        // TODO
    }

    @Override
    public void renderWeaponPickup(Weapon weapon) {
        String attackNotification = "Player picked up a weapon! " + weapon.getName() + " adds " + weapon.getAttack() + " attack damage.";
        notificationContainer.addNotification(attackNotification);

        requestRender();

        // Remove notification after 5 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notificationContainer.removeNotification(attackNotification);
                requestRender();
            }
        }, 5000);
    }

    @Override
    public void renderUseOfConsumable(Consumables consumable) {
        String attackNotification = "Player used a consumable! " + consumable.getName() + " heals " + consumable.getHealthPoints() + " health.";
        notificationContainer.addNotification(attackNotification);

        requestRender();

        // Remove notification after 5 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notificationContainer.removeNotification(attackNotification);
                requestRender();
            }
        }, 5000);
    }

    private String getAnsiColor(Color tileColor) {
        String foregroundColor = "\u001B[37m"; // Default white text
        String backgroundColor = "\u001B[40m"; // Default black background

        // Set foreground color
        if (tileColor.equals(Color.WHITE)) foregroundColor = "\u001B[38:5:15m";
        if (tileColor.equals(Color.BLACK)) foregroundColor = "\u001B[30m";

        // Set background color
        if (tileColor.equals(Color.WHITE)) backgroundColor = "\u001B[48:5:166m";
        if (tileColor.equals(Color.BLACK)) backgroundColor = "\u001B[48:5:0m";

        return backgroundColor + foregroundColor;
    }

    private void clearConsole() {
        try {
            // Clear console based on OS
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Or use ANSI escape codes:
                    // System.out.print("\033[H\033[2J\033[3J");
                    // System.out.flush();
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception innerException) {
            // Fallback to printing newlines
            System.out.println("\n".repeat(50));
        }
    }

    public static void main(String[] args) {

        DungeonConfiguration config = new DungeonConfiguration(70, 35, 15, 7, 5, 12, 5, 5);
        Dungeon dungeon = DungeonGenerator.generateDungeon(config);
        Map<UUID, Monster> monsters = MonsterFactory.createMonsters(config.getMaxRoomMonsters(), dungeon.getDungeonRooms());
        List<Item> items = ItemFactory.createItems(config.getMaxRoomItems(), dungeon.getDungeonRooms().values().stream().toList());
        Player player = new Player(dungeon.getRoomForPosition(dungeon.getPlayerSpawnPoint()).getRoomNumber(), dungeon.getPlayerSpawnPoint(), "Player");
        ItemStore itemStore = new ItemStore(items);
        MonsterStore monsterStore = new MonsterStore(monsters);
        DungeonRenderer rd = new DungeonRenderer(dungeon, player, monsterStore, itemStore);
        GameService gameService = new GameService(player, dungeon, monsterStore, itemStore, rd);

        rd.renderGame();

        Runnable r1 = () -> {
            while (true) {
                Scanner sc = new Scanner(System.in);
                String input = sc.next();
                if (input.equals("w")) {
                    gameService.movePlayer(Direction.NORTH);
                } else if (input.equals("s")) {
                    gameService.movePlayer(Direction.SOUTH);
                } else if (input.equals("a")) {
                    gameService.movePlayer(Direction.WEST);
                } else if (input.equals("d")) {
                    gameService.movePlayer(Direction.EAST);
                }
            }
        };

        Runnable r2 = () -> {
            while (true) {
                gameService.moveMonsters();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable renderingLoop = () -> {
            while (true) {
                rd.renderGame();
                try {
                    Thread.sleep(UPDATE_FREQUENCY_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(renderingLoop).start();
        new Thread(r1).start();
        new Thread(r2).start();
    }
}
