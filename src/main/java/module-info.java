module com.leonel.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.leonel.sudoku to javafx.fxml;
    exports com.leonel.sudoku;
    exports com.leonel.sudoku.UI;
    opens com.leonel.sudoku.UI to javafx.fxml;
}