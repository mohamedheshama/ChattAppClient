package org.project.controller.chat_home.left_side;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.project.controller.chat_home.HomeController;
import org.project.model.dao.users.Users;

import java.net.URL;
import java.util.ResourceBundle;

;

public class TabPaneNotifications_Controller implements Initializable {
    public JFXTabPane tabpane;
    public BorderPane chatsTab;
    Users user;
    HomeController homeController;
    RequestsListView requestsListView;
    ChatListView chatListView;
    @FXML
    Tab tab1;
    @FXML
    Tab tab2;
    @FXML
    Tab tab3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView notification = new ImageView();
        notification.setImage(new Image(getClass().getResource("/org/project/images/not.png").toExternalForm()));
        notification.setFitWidth(20);
        notification.setPreserveRatio(true);
        VBox notificationvBox = new VBox();
        notificationvBox.prefWidth(25);
        notificationvBox.getChildren().add(notification);
        notificationvBox.setAlignment(Pos.CENTER);
        Label notificationTxt = new Label("Notifications");
        notificationTxt.setFont(Font.font("Amble CN", FontWeight.BOLD, 12));
        notificationTxt.setStyle("-fx-text-fill: #413a3a");

        notificationvBox.getChildren().add(notificationTxt);

        ImageView chat = new ImageView();
        chat.setImage(new Image(getClass().getResource("/org/project/images/chat-icon.png").toExternalForm()));
        chat.setFitWidth(20);
        VBox chatvBox = new VBox();
        chatvBox.prefWidth(25);
        chatvBox.getChildren().add(chat);
        chatvBox.setAlignment(Pos.CENTER);
        Label chatxt = new Label("Chats");
        chatxt.setFont(Font.font("Amble CN", FontWeight.BOLD, 12));
        chatxt.setStyle("-fx-text-fill: #413a3a");
        chatvBox.getChildren().add(chatxt);
        chat.setPreserveRatio(true);

        //imageView.setStyle("-fx-image: url(notification-icon-png-2.png);");
        ImageView contact = new ImageView();
        contact.setImage(new Image(getClass().getResource("/org/project/images/Contact-icon.png").toExternalForm()));
        contact.setFitWidth(20);
        VBox contactvBox = new VBox();
        chatvBox.prefWidth(25);
        contactvBox.getChildren().add(contact);
        contactvBox.setAlignment(Pos.CENTER);
        Label contactstxt = new Label("Contacts");
        contactstxt.setFont(Font.font("Amble CN", FontWeight.BOLD, 12));
        contactstxt.setStyle("-fx-text-fill: #413a3a");
        contactvBox.getChildren().add(contactstxt);

        contact.setPreserveRatio(true);

        tab2.setGraphic(notificationvBox);

        tab1.setGraphic(chatvBox);
        tab3.setGraphic(contactvBox);
        tab1.setStyle("-fx-text-alignment:Left");
        tab2.setStyle(" -fx-text-alignment:Center");
        tab3.setStyle("-fx-text-alignment:Right");
        tab3.setStyle("-fx-pref-width:105");
        tab1.setStyle("-fx-pref-width:105");
        tab2.setStyle("-fx-pref-width:105");

    }

    public void setUser(Users user, HomeController homeController) {
        this.user = user;
        this.homeController = homeController;
    }

    public void setChatListView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/ChatsListView.fxml"));
        BorderPane root = (BorderPane) loader.load();
        ChatListView chatListView = loader.getController();
        chatListView.setChatsListView(user, homeController);
        tab1.setContent(root);
    }

    public void setRequestListView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/RequestsListView.fxml"));
        BorderPane root = (BorderPane) loader.load();
        requestsListView = loader.getController();
        requestsListView.setRequestListView(user, homeController, chatListView);
        tab2.setContent(root);
    }

    public void setContactListView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/tabpanecontacts_view.fxml"));
        BorderPane root = (BorderPane) loader.load();
        ContactListView contactListVIew = loader.getController();
        contactListVIew.setContactListView(user, homeController);
        tab3.setContent(root);
    }


    public void recieveContactRequest(Users user) {
        requestsListView.recieveContactRequest(user);
    }
}

