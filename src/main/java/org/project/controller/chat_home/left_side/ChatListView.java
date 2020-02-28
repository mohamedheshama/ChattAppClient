package org.project.controller.chat_home.left_side;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.project.controller.chat_home.HomeController;
import org.project.model.ChatRoom;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class ChatListView implements Initializable {
    @FXML
    public ListView chatsListView;



    public ObservableList<ChatRoom> chatsObservableList;
    Users user;
    //ArrayList<ChatRoom> chatRooms;
    HomeController homeController;
    ChatRoom currentChatRoom;



    public void setChatsListView(Users user, HomeController homeController) {
        this.user = user;
        this.homeController = homeController;
        chatsObservableList = FXCollections.observableArrayList(user.getChatRooms().stream().filter(chatRoom -> chatRoom.getUsers().size()>2).collect(Collectors.toList()));
        chatsListView.setItems(chatsObservableList);
        chatsListView.setCellFactory(chatListView -> new ChatsListViewCell());
        chatsListView.setCellFactory(new Callback<javafx.scene.control.ListView<ChatRoom>, ListCell<ChatRoom>>() {
            @Override
            public ListCell<ChatRoom> call(javafx.scene.control.ListView<ChatRoom> UserListView) {
                return new ChatsListViewCell();
            }
        });

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

    public void handle(MouseEvent event) throws Exception {
        if (chatsListView != null){
            ChatRoom groupChatRoom = (ChatRoom) chatsListView.getSelectionModel().getSelectedItem();
            currentChatRoom = requestChatRoom(groupChatRoom.getUsers());
            System.out.println("before if opening chatroom chatroom");
            if (currentChatRoom != null) {
                boolean isChatRoomAdded = addChatRoom(currentChatRoom);
                homeController.openChatRoom(currentChatRoom, isChatRoomAdded);
                System.out.println(" after if opening chatroom");
            }
        }

    }

    private boolean addChatRoom(ChatRoom chatRoom) {
        if(!isChatRoomExist(chatRoom)){
            //chatRooms.add(chatRoom);
            homeController.getChatRooms().add(chatRoom);
            return true;
        }
        return false;
    }

    private boolean isChatRoomExist(ChatRoom chatRoom) {
        return homeController.getChatRooms().stream().map(ChatRoom::getChatRoomId).filter(s -> s.equals(chatRoom.getChatRoomId())).count() > 0;
    }

    private ChatRoom requestChatRoom(ArrayList<Users> chatroomUsers) throws IOException {
        try {
            return homeController.requestChatRoom(chatroomUsers);
        } catch (RemoteException e) {
            homeController.setsetverIsAlive();
            e.printStackTrace();
        }
        return null;
    }

    public boolean changeUserStatus(UserStatus userStatus) throws RemoteException {
       return homeController.changeUserStatus(user , userStatus);
    }
    //start IMAN
    public void notifyUsersWithUpdateStatus(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

















































    //END IMAN
    //START SHIMAA








































































    //END SHIMAAA

    // START AMR






































    //END AMR
}


