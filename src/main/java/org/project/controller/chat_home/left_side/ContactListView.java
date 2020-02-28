package org.project.controller.chat_home.left_side;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
import java.util.stream.Collectors;

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
        this.user = user;
        this.homeController = homeController;
        //friendsObservableList = FXCollections.observableArrayList(user.getFriends());
        friendsObservableList = FXCollections.observableArrayList(user.getFriends().stream().filter(users ->!(users.getStatus().equals(UserStatus.valueOf("Offline")))).collect(Collectors.toList()));
       if (friendsObservableList != null){
           contactList.setItems(friendsObservableList);
           contactList.setCellFactory(chatListView -> new ChatsListViewCell());
           contactList.setCellFactory(new Callback<ListView<Users>, ListCell<Users>>() {
               @Override
               public ListCell<Users> call(javafx.scene.control.ListView<Users> UserListView) {
                   return new ContactCell();
               }
           });
       }

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
    public void handle(MouseEvent event)  {
        Users friendUser = (Users) contactList.getSelectionModel().getSelectedItem();
        ArrayList<Users> chatroomUsers = new ArrayList<>();
        chatroomUsers.add(friendUser);
        chatroomUsers.add(this.user);
        try {
            currentChatRoom = requestChatRoom(chatroomUsers);
            if (currentChatRoom != null){
                boolean isChatRoomAdded = addChatRoom(currentChatRoom);
                homeController.openChatRoom(currentChatRoom , isChatRoomAdded);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("this user has an issue please contact him later");
                alert.setTitle("EROOR");
                alert.show();
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }

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

    private ChatRoom requestChatRoom(ArrayList<Users> chatroomUsers) throws RemoteException {
            return homeController.requestChatRoom(chatroomUsers);
    }

    public boolean changeUserStatus(UserStatus userStatus) throws RemoteException {
        return homeController.changeUserStatus(user , userStatus);
    }
    //start IMAN
    public void notifyUsersWithUpdateStatus(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatRooms = new ArrayList<>();
    }











































    //END IMAN
    //START SHIMAA











































































    //END SHIMAAA

    // START AMR






































    //END AMR
}
