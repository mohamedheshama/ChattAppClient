package org.project.controller.chat_home;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.project.controller.MainDeligator;
import org.project.controller.chat_home.left_side.LeftSideController;
import org.project.controller.chat_home.right_side.MainChatController;
import org.project.controller.messages.Message;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public BorderPane borderBaneStage;
    MainDeligator mainDeligator;
    ArrayList<ChatRoom> chatRooms = new ArrayList<>();
    Users user;
    String phoneNumber;
    Stage stage;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws Exception {
        this.phoneNumber = phoneNumber;
        user = getUserData(this.phoneNumber);
        mainDeligator.setUser(user);
        System.out.println(user);
        initLeftSide();
        initRightSide();
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
        LeftSideController leftSideController = loader.getController();
        leftSideController.setTabPane(user);
        leftSideController.setMainDeligator(mainDeligator);
        borderBaneStage.setLeft(root);
    }

    public void sendMsg(Message newMsg, ChatRoom chatRoom) {
        mainDeligator.sendMsg(newMsg, chatRoom);
    }

    public void openChatRoom(ChatRoom chatRoom) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/right_side/main_chat.fxml"));
        Parent root = loader.load();
        MainChatController mainChatController = loader.getController();
        mainChatController.setChatRoom(chatRoom);
    }
    public void addChatRoom(ChatRoom chatRoom) throws IOException {
        long count = chatRooms.stream().map(value -> value.getChatRoomId()).filter(s -> s.equals(chatRoom.getChatRoomId())).count();
        if (count <= 0)
            chatRooms.add(chatRoom);
        openChatRoom(chatRoom);
    }

}