package com.example.adapters.ui;

import com.example.application.Factories.MonsterFactory;
import com.example.application.GameService;
import com.example.application.MonsterStore;
import com.example.application.map.DungeonGenerator;
import com.example.domain.*;
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

    private NotificationContainer notificationContainer;

    public DungeonRenderer(Dungeon dungeon, Player player, MonsterStore monsterStore) {
        this.dungeon = dungeon;
        this.player = player;
        this.monsterStore = monsterStore;
        this.notificationContainer = new NotificationContainer();
    }

    @Override
    public void renderDungeon() {
        clearConsole();

        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                Position currentTile = new Position(x, y);
                DungeonTile t =  dungeon.getTile(currentTile);
                Color tileColor = t.getColour();
                String displayCharacter = t.getDisplayCharacter();

                // Check if player is on current tile
                if (player.getPosition().equals(currentTile)) {
                    displayCharacter = DungeonTile.Player.getDisplayCharacter();
                    tileColor = DungeonTile.Player.getColour();
                } else {
                    // Check if monster is on current tile
                    List<Monster> monsterList = monsterStore.getMonsters();
                    for(Monster monster : monsterList){
                        if(monster.getPosition().equals(currentTile)){
                            displayCharacter = DungeonTile.Monster.getDisplayCharacter();
                            tileColor = DungeonTile.Monster.getColour();
                        }
                    }
                }

                System.out.print(getAnsiColor(tileColor) + displayCharacter + "\u001B[0m"); // Reset color
            }
            System.out.println();
        }

        renderStatusBar();
        renderNotifications();

        System.out.println();
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }

    private void renderNotifications() {
        System.out.println("\n Current notifications: \n");
        if(notificationContainer.getNotifications().isEmpty()) return;

        for (String notification : notificationContainer.getNotifications()) {
            System.out.println(" " + notification + "\n");
        }
    }

    private void renderStatusBar() {
        System.out.println("\n Player Health: " + player.getHealth() + " | Attack Damage: " + player.getAttack() + "\n");
    }

    @Override
    public void renderAttack(LivingEntity attacker, LivingEntity target) {
        String attackNotification = attacker.getName() + " attacks " + target.getName() + "! " + target.getName() + " took " + attacker.getAttack() + " damage.";
        notificationContainer.addNotification(attackNotification);

        renderDungeon();
        // Remove notification after 5 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notificationContainer.removeNotification(attackNotification);
                renderDungeon();
            }
        }, 5000);

    }

    @Override
    public void renderDeathOfMonster(Monster monster) {

    }

    @Override
    public void renderGameOver() {

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

    public static void main( String[] args ) {

        DungeonConfiguration config = new DungeonConfiguration(70,35,15,7,5,12,5,5);
        Dungeon dungeon = DungeonGenerator.generateDungeon(config);
        Map<UUID, Monster> monsters = MonsterFactory.createMonsters(config.getMaxRoomMonsters(), dungeon.getDungeonRooms());
        Player player = new Player(100, 30, dungeon.getRoomForPosition(dungeon.getPlayerSpawnPoint()).getRoomNumber(), dungeon.getPlayerSpawnPoint(), "Player"); // Assuming you have a default constructor for Player

        MonsterStore monsterStore = new MonsterStore(monsters);
        DungeonRenderer rd = new DungeonRenderer(dungeon, player, monsterStore);
        GameService gameService = new GameService(player, dungeon, monsterStore,rd);

        rd.renderDungeon();

        Runnable r1 = () -> {
            while(true){
                Scanner sc = new Scanner(System.in);
                String input = sc.next();
                if(input.equals("w")) {
                    gameService.movePlayer(Direction.NORTH);
                }else if(input.equals("s")) {
                    gameService.movePlayer(Direction.SOUTH);
                }else if(input.equals("a")) {
                    gameService.movePlayer(Direction.WEST);
                }else if(input.equals("d")) {
                    gameService.movePlayer(Direction.EAST);
                }
                rd.renderDungeon();
            }
        };

        Runnable r2 =
                () -> {
                    Random rnd = new Random();
                    while(true){
                        gameService.moveMonsters();
                        try {
                            Thread.sleep(2000);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                        rd.renderDungeon();
                    }
                };

        new Thread(r2).start();
        new Thread(r1).start();
    }
}
