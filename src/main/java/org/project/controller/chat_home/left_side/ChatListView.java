package org.project.controller.chat_home.left_side;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.project.controller.chat_home.HomeController;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.util.ArrayList;


public class ChatListView {
    @FXML
    public ListView chatsListView;

    private ObservableList<Users> chatsObservableList;
    Users user;
    ChatRoom chatRoom;
    HomeController homeController;

    public void displayUpdatedFriendStatus(ArrayList<Users> friends) {
        chatsObservableList = FXCollections.observableArrayList(friends);
    }

    public void setChatsListView(Users user, HomeController homeController) {
        this.user = user;
        this.homeController=homeController;
        chatsObservableList = FXCollections.observableArrayList(user.getFriends());
        chatsListView.setItems(chatsObservableList);
        chatsListView.setCellFactory(chatListView -> new ChatsListViewCell());
        chatsListView.setCellFactory(new Callback<javafx.scene.control.ListView<Users>, ListCell<Users>>() {
            @Override
            public ListCell<Users> call(javafx.scene.control.ListView<Users> UserListView) {
                return new ChatsListViewCell();
            }
        });

        System.out.println(user);
    }
    public void handle(MouseEvent event) {
        Users friendUser= (Users) chatsListView.getSelectionModel().getSelectedItem();
        chatRoom = new ChatRoom();
        chatRoom.getUsers().add(friendUser);
        chatRoom.getUsers().add(this.user);
        try {
            homeController.openChatRoom(chatRoom);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


