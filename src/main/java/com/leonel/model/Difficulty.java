package com.leonel.model;

public enum Difficulty {
    EASY(0.3),    // Remove 30% of the board
    MEDIUM(0.5),  // Remove 50% of the board
    HARD(0.7);    // Remove 70% of the board

    private final double removeRatio;

    Difficulty(double removeRatio) {
        this.removeRatio = removeRatio;
    }

    public double getRemoveRatio() {
        return removeRatio;
    }
}
