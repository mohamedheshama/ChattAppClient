module org.project {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.project to javafx.fxml;
    opens org.project.controller.login to javafx.fxml;
    opens org.project.controller.register to javafx.fxml;
    exports org.project;

}