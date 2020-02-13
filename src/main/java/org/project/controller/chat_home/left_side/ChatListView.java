package org.project.controller.chat_home.left_side;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.project.model.dao.users.Users;
import java.util.ArrayList;


public class ChatListView {
    @FXML
    public ListView chatsListView;

    private ObservableList<Users> chatsObservableList;
    Users user;

    public void displayUpdatedFriendStatus(ArrayList<Users> friends) {
        chatsObservableList = FXCollections.observableArrayList(friends);
    }

    public void setChatsListView(Users user) {
        this.user = user;

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
}


