package com.leonel.model;

import java.util.Objects;

public class Space {
    private Integer value;
    private final int expectedValue;
    private final int row;
    private final int col;
    private boolean fixed = true;

    public Space(int value, int expectedValue, int row, int col) {
        this.value = value;
        this.expectedValue = expectedValue;
        this.row = row;
        this.col = col;
    }

    public void clear() {
        this.value = null;
    }

    public boolean isCorrect() {
        return Objects.equals(value, expectedValue);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getExpectedValue() {
        return expectedValue;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }


}

