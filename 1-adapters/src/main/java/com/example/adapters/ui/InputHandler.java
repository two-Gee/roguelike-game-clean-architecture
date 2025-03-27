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
                }
            }
        };
        new Thread(playerInputLoop).start();
    }

    public static String getLevelDifficulty(){
        OutputUtils.clearConsole();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the level difficulty (easy, medium, hard): ");
        String difficulty = sc.next();
        while (!difficulty.equals("easy") && !difficulty.equals("medium") && !difficulty.equals("hard")){
            System.out.println("Invalid difficulty. Please enter easy, medium, or hard: ");
            difficulty = sc.next();
        }
        return difficulty;
    }
}
