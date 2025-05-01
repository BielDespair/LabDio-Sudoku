package com.leonel.model;



import com.leonel.exceptions.InvalidBoardSizeException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private final int size;
    private final int subBoardSize;
    private final Space[][] board;

    public Board(int size) {
        if (!isValidSize(size)) {throw new InvalidBoardSizeException();}
        this.size = size;
        this.subBoardSize = (int) Math.sqrt(size);
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

    public Space[][] getSubBoards() {
        Space[][] subBoards = new Space[size][size]; // n subBoards with n spaces e.g. 9x9 -> 9 subBoards with 9 spaces
        int subBoardIndex = 0;
        for (int subBoardRow = 0; subBoardRow < size; subBoardRow+=subBoardSize) {
            for (int subBoardCol = 0; subBoardCol < size; subBoardCol+= subBoardSize) {

                Space[] subBoardSpaces = new Space[subBoardSize*subBoardSize];
                int subBoardSpacesIndex = 0;
                for (int row = subBoardRow; row < subBoardRow+subBoardSize; row++) {
                    for (int col = subBoardCol; col < subBoardCol+subBoardSize; col++) {
                        subBoardSpaces[subBoardSpacesIndex++] = board[row][col];
                    }
                }
                subBoards[subBoardIndex++] = subBoardSpaces;
            }
        }
        return subBoards;
    }

    public Space[] getSpaceSubBoard(Space space) {
        return new Space[0];
    }

    public void printBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Space s = board[row][col];
                System.out.print((s != null ? s.getValue() : ".") + " ");
            }
            System.out.println();
        }
    }

    public void printSubBoards() {
        Space[][] subBoards = getSubBoards();

        for (int i = 0; i < subBoards.length; i++) {
            System.out.print("SubBoard " + i + ": ");
            for (int j = 0; j < subBoards[i].length; j++) {
                Space s = subBoards[i][j];
                System.out.print((s != null ? s.getValue() : ".") + " ");
            }
            System.out.println();
        }
    }
}
