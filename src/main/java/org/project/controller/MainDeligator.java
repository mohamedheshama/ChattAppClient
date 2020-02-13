package org.project.controller;

import org.project.model.dao.users.Users;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainDeligator {
    ServerConnectionController serverConnectionController;
    public MainDeligator() {
        try {
            this.serverConnectionController = new ServerConnectionController("localhost", 1260);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
// Hend

    //ToDo Register user  in DB (UserName & Password ) if already exists (throws UserAlreadyExistException (made in server))
//                                                  else switch on Login Scene
    public void registerUser(Users newUser) throws RemoteException {
        if (serverConnectionController.getServicesInterface().Register(newUser)) {
            System.out.println("user registe succesfully");
        } else {
            System.out.println("user can't registe");
        }
    }

//End Hend
//Karima
// ToDo establish connection using class ServerConnectionController and return all user data from server

    public Boolean checkUserLogin(String phoneNumber, String password) throws RemoteException {
        return serverConnectionController.getServicesInterface().checkUserLogin(phoneNumber , password);
    }
//end Karima
//Eman
// ToDo populate Frieds and Friend Requests ListView With data


//End Eman
// Amr
// ToDo message send and receive


//end amr
// mohamed


// end mohamed
//shaimaa


//end shaimaa

}