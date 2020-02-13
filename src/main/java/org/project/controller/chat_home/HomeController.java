package org.project.controller.chat_home;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import org.project.controller.MainDeligator;
import org.project.controller.chat_home.right_side.MainChatController;
import org.project.controller.messages.Message;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    MainDeligator mainDeligator; // todo assign this deligator from login page
    ArrayList<ChatRoom> chatRooms = new ArrayList<>();
    Users user; // todo assign this user from login page

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
