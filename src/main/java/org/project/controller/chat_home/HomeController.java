package org.project.controller.chat_home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.project.controller.ClientImp;
import org.project.controller.MainDeligator;
import org.project.controller.chat_home.left_side.LeftSideController;
import org.project.controller.chat_home.right_side.MainChatController;
import org.project.controller.messages.Message;
import org.project.model.ChatRoom;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable, Serializable {


    public BorderPane getBorderBaneStage() {
        return borderBaneStage;
    }

    @FXML
    private transient BorderPane borderBaneStage;
    transient MainDeligator mainDeligator;

    public ArrayList<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(ArrayList<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    ArrayList<ChatRoom> chatRooms = new ArrayList<>();
    Users user;
    String phoneNumber;
    transient Stage stage;
    ClientImp clientImp;
    transient MainChatController mainChatController;

    public LeftSideController getLeftSideController() {
        return leftSideController;
    }

    transient LeftSideController leftSideController;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws Exception {
        this.phoneNumber = phoneNumber;
        user = getUserData(this.phoneNumber);
        mainDeligator.setUser(user);
        mainDeligator.setHomeController(this);
        System.out.println(user);
        initClient();
        initLeftSide();
        initRightSide();
    }

    private void initClient() throws RemoteException {
        clientImp = new ClientImp(user, mainDeligator , this);
        System.out.println("in init clint");
        mainDeligator.registerClient(clientImp);
    }

    public Users getUserData(String phoneNumber) throws RemoteException {
        return mainDeligator.login(phoneNumber);
    }

    public MainDeligator getMainDeligator() {
        return mainDeligator;
    }

    public void setMainDeligator(MainDeligator mainDeligator) {
        this.mainDeligator = mainDeligator;
    }

    public Stage getStage() {
        return ((Stage) borderBaneStage.getScene().getWindow());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // todo get user data
    }

    private void initRightSide() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/right_side/welcome_view.fxml"));
        Pane root = (Pane) loader.load();
        borderBaneStage.setCenter(root);
    }

    private void initLeftSide() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/Left_Chat_pane.fxml"));
        Pane root = (Pane) loader.load();
        leftSideController = loader.getController();
        System.out.println("in initleftmethod" + user);
        leftSideController.setTabPane(user, this);
        leftSideController.setUserIcon(user, this);
        leftSideController.setMainDeligator(mainDeligator);
        leftSideController.setHomeController(this);
        borderBaneStage.setLeft(root);
    }

    public void sendMsg(Message newMsg, ChatRoom chatRoom) throws RemoteException {
        mainDeligator.sendMsg(newMsg, chatRoom);
    }

    /*public boolean fileNotifyUser(Message newMsg, ChatRoom chatRoom) throws RemoteException {
        return mainDeligator.fileNotifyUser(newMsg, chatRoom);
    }*/


    public void openChatRoom(ChatRoom chatRoom, boolean isChatRoomExist) throws Exception {
        System.out.println("in open chat room");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/right_side/main_chat.fxml"));
        Parent root = loader.load();
        mainChatController = loader.getController();
        mainChatController.setmUser(user);
        mainChatController.setHomeController(this);
        mainChatController.setChatRoom(chatRoom);
        borderBaneStage.setCenter(root);
        if (!isChatRoomExist) {
            mainChatController.displayMessagesFromArrList();
        }
    }

    public void addChatRoom(ChatRoom chatRoom) throws IOException {
        user.getChatRooms().add(chatRoom);
        System.out.println("here is your chat room " + chatRoom.getChatRoomId());
    }

    public void reciveMsg(Message newMsg, ChatRoom chatRoom) throws Exception {
        mainChatController.reciveMsg(newMsg, chatRoom);
    }

   /* public void notifyrecieveFile(Message newMsg, ChatRoom chatRoom, int userSendFileId) {
     // return
              mainChatController.notifyrecieveFile(newMsg, chatRoom,userSendFileId);
    }*/

    public ChatRoom requestChatRoom(ArrayList<Users> chatroomUsers) {
        return mainDeligator.requestChatRoom(chatroomUsers);
    }

    public boolean changeUserStatus(Users user, UserStatus userStatus) throws RemoteException {
        return mainDeligator.changeUserStatus(user ,userStatus);
    }

    public ArrayList<Message> getMessagesFromArrayList() {
        return mainChatController.getMessagesFromArrayList();
    }

    public void addUsersToFriedNotifications(List<String> contactList, Users user) throws RemoteException {
        mainDeligator.addUsersToFriedNotifications(contactList , user);
    }

    public List<String> getUsersList(int userId)  throws RemoteException{
        return mainDeligator.getUsersList(userId);
    }

    public void recieveContactRequest(Users user)  {
          leftSideController.recieveContactRequest(user);
    }


    // START AMR


    //END AMR

    public boolean acceptRequest(Users currentUser, Users friend) {
        return mainDeligator.acceptRequest(currentUser,friend);
    }

    public ArrayList<Users> updateNotifications(Users currentUser){
        return mainDeligator.updateNotifications(currentUser);
    }

    public ArrayList<Users> updateFriends(Users currentUser) {
        return mainDeligator.updateFriends(currentUser);
    }


    public void updateRequestNotifications(ArrayList<Users> usersToUpdate) {
        mainDeligator.updateRequestNotifications(usersToUpdate);
    }

    public boolean declineRequest(Users currentUser, Users friend) {
        return mainDeligator.declineRequest(currentUser,friend);
    }

    public void updateStatus(Users user, UserStatus newStatus) {
        mainDeligator.updateStatus(user,newStatus);
    }

    public ArrayList<Users> getUserOnlineFriends(Users user) throws RemoteException {
        return mainDeligator.getUserOnlineFriends(user);
    }

    public void setSceneForUpdateUser() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/update_info_view.fxml"));
        Parent root = loader.load();
        borderBaneStage.setCenter(root);


    }



}