package org.project.controller.chat_home.left_side;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.project.controller.chat_home.HomeController;
import org.project.controller.chat_home.right_side.NewContactController;
import org.project.model.ChatRoom;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ContactListView implements Initializable {
    @FXML
    private ListView contactList;
    private ObservableList<Users> friendsObservableList;
    private Users user;
    private  HomeController homeController;
    private NewContactController newContactController;
    ChatRoom currentChatRoom;
    ArrayList<ChatRoom> chatRooms;

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
                return new ContactCell();
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
    public void handle(MouseEvent event) throws Exception {
        Users friendUser = (Users) contactList.getSelectionModel().getSelectedItem();
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
