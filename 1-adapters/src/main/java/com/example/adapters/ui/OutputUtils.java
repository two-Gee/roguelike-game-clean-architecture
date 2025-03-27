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
}
