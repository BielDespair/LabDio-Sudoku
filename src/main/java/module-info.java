module com.leonel.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.leonel to javafx.fxml;
    exports com.leonel;
    exports com.leonel.UI;
    opens com.leonel.UI to javafx.fxml;
}