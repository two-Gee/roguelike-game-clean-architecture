package com.example.adapters.ui;

public class OutputUtils {
    public static void clearConsole() {
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

    public static void renderStartScreen() {
        OutputUtils.clearConsole();

        String gameStartText = """
            #############################################################################
            ▗▄▄▄ ▗▖ ▗▖▗▖  ▗▖ ▗▄▄▖▗▄▄▄▖ ▗▄▖ ▗▖  ▗▖    ▗▄▄▄ ▗▄▄▄▖ ▗▄▄▖ ▗▄▄▖▗▄▄▄▖▗▖  ▗▖▗▄▄▄▖
            ▐▌  █▐▌ ▐▌▐▛▚▖▐▌▐▌   ▐▌   ▐▌ ▐▌▐▛▚▖▐▌    ▐▌  █▐▌   ▐▌   ▐▌   ▐▌   ▐▛▚▖▐▌  █ \s
            ▐▌  █▐▌ ▐▌▐▌ ▝▜▌▐▌▝▜▌▐▛▀▀▘▐▌ ▐▌▐▌ ▝▜▌    ▐▌  █▐▛▀▀▘ ▝▀▚▖▐▌   ▐▛▀▀▘▐▌ ▝▜▌  █ \s
            ▐▙▄▄▀▝▚▄▞▘▐▌  ▐▌▝▚▄▞▘▐▙▄▄▖▝▚▄▞▘▐▌  ▐▌    ▐▙▄▄▀▐▙▄▄▖▗▄▄▞▘▝▚▄▄▖▐▙▄▄▖▐▌  ▐▌  █ \s
            #############################################################################
                            
            Welcome, adventurer!
            You have entered the depths of the dungeon, where danger lurks in every shadow. 
            Your goal is simple: eliminate all monsters in each room to survive.
            To help you on your quest, you can find weapons and consumables scattered throughout the dungeon. 
            But be careful - the monsters are not to be underestimated. Monsters will attack you if you are standing next to them.
                            
            Controls:
            - Move with [W] Up, [A] Left, [S] Down, [D] Right.
            - Pick up weapons or consumables by pressing [E] when next to them.
            To confirm the action, press [Enter].
                            
            Choose your challenge:
            1. Easy - A gentle start for new adventurers.
            2. Normal - A balanced experience.
            3. Hard - Only for the brave.
                            
            Select difficulty (1-3): """;

        System.out.println(gameStartText);
    }
}
