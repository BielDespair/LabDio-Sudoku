package com.leonel.UI;

import com.leonel.config.GameConfig;
import com.leonel.model.Space;

import javax.swing.*;


public class NumberTextField extends JTextField {
    private final Space space;


    public NumberTextField(Space space) {
        this.space = space;
        this.setVisible(true);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setDocument(new NumberTextLimit(GameConfig.BOARD_SIZE));

        if (space.isFixed()) {
            this.setEnabled(false);
            this.setText(String.valueOf(space.getExpectedValue()));
        }

        //Dimension dimension = new Dimension(20, 20);
        //this.setSize(dimension);
        //this.setPreferredSize(dimension);

    }
}
