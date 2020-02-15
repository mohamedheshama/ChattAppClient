package org.project.controller;

import org.project.controller.chat_home.HomeController;
import org.project.controller.messages.Message;
import org.project.model.ChatRoom;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClientImp extends UnicastRemoteObject implements ClientInterface {
    Users user;
    HomeController homeController;

    public ClientImp(Users user, HomeController homeController) throws RemoteException {
        this.user = user;
        this.homeController = homeController;
    }

    @Override
    public Users getUser() {
        return user;
    }

    @Override
    public void recieveUpdateStatus(UserStatus status, int id) {
        Users user = findUserById(this.getUser().getFriends(), id);
        user.setStatus(status);
        //mainDeligator.displayUpdateStatus(this.getUser().getFriends());

    }

    @Override
    public void recieveMsg(Message newMsg, ChatRoom chatRoom) {
        homeController.reciveMsg(newMsg, chatRoom);
    }

    @Override
    public void addChatRoom(ChatRoom chatRoomExist) {
        try {
            homeController.addChatRoom(chatRoomExist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Users findUserById(ArrayList<Users> friends, int id) {
        for (Users friend : friends) {
            if (friend.getId() == id)
                return friend;
        }
        return null;
    }




}
