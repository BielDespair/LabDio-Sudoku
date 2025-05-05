package com.leonel;


import com.leonel.UI.NumberTextField;
import com.leonel.UI.SudokuSector;
import com.leonel.config.GameConfig;
import com.leonel.model.Board;
import com.leonel.model.Space;
import com.leonel.service.NotifierService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends JFrame {
    private final JPanel mainPanel;
    private final Board board;

    private final NotifierService notifierService;
    private final List<NumberTextField> textFields = new ArrayList<>();


    // States
    private boolean verifying = false;


    public Main() {
        this.board = new Board(GameConfig.BOARD_SIZE);
        this.notifierService = new NotifierService();
        int subBoardSize = board.getSubBoardSize();

        int cellSize = 50;
        int totalSize = GameConfig.BOARD_SIZE * cellSize;

        // Main Panel
        this.mainPanel = new JPanel(new GridLayout(subBoardSize, subBoardSize, 2, 2));
        mainPanel.setPreferredSize(new Dimension(totalSize + 10, totalSize + 10));

        // Layout
        JPanel boardWrapper = new JPanel();
        boardWrapper.setLayout(new BorderLayout());
        boardWrapper.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        boardWrapper.add(mainPanel, BorderLayout.CENTER);

        JPanel rootPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        rootPanel.add(boardWrapper, BorderLayout.CENTER);
        rootPanel.add(topButtonPanel, BorderLayout.NORTH);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);


        // Button verify
        JButton newGameButton = new JButton("Novo Jogo");
        JButton verifyButton = new JButton("Verificar");
        JButton finishButton = new JButton("Finalizar");

        topButtonPanel.add(newGameButton);
        buttonPanel.add(verifyButton);
        buttonPanel.add(finishButton);


        newGameButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente iniciar um novo jogo? O tabuleiro atual será perdido.",
                    "Confirmar Novo Jogo",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                this.newGame();
            }
        });
        verifyButton.addActionListener(e -> {
            if (!verifying) {
                textFields.forEach(NumberTextField::markIfIncorrect);
                verifyButton.setText("Restaurar");
                verifying = true;
            } else {
                textFields.forEach(NumberTextField::clearMark);
                verifyButton.setText("Verificar");
                verifying = false;
            }
        });

        finishButton.addActionListener(e -> {
            if (board.isComplete()) {
                JOptionPane.showMessageDialog(this, "Parabéns! Você venceu o jogo.");
            } else {
                JOptionPane.showMessageDialog(this, "O tabuleiro ainda não está correto!");
            }
        });


        initBoard();


        // Window setup
        setTitle("Sudoku");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(rootPanel);


        Dimension screenSize = new Dimension(totalSize + 50, totalSize + 100);
        setPreferredSize(screenSize);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        this.revalidate();
        this.repaint();

    }

    private void newGame() {
        this.textFields.clear();
        this.board.newGame();
        this.mainPanel.removeAll();

        initBoard();
        this.revalidate();
        this.repaint();
    }
    private void initBoard() {
        for (Space[] subBoard : board.getSubBoards()) {
            mainPanel.add(generateSection(subBoard));
        }
    }

    private JPanel generateSection(Space[] subBoard) {
        List<NumberTextField> fields = Arrays.stream(subBoard).map(NumberTextField::new).toList();
        this.textFields.addAll(fields); // Adicionando os itens a lista de campos na Main


        return new SudokuSector(fields, board.getSubBoardSize());

    }


    public static void main(String[] args) {
        new Main();
    }
}
