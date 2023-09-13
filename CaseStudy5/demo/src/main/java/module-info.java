module se233.chapter5.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    opens se233.chapter5 to javafx.fxml;
    exports se233.chapter5;
}