package com.grupa.projektowa.labirynt;

import java.util.Arrays;

public class UploadLabyrinth {
    public int columns;
    public int rows;
    public int[][] board;

    public UploadLabyrinth() {}

    public UploadLabyrinth(int columns, int rows, int[][] board) {
        this.columns = columns;
        this.rows = rows;
        this.board = board;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

}
