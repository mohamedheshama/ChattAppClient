package org.project.controller.chat_home.left_side;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.project.controller.ClientInterface;
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
        System.out.println("from setChatListView" + user);
        this.user = user;
        this.homeController = homeController;
        chatsObservableList = FXCollections.observableArrayList(user.getFriends());
        chatsListView.setItems(chatsObservableList);
        chatsListView.setCellFactory(chatListView -> new ChatsListViewCell());
        chatsListView.setCellFactory(new Callback<javafx.scene.control.ListView<Users>, ListCell<Users>>() {
            @Override
            public ListCell<Users> call(javafx.scene.control.ListView<Users> UserListView) {
                return new ChatsListViewCell();
            }
        });

        // System.out.println(user);
    }
/*    public void handle(MouseEvent event) {
        System.out.println("in handle");
        Users friendUser= (Users) chatsListView.getSelectionModel().getSelectedItem();
        System.out.println(friendUser);
        chatRoom = new ChatRoom();
        chatRoom.setChatRoomId("id" + user.getPhoneNumber());
        chatRoom.getUsers().add(friendUser);
        chatRoom.getUsers().add(this.user);
        System.out.println(chatRoom.getUsers());
        try {
            homeController.openChatRoom(chatRoom);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void handle(MouseEvent event) {
        System.out.println("user ----> " + user.getChatRooms());
        Users friendUser= (Users) chatsListView.getSelectionModel().getSelectedItem();
        ArrayList<Users> chatroomUsers= new ArrayList<>();
        chatroomUsers.add(friendUser);
        System.out.println(friendUser.getChatRooms() + " this is th chat rooms in my fried");
        chatroomUsers.add(this.user);
        ChatRoom chatRoom=requestChatRoom(chatroomUsers);
    }

    private ChatRoom requestChatRoom(ArrayList<Users> chatroomUsers) {
        return homeController.requestChatRoom(chatroomUsers);
    }


}


