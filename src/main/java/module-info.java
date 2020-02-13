module org.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires javafx.web;
    requires java.logging;
    requires java.sql;
    requires java.rmi;

    opens org.project to javafx.fxml;
    exports org.project.controller.chat_home.right_side to com.jfoenix;
    exports org.project.controller;
    opens org.project.controller.login to javafx.fxml;
    opens org.project.controller.register to javafx.fxml;
    opens org.project.controller.chat_home.right_side to javafx.fxml;
    exports org.project;

}