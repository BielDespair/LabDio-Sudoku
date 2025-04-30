package com.leonel.exceptions;

public class InvalidBoardSizeException extends RuntimeException {
    public InvalidBoardSizeException() {
      super("Invalid board size: must be greater than 1 and a perfect square (e.g., 4, 9, 16).");
    }
}
