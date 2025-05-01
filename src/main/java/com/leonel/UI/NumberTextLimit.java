package com.leonel.UI;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberTextLimit extends PlainDocument {
    private final List<String> NUMBERS;

    public NumberTextLimit(int boardSize) {
        this.NUMBERS = IntStream.rangeClosed(1, boardSize)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public void insertString(final int offs, final String str, final AttributeSet a) throws BadLocationException {
        if (!NUMBERS.contains(str)) return;

        if (getLength() + str.length() <= 1) {
            super.insertString(offs, str, a);
        }
    }
}