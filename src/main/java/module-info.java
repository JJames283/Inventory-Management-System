module project.c482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.c482 to javafx.fxml;
    exports project.c482;
}