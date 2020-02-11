package org.project.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainDeligator {
    ServerConnectionController serverConnectionController;

    public MainDeligator() {
        try {
            this.serverConnectionController = new ServerConnectionController("localhost", 1234);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
// Hend

//ToDo Register user  in DB (UserName & Password ) if already exists (throws UserAlreadyExistException (made in server))
//                                                  else switch on Login Scene


//End Hend
//Karima
// ToDo establish connection using class ServerConnectionController and return all user data from server


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