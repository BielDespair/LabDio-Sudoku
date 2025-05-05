package com.leonel.UI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

import static java.awt.Color.black;

public class SudokuSector extends JPanel {

    public SudokuSector(final List<NumberTextField> textFields, int subBoardSize) {
        super(new GridLayout(subBoardSize, subBoardSize, 3, 2));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.NONE;


        Dimension dimension = new Dimension(170, 170);
        this.setPreferredSize(dimension);
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        this.setVisible(true);
        textFields.forEach(this::add);
    }

}
