package org.project.controller;

import org.project.controller.messages.Message;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainDeligator implements Serializable {
    Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    ServerConnectionController serverConnectionController;

    public MainDeligator() throws RemoteException, NotBoundException {
        this.serverConnectionController = new ServerConnectionController("localhost", 1260);
    }

    // Hend
    public void registerUser(Users newUser) throws RemoteException, SQLException {
        if (serverConnectionController.getServicesInterface().register(newUser)) {
            System.out.println("user registe succesfully");
        } else {
            System.out.println("user can't registe");
        }
    }

    public void updateUser(Users existUser) throws RemoteException {
        serverConnectionController.getServicesInterface().notifyUpdate(existUser);


    }

    /*
       public void sendFile( Message newMsg, RemoteInputStream remoteFileData){
        //   serverConnectionController.getServicesInterface().sendFile(newMsg,remoteFileData);

       }

       */
    public void notifyUser(Message newMsg, ChatRoom chatRoom) throws RemoteException {
        serverConnectionController.getServicesInterface().notifyUser(newMsg, chatRoom);
    }


    //End Hend
//Karima
    public Boolean checkUserLogin(String phoneNumber, String password) throws RemoteException {
        return serverConnectionController.getServicesInterface().checkUserLogin(phoneNumber, password);
    }

    public void sendMsg(Message newMsg, ChatRoom chatRoom) throws RemoteException {
        serverConnectionController.getServicesInterface().sendMessage(newMsg, chatRoom);
    }

    public Users login(String phoneNumber) throws RemoteException {
        return serverConnectionController.getServicesInterface().getUserData(phoneNumber);
    }

    public void registerClient(ClientInterface clientImp) throws RemoteException {
        System.out.println("main deligator init client");
        serverConnectionController.getServicesInterface().registerClient(clientImp);
    }

    // public void reciveMsg(Message newMsg) {

    //}

    public ChatRoom requestChatRoom(ArrayList<Users> chatroomUsers) {
        try {
            return serverConnectionController.getServicesInterface().requestChatRoom(chatroomUsers);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
//End Eman

}