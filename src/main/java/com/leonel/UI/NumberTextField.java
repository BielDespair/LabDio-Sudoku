package com.leonel.UI;

import com.leonel.config.GameConfig;
import com.leonel.model.Space;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


public class NumberTextField extends JTextField {
    private final Space space;
    private final Color defaultColor;


    public NumberTextField(Space space) {
        super();
        this.space = space;
        this.defaultColor = getForeground();
        setDocument(new NumberTextLimit(GameConfig.BOARD_SIZE));

        //Layout
        setHorizontalAlignment(CENTER);
        setPreferredSize(new Dimension(50, 50));
        setFont(new Font("Arial", Font.PLAIN, 20));
        setBorder(BorderFactory.createLineBorder(Color.RED, 0, true));

        if (space.isFixed()) {
            this.setEnabled(false);
            this.setText(String.valueOf(space.getExpectedValue()));
        }

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {updateSpace();}

            @Override
            public void removeUpdate(DocumentEvent e) {updateSpace();}

            @Override
            public void changedUpdate(DocumentEvent e) {updateSpace();}

            private void updateSpace() {
                if (getText().isEmpty()) {
                    space.clear();
                    return;
                }
                space.setValue(Integer.parseInt(getText()));

            }
        });

    }

    public void markIfIncorrect() {
        if (space.isFixed()) {return;}
        this.setFocusable(false);
        if(!space.isCorrect()) {
            this.setForeground(Color.RED);
        }
        else {
            this.setForeground(this.defaultColor);
        }
    }

    public void clearMark() {
        if (space.isFixed()) {return;}
        this.setFocusable(true);
        this.setForeground(this.defaultColor);
    }
}
