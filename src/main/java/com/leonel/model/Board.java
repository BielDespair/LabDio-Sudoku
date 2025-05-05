package com.leonel.model;



import com.leonel.config.GameConfig;
import com.leonel.exceptions.InvalidBoardSizeException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        removeRandomSpaces();

    }

    public boolean isComplete() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream) // Lineariza a matriz. Pega cada array dentro dos arrays e aplica stream, juntando em um unico array.
                .allMatch(Space::isCorrect); // Aplica um predicate booleano (Space.isCorrect())  em cada elemento.
    }

    public void newGame() {
        clearBoard();
        fillBoard();
        removeRandomSpaces();
    }
    private boolean fillBoard() {
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
        Arrays.stream(board).forEach(col -> Arrays.fill(col, null));
    }


    private void removeRandomSpaces() {
        List<Space> candidates = Arrays.stream(board)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        Collections.shuffle(candidates);

        int toRemove = (int) (size*size * GameConfig.difficulty.getRemoveRatio());
        toRemove = Math.max(1, Math.min(toRemove, candidates.size()));

        for (int i = 0; i < toRemove; i++) {
            Space space = candidates.get(i);
            space.setFixed(false);
            space.clear();
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

        // Verificação no subBoard
        Space[] subBoard = getSpaceSubBoard(row, col);
        for (Space space : subBoard) {
            if (space != null && Objects.equals(space.getValue(), num)) {return false;}
        }

        return true;
    }


    public static boolean isValidSize(int size) {
        if (size < 1) {return false;}
        int sqrt = (int) Math.sqrt(size);
        return sqrt * sqrt == size;
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

    public Space[] getSpaceSubBoard(int row, int col) {
        int subSize = (int) Math.sqrt(size); // Ex: 9 → 3
        int startRow = (row / subSize) * subSize;
        int startCol = (col / subSize) * subSize;

        Space[] subBoardSpaces = new Space[size];

        int index = 0;
        for (int r = startRow; r < startRow + subSize; r++) {
            for (int c = startCol; c < startCol + subSize; c++) {
                subBoardSpaces[index++] = board[r][c];
            }
        }

        return subBoardSpaces;
    }

    public int getSubBoardSize() {
        return subBoardSize;
    }
}
