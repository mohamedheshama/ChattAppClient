package org.project.controller.chat_home.left_side;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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


public class ChatListView implements Initializable {
    @FXML
    public ListView chatsListView;

    private ObservableList<Users> chatsObservableList;
    Users user;
    ArrayList<ChatRoom> chatRooms;
    HomeController homeController;
    ChatRoom currentChatRoom;

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

    public void handle(MouseEvent event) throws IOException {
        Users friendUser = (Users) chatsListView.getSelectionModel().getSelectedItem();
        System.out.println("the user is " + friendUser);
        ArrayList<Users> chatroomUsers = new ArrayList<>();
        chatroomUsers.add(friendUser);
        chatroomUsers.add(this.user);
        currentChatRoom = requestChatRoom(chatroomUsers);
        boolean isChatRoomAdded = addChatRoom(currentChatRoom);
        if(!isChatRoomAdded){
            homeController.openChatRoom(currentChatRoom , isChatRoomAdded);
        }
        homeController.openChatRoom(currentChatRoom , isChatRoomAdded);

    }

    private boolean addChatRoom(ChatRoom chatRoom) {
        if(!isChatRoomExist(chatRoom)){
            chatRooms.add(chatRoom);
            return true;
        }
        return false;
    }

    private boolean isChatRoomExist(ChatRoom chatRoom) {
        return chatRooms.stream().map(ChatRoom::getChatRoomId).filter(s -> s.equals(chatRoom.getChatRoomId())).count() > 0;
    }

    private ChatRoom requestChatRoom(ArrayList<Users> chatroomUsers) {
        return homeController.requestChatRoom(chatroomUsers);
    }

    public boolean changeUserStatus(UserStatus userStatus) throws RemoteException {
        // todo ====> EMAN
        //  call this method after updating the userStatus and if true call notify all clients with update
        return homeController.changeUserStatus(user , userStatus);
    }
    //start IMAN
    public void notifyUsersWithUpdateStatus(){
        // todo =====> EMAN
        //  there is two ways to update users list viww with the changes
        //  first from DB (faster)
        //  Second filter List view with this user and change his image with the new status (preferred)










    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatRooms = new ArrayList<>();
    }

    // TODO ====> IMAN // accept and decline Friend request (HIGH PERIORITY)
















































    //END IMAN
    //START SHIMAA
    //TODO ADD CONTACT
    // AND SWITCH UPDATER PROFILE
    // try SORTING users based on there STATUS (ONLINE - BUSY - AWAY - OFFLINE)

















































































    //END SHIMAAA

    // START AMR






































    //END AMR
}


