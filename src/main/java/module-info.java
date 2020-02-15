module org.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires javafx.web;
    requires java.logging;
    requires java.sql;
    requires java.rmi;
    requires java.desktop;
    requires javafx.swing;
    requires javafx.media;
    requires java.base;
    requires org.controlsfx.controls;


    opens org.project to javafx.fxml;
    exports org.project.controller.chat_home.right_side to com.jfoenix;
    exports org.project.controller;
    opens org.project.controller.login to javafx.fxml;
    opens org.project.controller.register to javafx.fxml;
    opens org.project.controller.chat_home.left_side;
    opens org.project.controller.chat_home.right_side;
    exports org.project;
    exports org.project.controller.chat_home to javafx.fxml;
    exports org.project.model.dao.users;
    exports org.project.model.dao.friends;
    exports org.project.controller.chat_home.left_side;
    opens org.project.controller.chat_home to javafx.fxml;

}