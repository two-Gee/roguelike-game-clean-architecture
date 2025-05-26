package com.example.adapters.ui;

import com.example.application.GameService;
import com.example.application.GameStateService;
import com.example.domain.Direction;

import java.util.Scanner;

public class InputHandler{
    public static void startPlayerInputLoop(GameService gameService) {
        OutputUtils.clearConsole();
        Runnable playerInputLoop = () -> {
            Scanner sc = new Scanner(System.in);
            while (!gameService.getGameStateService().isGameOver()) {
                String input = sc.next();
                switch (input) {
                    case "w" -> gameService.handlePlayer(Direction.NORTH);
                    case "s" -> gameService.handlePlayer(Direction.SOUTH);
                    case "a" -> gameService.handlePlayer(Direction.WEST);
                    case "d" -> gameService.handlePlayer(Direction.EAST);
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
