package org.project.controller.chat_home.left_side;;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.project.model.dao.users.Users;
import java.net.URL;
import java.util.ResourceBundle;

public class TabPaneNotifications_Controller implements Initializable {
    public JFXTabPane tabpane;
    public BorderPane chatsTab;
    Users user;

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

    public void setUser(Users user) {
        this.user = user;
    }
    public void setChatListView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/ChatsListView.fxml"));
        BorderPane root = (BorderPane) loader.load();
        ChatListView chatListView = loader.getController();
        chatListView.setChatsListView(user);
        tab1.setContent(root);
    }

    public void setRequestListView() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/RequestsListView.fxml"));
        BorderPane root = (BorderPane) loader.load();
        RequestsListView requestsListView = loader.getController();
        requestsListView.setRequestListView(user);
        tab2.setContent(root);
    }

}

