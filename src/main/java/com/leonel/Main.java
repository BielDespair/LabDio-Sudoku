package com.leonel;


import com.leonel.UI.NumberTextField;
import com.leonel.UI.SudokuSector;
import com.leonel.config.GameConfig;
import com.leonel.model.Board;
import com.leonel.model.Space;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class Main extends JFrame {
    private final Board board;
    private JPanel mainPanel;


    public Main() {
        this.board = new Board(GameConfig.BOARD_SIZE);

        // Window setup
        setContentPane(mainPanel);
        setTitle("Sudoku");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(200, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        initBoard();

    }


    private void initBoard() {
        for (Space[] subBoard : board.getSubBoards()) {
            mainPanel.add(generateSection(subBoard));
        }
        this.revalidate();
        this.repaint();
    }

    private JPanel generateSection(Space[] subBoard) {
        List<NumberTextField> fields = Arrays.stream(subBoard).map(NumberTextField::new).toList();
        System.out.println(fields);
        return new SudokuSector(fields);
    }

    public static void main(String[] args) {
        new Main();
    }
}
