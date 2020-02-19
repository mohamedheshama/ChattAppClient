package org.project.controller.chat_home.left_side;;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.project.controller.chat_home.HomeController;
import org.project.model.dao.users.Users;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

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
        notification.setFitWidth(25);
        notification.setPreserveRatio(true);

        ImageView chat = new ImageView();
        chat.setImage(new Image(getClass().getResource("/org/project/images/chat2.png").toExternalForm()));
        chat.setFitWidth(25);

        chat.setPreserveRatio(true);
        //imageView.setStyle("-fx-image: url(notification-icon-png-2.png);");
        ImageView contact = new ImageView();
        contact.setImage(new Image(getClass().getResource("/org/project/images/chat2.png").toExternalForm()));
        contact.setFitWidth(25);

        contact.setPreserveRatio(true);

        tab2.setGraphic(notification);
        tab1.setGraphic(chat);
        tab3.setGraphic(contact);
        tab1.setText("Chats");
        tab2.setText("Notifications");
        tab3.setText("Contacts");
        tab1.setStyle("-fx-font-size: 10; -fx-text-alignment:Left");
        tab2.setStyle("-fx-font-size: 10; -fx-text-alignment:Center");
        tab3.setStyle("-fx-font-size: 10; -fx-text-alignment:Right");

    }

    public void setUser(Users user, HomeController homeController) {
        this.user = user;
        this.homeController=homeController;
    }
    public void setChatListView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/ChatsListView.fxml"));
        BorderPane root = (BorderPane) loader.load();
        ChatListView chatListView = loader.getController();
        chatListView.setChatsListView(user,homeController);
        tab1.setContent(root);
    }

    public void setRequestListView() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/RequestsListView.fxml"));
        BorderPane root = (BorderPane) loader.load();
        requestsListView = loader.getController();
        requestsListView.setRequestListView(user,homeController,chatListView);
        tab2.setContent(root);
    }
   /* public void setContactListView() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/tabpanecontacts_view.fxml"));
        BorderPane root = (BorderPane) loader.load();
        ContactListView contactListVIew = loader.getController();
        contactListVIew.setContactListView(user,homeController);
        tab3.setContent(root);
    }*/


    public void recieveContactRequest(Users user)  {
       requestsListView.recieveContactRequest(user);
    }
}

