module org.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires javafx.base;
    requires javafx.web;
    requires javafx.graphics;
    requires java.sql;
    opens org.project to javafx.fxml;
    opens org.project.controller.login to javafx.fxml;
    opens org.project.controller.register to javafx.fxml;
    opens org.project.controller.chat_home.right_side;
    exports org.project;

}