package org.example;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Minesweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        do {
            int rows = 0;
            int cols = 0;
            int mines = 0;
            boolean validInput = false;

            // Validate rows and columns
            while (!validInput) {
                try {
                    System.out.print("Enter the number of rows (1-20): ");
                    rows = scanner.nextInt();

                    if (rows <= 0 || rows > 20)  {
                        System.out.println("Rows must be between 1 and 20.");
                    }
                    else {
                        validInput = true;
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter integers for rows.");
                    scanner.next(); // Clear the invalid input
                }
            }

            // Reset validInput for columns validation
            validInput = false;

            // Validate columns
            while (!validInput) {
                try {
                    System.out.print("Enter the number of columns (1-20): ");
                    cols = scanner.nextInt();

                    if (cols <= 0 || cols > 20) {
                        System.out.println("Columns must be between 1 and 20.");
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

            playAgain = askPlayAgain(scanner);

        } while (playAgain);

        System.out.println("Thanks for playing!");
        scanner.close();

    }

    private static boolean askPlayAgain(Scanner scanner) {
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