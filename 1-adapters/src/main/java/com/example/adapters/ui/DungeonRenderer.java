package com.example.adapters.ui;

import com.example.application.Factories.MonsterFactory;
import com.example.application.GameService;
import com.example.application.MonsterStore;
import com.example.application.map.DungeonGenerator;
import com.example.domain.*;
import com.example.domain.Monster.Monster;
import com.example.domain.Monster.MonsterTypes;
import com.example.domain.map.DungeonConfiguration;
import com.example.domain.map.DungeonRoom;
import com.example.domain.map.DungeonTile;

import java.awt.*;
import java.util.*;
import java.util.List;

public class DungeonRenderer {
    private Dungeon dungeon;
    private Player player;
    private MonsterStore monsterStore;

    // TODO add Fov

    public DungeonRenderer(Dungeon dungeon, Player player, MonsterStore monsterStore) {
        this.dungeon = dungeon;
        this.player = player;
        this.monsterStore = monsterStore;

    }


    public void renderDungeonToConsole() {
        clearConsole();
        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                DungeonTile t =  dungeon.getTile(new Position(x,y));
                Color fgColor = t.getColour(DungeonTile.TileColorType.Primary);
                String displayCharacter = t.getDisplayCharacter();

                if (player.getPosition().equals(new Position(x, y))) {

                    displayCharacter = " x ";
                    fgColor = Color.WHITE;
                }
                boolean monsterPresent = false;
                List<Monster> monsterList = monsterStore.getMonsters();
                for(Monster monster : monsterList){
                    if(monster.getPosition().equals(new Position(x, y))){
                       displayCharacter = " m ";
                       fgColor = Color.WHITE;
                    }
                }


                System.out.print(getAnsiColor(fgColor) + displayCharacter + "\u001B[0m"); // Reset color
            }
            System.out.print("\n");
        }
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }
//    public void renderPlayerToConsole() {
//        Position playerPosition = player.getPosition();
//
//        // Clear the previous player position
//        if (previousPlayerPosition != null) {
//            System.out.print("\033[" + (previousPlayerPosition.getyPos() + 1) + ";" + (previousPlayerPosition.getxPos() + 1) + "H");
//            DungeonTile t = dungeon.getTile(previousPlayerPosition);
//            Color fgColor = t.getColour(DungeonTile.TileColorType.Primary);
//            System.out.print(getAnsiColor(fgColor) + t.getDisplayCharacter() + "\u001B[0m"); // Reset color
//        }
//
//        // Render the player at the new position
//        System.out.print("\033[" + (playerPosition.getyPos() + 1) + ";" + (playerPosition.getxPos() + 1) + "H");
//        System.out.print("x");
//
//        // Update the previous player position
//        previousPlayerPosition = playerPosition;
//    }


    private String getAnsiColor(Color foreground) {
        String fg = "\u001B[37m"; // Default white text
        String bg = "\u001B[40m"; // Default black background

        // Set foreground color
        if (foreground.equals(Color.WHITE)) fg = "\u001B[38:5:15m";  // White text
        if (foreground.equals(Color.YELLOW)) fg = "\u001B[30m"; // Yellow text
        if (foreground.equals(Color.RED)) fg = "\\e[0;31m\t";  // Black text

        // Set background color
        if (foreground.equals(Color.WHITE)) bg = "\u001B[48:5:166m";  // White background
        if (foreground.equals(Color.YELLOW)) bg = "\u001B[48:5:0m";  // Black background

        return bg + fg; // Combine background and foreground
    }


    // Test rendering for now
    public static void main( String[] args ) throws InterruptedException {

        DungeonConfiguration config = new DungeonConfiguration(70,35,3,3,5,12,5,5);
        Dungeon dungeon = DungeonGenerator.generateDungeon(config);
        Map<UUID, Monster> monsters = MonsterFactory.createMonsters(config.getMaxRoomMonsters(), dungeon.getDungeonRooms());
        Player player = new Player(100, 30, dungeon.getRoomForPosition(dungeon.getPlayerSpawnPoint()).getRoomNumber(), dungeon.getPlayerSpawnPoint(), "player"); // Assuming you have a default constructor for Player

        MonsterStore monsterStore = new MonsterStore(monsters);
        GameService gameService = new GameService(player, dungeon, monsterStore);
        DungeonRenderer rd = new DungeonRenderer(dungeon, player, monsterStore);

        rd.renderDungeonToConsole();

        // Simulate player movement
        rd.renderDungeonToConsole();

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
                rd.renderDungeonToConsole();
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
                        rd.renderDungeonToConsole();}
                };
        new Thread(r2).start();
        new Thread(r1).start();

    }

}
