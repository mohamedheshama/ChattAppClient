package org.project.controller.chat_home.left_side;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.project.controller.chat_home.HomeController;
import org.project.controller.chat_home.right_side.NewContactController;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.util.ArrayList;

public class ContactListView {
    @FXML
    private ListView contactList;
    private ObservableList<Users> friendsObservableList;
    private Users user;
    private  HomeController homeController;
    private NewContactController newContactController;

    public void displayUpdatedFriendStatus(ArrayList<Users> friends) {
        friendsObservableList = FXCollections.observableArrayList(friends);
    }



    public void setContactListView(Users user, HomeController homeController) {
        System.out.println("from setChatListView" + user);
        this.user = user;
        this.homeController = homeController;
        friendsObservableList = FXCollections.observableArrayList(user.getFriends());
        contactList.setItems(friendsObservableList);
        contactList.setCellFactory(chatListView -> new ChatsListViewCell());
        contactList.setCellFactory(new Callback<ListView<Users>, ListCell<Users>>() {
            @Override
            public ListCell<Users> call(javafx.scene.control.ListView<Users> UserListView) {
                return new ChatsListViewCell();
            }
        });
    }
    public void handleNewContact(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/right_side/addContact_view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            newContactController =loader.getController();
            newContactController.setUser(user);
            newContactController.setHomeController(homeController);
            homeController.getBorderBaneStage().setCenter(root);
            homeController.getStage().setMinWidth(1300);
            homeController.getStage().setMinHeight(700);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
