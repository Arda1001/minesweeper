package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private Scanner scanner;

    public Game() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean playAgain;

        // Print welcome message
        System.out.println("Welcome to Minesweeper!");
        System.out.println("You will be prompted to enter the number of rows, columns, and mines for the game.");

        do {
            int rows = 0;
            int cols = 0;
            int mines = 0;
            boolean validInput = false;

            // Validate rows
            while (!validInput) {
                try {
                    System.out.print("Enter the number of rows (2-20): ");
                    rows = scanner.nextInt();

                    if (rows <= 1 || rows > 20) {
                        System.out.println("Rows must be between 2 and 20.");
                    }
                    else {
                        validInput = true;
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer for rows.");
                    scanner.next(); // Clear the invalid input
                }
            }

            // Reset validInput for columns validation
            validInput = false;

            // Validate columns
            while (!validInput) {
                try {
                    System.out.print("Enter the number of columns (2-20): ");
                    cols = scanner.nextInt();

                    if (cols <= 1 || cols > 20) {
                        System.out.println("Columns must be between 2 and 20.");
                    }
                    else {
                        validInput = true;
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer for columns.");
                    scanner.next(); // Clear the invalid input
                }
            }

            // Reset validInput for mines validation
            validInput = false;

            // Validate mines
            while (!validInput) {
                try {
                    System.out.print("Enter the number of mines: ");
                    mines = scanner.nextInt();

                    if (mines <= 0 || mines >= rows * cols) {
                        System.out.println("Number of mines must be between 1 and " + (rows * cols - 1) + ".");
                    }
                    else {
                        validInput = true;
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer for the number of mines.");
                    scanner.next(); // Clear the invalid input
                }
            }

            Board board = new Board(rows, cols, mines);
            board.handleUserInput();

            playAgain = askPlayAgain();

        }
        while (playAgain);

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private boolean askPlayAgain() {
        while (true) {
            System.out.print("Do you want to play again? (yes/no): ");
            String response = scanner.next().trim().toLowerCase();

            if (response.equals("yes")) {
                return true;
            }
            else if (response.equals("no")) {
                return false;
            }
            else {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        }
    }
}

