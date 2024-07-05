package org.example;

public class Minesweeper {
    public static void main(String[] args) {
        int rows = 10;
        int cols = 10;
        int mines = 10;

        Board board = new Board(rows, cols, mines);
        board.printBoard();

        board.handleUserInput();
    }
}