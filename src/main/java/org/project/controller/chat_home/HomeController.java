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
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable, Serializable {
    @FXML
    transient BorderPane borderBaneStage;
    transient MainDeligator mainDeligator;
    ArrayList<ChatRoom> chatRooms = new ArrayList<>();
    Users user;
    String phoneNumber;
    transient Stage stage;
    ClientImp clientImp;
    transient MainChatController mainChatController;
    transient LeftSideController leftSideController;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws Exception {
        this.phoneNumber = phoneNumber;
        user = getUserData(this.phoneNumber);
        mainDeligator.setUser(user);
        System.out.println(user);
        initClient();
        initLeftSide();
        initRightSide();
    }

    private void initClient() throws RemoteException {
        clientImp = new ClientImp(user, this);
        System.out.println("in init clint");
        mainDeligator.registerClient(clientImp);
    }

    private Users getUserData(String phoneNumber) throws RemoteException {
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
        leftSideController.setUserIcon(user);
        leftSideController.setMainDeligator(mainDeligator);
        leftSideController.setHomeController(this);
        borderBaneStage.setLeft(root);
    }

    public void sendMsg(Message newMsg, ChatRoom chatRoom) throws RemoteException {
        mainDeligator.sendMsg(newMsg, chatRoom);
    }

    public void openChatRoom(ChatRoom chatRoom) throws IOException {
        System.out.println("in open chat room");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/right_side/main_chat.fxml"));
        Parent root = loader.load();
        mainChatController = loader.getController();
        mainChatController.setmUser(user);
        mainChatController.setHomeController(this);
        mainChatController.setChatRoom(chatRoom);
        borderBaneStage.setCenter(root);
    }

    public void addChatRoom(ChatRoom chatRoom) throws IOException {
        user.getChatRooms().add(chatRoom);
        System.out.println(user.getChatRooms());
    }

    public void reciveMsg(Message newMsg) {
        mainChatController.reciveMsg(newMsg);
    }

    public ChatRoom requestChatRoom(ArrayList<Users> chatroomUsers) {
        return mainDeligator.requestChatRoom(chatroomUsers);
    }
}