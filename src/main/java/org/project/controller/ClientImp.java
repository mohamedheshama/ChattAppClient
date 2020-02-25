package org.project.controller;

import javafx.application.Platform;
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
    MainDeligator mainDeligator;
    HomeController homeController;

    public ClientImp(Users user, MainDeligator mainDeligator,HomeController homeController) throws RemoteException {
        this.user = user;
        this.mainDeligator = mainDeligator;
        this.homeController=homeController;
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
        try {
            mainDeligator.reciveMsg(newMsg, chatRoom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void recieveFile(Message newMsg, ChatRoom chatRoom) throws RemoteException {

    }


    @Override
    public void addChatRoom(ChatRoom chatRoomExist) {
        try {
            mainDeligator.addChatRoom(chatRoomExist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void recieveContactRequest(Users user) throws RemoteException {
        user.setRequest_notifications(homeController.updateNotifications(user));
        Platform.runLater(() -> {
            try {
                System.out.println(user.getRequest_notifications());

                homeController.getLeftSideController().setTabPane(user,homeController);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void recieveNewGroupChat(Users user,ChatRoom currentRoom ) {
        //this.user.getChatRooms().add(currentRoom);
        System.out.println("inside recieve new group for"+user.getName()+"  chat rooms are:"+user.getChatRooms());
        System.out.println("inside recieve new group for"+user.getName()+"  friends are:"+user.getFriends());

        Platform.runLater(() -> {
            try {
                homeController.getLeftSideController().setTabPane(this.user,homeController);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void notifyUserLoggedOut(Users user) throws RemoteException {
        mainDeligator.notifyUserLoggedOut(user);
    }

    @Override
    public void recieveUpdatedNotifications(Users user) throws RemoteException {
        try {
            this.user.setFriends(homeController.updateFriends(user));
            this.user.setRequest_notifications(homeController.updateNotifications(this.user));
            Platform.runLater(() -> {
                try {
                    System.out.println("from updated notifications user is "+this.user.getName()+this.user.getRequest_notifications());

                    homeController.getLeftSideController().setTabPane(this.user,homeController);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
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




    @Override
    public void recieveMsgFromAdmin(Message newMsg, Users onlineUser) throws RemoteException {
        try {
            System.out.println("recieve message from admin in clintImp");
            mainDeligator.recieveMsgFromAdmin(newMsg,onlineUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
