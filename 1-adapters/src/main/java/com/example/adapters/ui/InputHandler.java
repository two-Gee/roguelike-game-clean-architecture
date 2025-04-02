package com.example.adapters.ui;

import com.example.application.GameService;
import com.example.domain.Direction;
import com.example.domain.Dungeon;

import java.util.Scanner;

public class InputHandler{
    public static void startPlayerInputLoop(GameService gameService){
        OutputUtils.clearConsole();
        Runnable playerInputLoop = () -> {
            Scanner sc = new Scanner(System.in);
            while (!gameService.isGameOver()) {
                String input = sc.next();
                switch (input) {
                    case "w" -> gameService.movePlayer(Direction.NORTH);
                    case "s" -> gameService.movePlayer(Direction.SOUTH);
                    case "a" -> gameService.movePlayer(Direction.WEST);
                    case "d" -> gameService.movePlayer(Direction.EAST);
                    case "e" -> gameService.pickUpItem();
                }
            }
        };
        new Thread(playerInputLoop).start();
    }

    public static String getLevelDifficulty(){
        Scanner sc = new Scanner(System.in);
        String difficulty = sc.next();
        while (!difficulty.equals("1") && !difficulty.equals("2") && !difficulty.equals("3")){
            System.out.println("Invalid difficulty. Please enter 1, 2, or 3: ");
            difficulty = sc.next();
        }
        return difficulty;
    }
}
