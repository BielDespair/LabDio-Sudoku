package com.leonel.model;



import com.leonel.exceptions.InvalidBoardSizeException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private final int size;
    private final Space[][] board;

    public Board(int size) {
        if (!isValidSize(size)) {throw new InvalidBoardSizeException();}
        this.size = size;
        this.board = new Space[size][size];

        fillBoard();
    }

    public boolean fillBoard() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board[r][c] == null) {
                    List<Integer> numbers = IntStream.rangeClosed(1, size).boxed().collect(Collectors.toList());
                    Collections.shuffle(numbers);

                    for (int n : numbers) {
                        if (isValid(r, c, n)) {
                            board[r][c] = new Space(n, n, r, c);
                            if (fillBoard()) {
                                return true;
                            }
                            board[r][c] = null; // backtrack
                        }
                    }
                    return false;
                }

            }
        }
        return true;
    }

    private void clearBoard() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                board[r][c] = null;
            }
        }
    }


    private boolean isValid(int row, int col, int num) {
        // Vertical check
        for (int r = 0; r < size; r++) {
            if (board[r][col] == null) {continue;}
            if (board[r][col].getValue() == num) {return false;}
        }

        // Horizontal check
        for (int c = 0; c < size; c++) {
            if (board[row][c] == null) {continue;}
            if (board[row][c].getValue() == num) { return false;}
        }
        // sqrt(size) check

        return true;
    }


    public static boolean isValidSize(int size) {
        if (size < 1) {return false;}
        int sqrt = (int) Math.sqrt(size);
        return sqrt * sqrt == size;
    }

    public void setValue(int row, int col, int value) {
        Space space = this.board[row][col];
        space.setValue(value);
    }

    public Space[][] getBoard() {
        return board;
    }
}
