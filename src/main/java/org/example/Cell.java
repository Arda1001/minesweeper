package org.example;

public class Cell {
    private int row;
    private int col;
    private boolean isMine;
    private int adjacentMines;
    private boolean isRevealed;
    private boolean isFlagged;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.isMine = false;
        this.adjacentMines = 0;
        this.isRevealed = false;
        this.isFlagged = false;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }
}
