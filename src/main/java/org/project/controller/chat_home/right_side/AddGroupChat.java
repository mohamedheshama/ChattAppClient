package org.project.controller.chat_home.right_side;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.project.controller.chat_home.HomeController;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

public class AddGroupChat implements Initializable {
    @FXML
    private Button addBtn;
    @FXML
    private VBox contactBox;
    @FXML
    private ListView<String> contactList;
    @FXML
    private JFXTextField phoneNoTxt;
    @FXML
    private Label errorMessageLbl;
    @FXML
    private Button startbtn;
    @FXML
    private ListView<String> GroupContactList;

    HomeController homeController;
    Users user;
    ArrayList<String> onlineFriendsStrings;
    private WelcomeController welcomeController;


    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    ChatRoom chatRoom;

    private ArrayList<Users> OnlineFriends;
    private Set<String> possibleSuggestionContacts;
    private AutoCompletionBinding<String> autoCompletionBinding;
    ObservableList<String> contactListViewCell = FXCollections.observableArrayList();
    ArrayList<String> friendPhoneNumberList;
    String phoneNumber;

    public void setHomeController(HomeController homeController) throws RemoteException {
        this.homeController = homeController;
        OnlineFriends = getUserOnlineFriends();
        if (OnlineFriends != null) {
            onlineFriendsStrings = new ArrayList<>();
            for (Users onlineFriend : OnlineFriends) {

                onlineFriendsStrings.add(onlineFriend.getPhoneNumber());

            }
            //System.out.println(onlineFriendsStrings);
            //onlineFriendsStrings.remove(1);
            ArrayList<String>filteredOnlineFriendsStrings = new ArrayList<>();
            List<String> currentChatRoomPhoneNumber = getChatRoom().getUsers().stream().map(Users::getPhoneNumber).collect(Collectors.toList());
            System.out.println("nline friends are " + onlineFriendsStrings);
            for (String onlineFriendsString : onlineFriendsStrings) {
                boolean phoneExist = false;
                for (String currentChatRoomPhNum : currentChatRoomPhoneNumber) {
                    if (onlineFriendsString.equals(currentChatRoomPhNum)){
                        phoneExist = true;
                    }
                    System.out.println("current : " + currentChatRoomPhNum + "   online : " + onlineFriendsString);
                }

                if (!phoneExist){
                    filteredOnlineFriendsStrings.add(onlineFriendsString);
                    System.out.println("this phone is added  : "  + onlineFriendsString);
                }
            }
            onlineFriendsStrings.clear();
            onlineFriendsStrings.addAll(filteredOnlineFriendsStrings);

            possibleSuggestionContacts = new HashSet(onlineFriendsStrings);
            TextFields.bindAutoCompletion(phoneNoTxt, onlineFriendsStrings);
        }

    }
    public void setUser (Users user)
    {
        this.user=user;
    }

