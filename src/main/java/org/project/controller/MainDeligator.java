package org.project.controller;

import org.project.controller.messages.Message;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class MainDeligator {
    ServerConnectionController serverConnectionController;

    public MainDeligator() throws RemoteException, NotBoundException {
        this.serverConnectionController = new ServerConnectionController("localhost", 1260);
    }
// Hend

    //ToDo Register user  in DB (UserName & Password ) if already exists (throws UserAlreadyExistException (made in server))
//                                                  else switch on Login Scene
    public void registerUser(Users newUser) throws RemoteException, SQLException {
        if (serverConnectionController.getServicesInterface().register(newUser)) {
            System.out.println("user registe succesfully");
        } else {
            System.out.println("user can't registe");
        }
    }

//End Hend
//Karima
// ToDo establish connection using class ServerConnectionController and return all user data from server

    public Boolean checkUserLogin(String phoneNumber, String password) throws RemoteException {
        return serverConnectionController.getServicesInterface().checkUserLogin(phoneNumber, password);
    }

    public void sendMsg(Message newMsg, ChatRoom chatRoom) {

    }

    //end Karima
//Eman
// ToDo populate Frieds and Friend Requests ListView With data
    public Users login(String phoneNumber) throws RemoteException {
        return serverConnectionController.getServicesInterface().getUserData(phoneNumber);
    }

//End Eman
// Amr
// ToDo message send and receive


//end amr
// mohamed


// end mohamed
//shaimaa


//end shaimaa

}