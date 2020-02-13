package org.project.controller;

import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClientImp extends UnicastRemoteObject implements ClientInterface {
    Users user;
    MainDeligator mainDeligator;

    protected ClientImp(Users user) throws RemoteException {
        this.user = user;
    }

    @Override
    public Users getUser() {
        return user;
    }

    @Override
    public void recieveUpdateStatus(UserStatus status,int id) {
        Users user=findUserById(this.getUser().getFriends(),id);
        user.setStatus(status);
        //mainDeligator.displayUpdateStatus(this.getUser().getFriends());

    }
    public Users findUserById(ArrayList<Users> friends,int id){
        for (Users friend:friends) {
            if (friend.getId()==id)
                return friend;
        }
        return null;
    }




}