    @FXML
    private void handleAddBtn(ActionEvent event) throws RemoteException {
        phoneNumber = phoneNoTxt.getText();
        if (!phoneNumber.trim().isEmpty()) {
            if (contactListViewCell.size() == 0) {

                if (validatePhoneNo(phoneNumber) && !isPhoneNoAdded(phoneNumber)) {
                    contactListViewCell.add(phoneNumber);
                    GroupContactList.setItems(contactListViewCell);
                    errorMessageLbl.setText("");
                } else if (validatePhoneNo(phoneNumber) && isPhoneNoAdded(phoneNumber)) {
                    errorMessageLbl.setText("This contact is Already Added");
                } else {
                    errorMessageLbl.setText("Please Enter Valid contact");
                }
            } else {
                if (validatePhoneNo(phoneNumber) && !isPhoneNoAdded(phoneNumber)) {
                    GroupContactList.getItems().add(phoneNumber);
                    errorMessageLbl.setText("");
                } else if (validatePhoneNo(phoneNumber) && isPhoneNoAdded(phoneNumber)) {
                    errorMessageLbl.setText("This contact is Already Added");
                } else {
                    errorMessageLbl.setText("Please Enter Valid contact");
                }
            }
            phoneNoTxt.setText("");

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private boolean validatePhoneNo(String phoneNo) throws RemoteException {
        boolean isValidPhoneNo = false;
        if (OnlineFriends != null) {
            for (Users onlineFriend : OnlineFriends) {
                if (phoneNo.equals(onlineFriend.getPhoneNumber()) && !isValidPhoneNo) {
                    isValidPhoneNo = true;
                }
            }
        } else {
            errorMessageLbl.setText("there is no Contacts");
        }
        return isValidPhoneNo;
    }

    private boolean isPhoneNoAdded(String phoneNo) {
        boolean isAdded = false;
        for (String phoneNum : contactListViewCell) {
            if (phoneNo.equals(phoneNum) && !isAdded) {
                isAdded = true;
            }
        }

        return isAdded;
    }

    public ArrayList<Users> getUserOnlineFriends() throws RemoteException {
        //System.out.println("getUsers"+user);
        OnlineFriends = homeController.getUserOnlineFriends(user);
        //System.out.println("frined list is : " + OnlineFriends);
        return OnlineFriends;
    }

    public void handleAutoComplete(KeyEvent keyEvent) throws RemoteException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            phoneNumber = phoneNoTxt.getText();
            learnWord(phoneNumber);
            errorMessageLbl.setText("");
        }
    }

    private void learnWord(String text) throws RemoteException {
        OnlineFriends = getUserOnlineFriends();
        if (OnlineFriends != null) {
            possibleSuggestionContacts = new HashSet(onlineFriendsStrings);
            TextFields.bindAutoCompletion(phoneNoTxt, onlineFriendsStrings);
        }
        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(phoneNoTxt, onlineFriendsStrings);
    }

    public void handleStartBtn(ActionEvent event) throws Exception {
        //GroupContactList
        ArrayList<Users> groupUsers = new ArrayList<>();
        for (String item : GroupContactList.getItems()) {
            Users user = homeController.getUserData(item);
            if (user != null)
                    groupUsers.add(user);
        }
        /*
        Set setOfGroupUser = new HashSet(groupUsers);
        System.out.println("set: "+setOfGroupUser);
        ArrayList<Users> listOfGroupUser = new ArrayList<Users>(setOfGroupUser);
        System.out.println("Arr List : " + listOfGroupUser);

         */

        //chatRoom.getUsers().addAll(groupUsers);
        groupUsers.addAll(getChatRoom().getUsers());
        ChatRoom currentChatRoom = homeController.requestChatRoom(groupUsers);
        System.out.println("current chatroom is"+currentChatRoom.getChatRoomId());
        boolean isChatRoomAdded = addChatRoom(currentChatRoom);
        homeController.openChatRoom(currentChatRoom , isChatRoomAdded);
        if(isChatRoomAdded){
            homeController.notifyNewGroup(groupUsers,currentChatRoom);
        }

    }

    private boolean addChatRoom(ChatRoom chatRoom) {
        if(!isChatRoomExist(chatRoom)){
            homeController.getChatRooms().add(chatRoom);
            return true;
        }
        return false;
    }

    private boolean isChatRoomExist(ChatRoom chatRoom) {
        return homeController.getChatRooms().stream().map(ChatRoom::getChatRoomId).filter(s -> s.equals(chatRoom.getChatRoomId())).count() > 0;
    }


    public void handleCancelBtn(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/right_side/welcome_view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            System.out.println("l mfrod d5l");
            welcomeController = loader.getController();
            welcomeController.setExistUser(user);
            welcomeController.setHomeController(homeController);
            homeController.getBorderBaneStage().setCenter(root);
            homeController.getStage().setMinWidth(1300);
            homeController.getStage().setMinHeight(700);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
