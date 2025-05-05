# Java Sudoku

Jogo Sudoku com Java Swing. Projeto feito para o Bootcamp Bradesco - Java Cloud Native

## Configurações do Jogo

O tamanho do tabuleiro e a dificuldade do jogo podem ser ajustados no arquivo de configuração **`GameConfig`**.

### Tamanho do Tabuleiro
O tamanho do tabuleiro é configurado pela variável `BOARD_SIZE` em **`GameConfig`**. Essa variável define o tamanho da grade do tabuleiro de Sudoku, que deve ser um quadrado perfeito (exemplo: 9 para um tabuleiro 9x9 ou 16 para um tabuleiro 16x16).

### Dificuldade
A dificuldade do jogo pode ser configurada pela variável `difficulty`, também em **`GameConfig`**. A dificuldade controla a porcentagem de espaços que serão removidos do tabuleiro para criar um desafio. As opções de dificuldade disponíveis são:

EASY: Remove 30% das células.

MEDIUM: Remove 50% das células.

HARD: Remove 70% das células.

## Diagrama de Classes

```mermaid
classDiagram
    class Board {
        -int size
        -int subBoardSize
        -Space[][] board
        +Board(int size)
        +boolean isComplete()
        +void newGame()
        +boolean fillBoard()
        +void clearBoard()
        +void removeRandomSpaces()
        +boolean isValid(int row, int col, int num)
        +boolean isValidSize(int size)
        +Space[][] getSubBoards()
        +Space[] getSpaceSubBoard(int row, int col)
        +int getSubBoardSize()
    }

    class Space {
        -Integer value
        -int expectedValue
        -int row
        -int col
        -boolean fixed
        +Space(int value, int expectedValue, int row, int col)
        +void clear()
        +boolean isCorrect()
        +int getValue()
        +void setValue(int value)
        +int getExpectedValue()
        +int getRow()
        +int getCol()
        +boolean isFixed()
        +void setFixed(boolean fixed)
    }

    class Difficulty {
        -double removeRatio
        +Difficulty(double removeRatio)
        +double getRemoveRatio()
    }

    Board "1" *-- "*" Space : contains
    Board "1" *-- "*" Difficulty : has
