package org.example;

import java.util.Random;
import java.util.Scanner;
import java.time.Duration;
import java.time.Instant;

public class Board {
    private final int rows;
    private final int cols;
    private final int mines;
    private boolean gameOver;
    private Instant startTime;
    private final Cell[][] cells;

    public Board(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.gameOver = false;
        this.cells = new Cell[rows][cols];
        this.startTime = Instant.now();
        initialiseBoard();
        placeMines();
        calculateAdjacentMines();
    }

    private void initialiseBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    private void placeMines() {
        Random random = new Random();
        int placedMines = 0;

        while (placedMines < mines) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            if (!cells[row][col].isMine()) {
                cells[row][col].setMine(true);
                placedMines++;
            }
        }
    }

    private void calculateAdjacentMines() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!cells[row][col].isMine()) {
                    int mineCount = 0;
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            if (i != 0 || j != 0){
                                int newRow = row + i;
                                int newCol = col + j;

                                if (isValidCell(newRow, newCol) && cells[newRow][newCol].isMine()) {
                                    mineCount++;
                                }
                            }
                        }
                    }
                    cells[row][col].setAdjacentMines(mineCount);
                }
            }
        }
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public void revealCell(int row, int col) {
        // If the cell is invalid, already revealed, or flagged, return immediately
        if (!isValidCell(row, col) || cells[row][col].isRevealed() || cells[row][col].isFlagged()) {
            return;
        }

        // Reveal the current cell
        cells[row][col].setRevealed(true);

        // If the cell is a mine, end the game
        if (cells[row][col].isMine()) {
            System.out.println("Game Over! You hit a mine.");
            gameOver = true;
            printBoard();  // Print board revealing all mines
            displayStats();
            System.exit(0);
        }

        // If the cell has zero adjacent mines, recursively reveal adjacent cells
        if (cells[row][col].getAdjacentMines() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) { // Skip the current cell itself
                        revealCell(row + i, col + j);
                    }
                }
            }
        }
    }

    private double calculateClearedPercentage() {
        int totalCells = rows * cols;
        int clearedCells = 0;

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if (cells[row][col].isRevealed() && !cells[row][col].isMine()) {
                    clearedCells++;
                }
            }
        }
        return (double) clearedCells / (totalCells - mines) * 100;
    }

    private void displayStats() {
        Instant endTime = Instant.now();
        Duration elapsedTime = Duration.between(startTime, endTime);
        long minutes = elapsedTime.toMinutes();
        long seconds = elapsedTime.minusMinutes(minutes).getSeconds();

        double clearedPercentage = calculateClearedPercentage();

        System.out.printf("Time taken: %d minutes %d seconds%n", minutes, seconds);
        System.out.printf("You have cleared %.2f%% of the board.%n", clearedPercentage);
    }

    public void handleUserInput() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command (r row col to reveal, f row col to flag/unflag): ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            if (parts.length != 3) {
                System.out.println("Invalid input. Please use the format: r row col or f row col.");
                continue;
            }

            String command = parts[0];
            int row, col;
            try {
                row = Integer.parseInt(parts[1]);
                col = Integer.parseInt(parts[2]);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid row or column. Please enter numbers.");
                continue;
            }

            if (!isValidCell(row, col)) {
                System.out.println("Invalid cell coordinates. Please enter numbers within the board range.");
                continue;
            }

            if (command.equals("r")) {
                revealCell(row, col);
            }
            else if (command.equals("f")) {
                flagCell(row, col);
            }
            else {
                System.out.println("Unknown command. Please use 'r' to reveal or 'f' to flag/unflag.");
            }

            printBoard();  // Print the board without revealing mines
            if (checkWin()) {
                System.out.println("Congratulations! You've cleared all mines.");
                gameOver = true;
                printBoard();  // Print board revealing all mines
                displayStats();
                break;
            }
        }
    }

    private void flagCell(int row, int col) {
        if (isValidCell(row, col) && !cells[row][col].isRevealed()) {
            cells[row][col].setFlagged(!cells[row][col].isFlagged());
        }
    }

    private boolean checkWin() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!cells[row][col].isRevealed() && !cells[row][col].isMine()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        System.out.println("Current Board:");
        System.out.print("   "); // Print initial spaces for column numbers
        for (int col = 0; col < cols; col++) {
            System.out.printf("%2d ", col); // Print column numbers
        }
        System.out.println();

        for (int row = 0; row < rows; row++) {
            System.out.printf("%2d ", row); // Print row number
            for (int col = 0; col < cols; col++) {
                Cell cell = cells[row][col];
                if (cell.isRevealed()) {
                    if (cell.isMine()) {
                        System.out.print(" * ");
                    }
                    else {
                        System.out.print(" " + cell.getAdjacentMines() + " ");
                    }
                }
                else if (cell.isFlagged() && !gameOver) {
                    System.out.print(" F ");
                }
                else if (cell.isMine() && gameOver) {
                    System.out.print(" * ");
                }
                else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }

}


