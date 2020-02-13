package org.project.controller.chat_home.left_side;;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class TabPaneNotifications_Controller implements Initializable {
    public JFXTabPane tabpane;
    @FXML
    Tab tab1;
    @FXML
    Tab tab2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView notification = new ImageView();
        notification.setImage(new Image(getClass().getResource("/org/project/images/not.png").toExternalForm()));
        notification.setFitWidth(25);
        notification.setPreserveRatio(true);

        ImageView chat = new ImageView();
        chat.setImage(new Image(getClass().getResource("/org/project/images/chat2.png").toExternalForm()));
        chat.setFitWidth(25);

        chat.setPreserveRatio(true);
        //imageView.setStyle("-fx-image: url(notification-icon-png-2.png);");



        tab2.setGraphic(notification);
        tab1.setGraphic(chat);
        tab1.setText("Chats");
        tab2.setText("Notifications");
        tab1.setStyle("-fx-font-size: 10; -fx-text-alignment:Left");
        tab2.setStyle("-fx-font-size: 10; -fx-text-alignment:Right");









    }
}
