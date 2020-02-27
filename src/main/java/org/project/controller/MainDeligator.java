package org.project.controller;

import com.healthmarketscience.rmiio.RemoteInputStream;
import javafx.application.Platform;
import org.project.App;
import org.project.controller.chat_home.HomeController;
import org.project.controller.login.LoginController;
import org.project.controller.messages.Message;
import org.project.model.ChatRoom;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainDeligator implements Serializable {
    Users user;
    HomeController homeController;

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    ServerConnectionController serverConnectionController;



    public MainDeligator() throws RemoteException, NotBoundException {
        this.serverConnectionController = new ServerConnectionController("127.0.0.1", 1260);
    }
    //Karima



























































    //end Karima

    // Hend
    public void registerUser(Users newUser) throws RemoteException, SQLException {
        if (serverConnectionController.getServicesInterface().register(newUser)) {
            System.out.println("user registe succesfully");
        } else {
            System.out.println("user can't registe");
        }
    }

    public void sendFile( String newMsg, RemoteInputStream remoteFileData,ChatRoom chatRoom,int userId) throws IOException, NotBoundException {
        serverConnectionController.getServicesInterface().sendFile(newMsg,remoteFileData,chatRoom,userId);

    }






































    public void updateUser(Users existUser) throws RemoteException {

        serverConnectionController.getServicesInterface().notifyUpdate(existUser);


    }

    /*
       public void sendFile( Message newMsg, RemoteInputStream remoteFileData){
        //   serverConnectionController.getServicesInterface().sendFile(newMsg,remoteFileData);

       }

       */
   /* public boolean fileNotifyUser(Message newMsg, ChatRoom chatRoom) throws RemoteException {
       return serverConnectionController.getServicesInterface().fileNotifyUser(newMsg, chatRoom);
    }*/


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

    public void reciveMsg(Message newMsg, ChatRoom chatRoom) throws Exception {
        homeController.reciveMsg(newMsg , chatRoom);
    }

    public ChatRoom requestChatRoom(ArrayList<Users> chatroomUsers) {
        try {
            return serverConnectionController.getServicesInterface().requestChatRoom(chatroomUsers);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean changeUserStatus(Users user, UserStatus userStatus) throws RemoteException {
        return serverConnectionController.getServicesInterface().changeUserStatus(user , userStatus);
    }

    public void addChatRoom(ChatRoom chatRoomExist) throws IOException {
        homeController.addChatRoom(chatRoomExist);
    }


//End Eman
// AMR




























    //END AMR

    // shimaa

    public void addUsersToFriedNotifications(List<String> contactList, Users user) throws RemoteException {
        serverConnectionController.getServicesInterface().addUsersToFriedNotifications(contactList , user);
    }

    public List<String> getUsersList(int userId)  throws RemoteException{
        return serverConnectionController.getServicesInterface().getUsersList(userId);

    }
    public void recieveContactRequest(List<String> contactsToAdd, Users user) throws RemoteException{
        serverConnectionController.getServicesInterface().notifyRequestedContacts(contactsToAdd,user);
    }


    // end shimaa

    //mohamed












































    //end mohamed

    //iman
    public boolean acceptRequest(Users currentUser, Users friend) {
        try {
            return serverConnectionController.getServicesInterface().acceptRequest(currentUser, friend);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Users> updateNotifications(Users currentUser) {
        try {
            return serverConnectionController.getServicesInterface().getNotifications(currentUser);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Users> updateFriends(Users currentUser) {
        try {
            return serverConnectionController.getServicesInterface().getFriends(currentUser);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRequestNotifications(ArrayList<Users> usersToUpdate) {
        try {
            serverConnectionController.getServicesInterface().notifyUpdatedNotifications(usersToUpdate);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean declineRequest(Users currentUser, Users friend) {
        try {
            return serverConnectionController.getServicesInterface().declineRequest(currentUser, friend);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Users> getUserOnlineFriends(Users user) throws RemoteException {
        //return null;
        return serverConnectionController.getServicesInterface().getUserOnlineFriends(user);
    }


    public void updateStatus(Users user, UserStatus newStatus) {
        try {
            serverConnectionController.getServicesInterface().updateStatus(user,newStatus);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void notifyNewGroup(ArrayList<Users> groupUsers, ChatRoom currentChatRoom) throws RemoteException {
        for (Users temp:groupUsers) {
            System.out.println("inside main delegator,friends for: "+temp.getName()+" are -->"+temp.getFriends());

        }
        serverConnectionController.getServicesInterface().notifyNewGroup(groupUsers,currentChatRoom);
    }

    public void recieveMsgFromAdmin(Message newMsg, Users onlineUser) throws RemoteException {
        System.out.println("recieve message from admin in mainDeligator");
        homeController.recieveMsgFromAdmin(newMsg,onlineUser);
    }

    public boolean logout(Users user) throws RemoteException {
        return serverConnectionController.getServicesInterface().logout(user);
    }

    public void notifyUserLoggedOut(Users user) {
        homeController.notifyUserLoggedOut(user);
    }


    public void recieveServerDown() {
        homeController.recieveServerDown();
    }

    public void recieveServerUp() {

        homeController.recieveServerUp();
    }
}