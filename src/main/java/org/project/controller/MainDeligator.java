package org.project.controller;

import com.healthmarketscience.rmiio.RemoteInputStream;
import javafx.scene.control.Alert;
import org.project.App;
import org.project.controller.chat_home.HomeController;
import org.project.controller.messages.Message;
import org.project.controller.register.RegisterController;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainDeligator implements Serializable {
    Users user;
    RegisterController registerController;
    HomeController homeController;
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

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



    public MainDeligator(){
        try {
            this.serverConnectionController = new ServerConnectionController("127.0.0.1", 1290);
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                try {
                    setverIsAlive();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            },0, 500 , TimeUnit.SECONDS);

        } catch (NotBoundException | IOException e) {
            System.out.println("dsfsdfsdfsfsdfds");
            try {
                if (homeController != null){
                    homeController.setsetverIsAlive();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Server is Down");
                    alert.setContentText("Server now is Down Please connect Later");
                    alert.show();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void setverIsAlive() throws IOException {
        try {
            serverConnectionController.getServicesInterface().setverIsAlive();
        } catch (RemoteException e) {
            homeController.setsetverIsAlive();
            e.printStackTrace();
        }
    }
    //Karima



























































    //end Karima

    // Hend

    public boolean checkIsExistUserForUpdate(Users user) throws RemoteException{
        return serverConnectionController.getServicesInterface().checkForExistUser(user);
    }

    public void registerUser(Users newUser) throws RemoteException, SQLException {
        if (serverConnectionController.getServicesInterface().register(newUser)) {
            System.out.println("user registe succesfully");
            try {

                App.setRoot("/org/project/views/login_view");

            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("user is already registe");
            alert.show();
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
        serverConnectionController.getServicesInterface().registerClient(clientImp);
    }

    public void reciveMsg(Message newMsg, ChatRoom chatRoom) throws Exception {
        homeController.reciveMsg(newMsg , chatRoom);
    }

    public ChatRoom requestChatRoom(ArrayList<Users> chatroomUsers) throws RemoteException {
            return serverConnectionController.getServicesInterface().requestChatRoom(chatroomUsers);
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
        serverConnectionController.getServicesInterface().notifyNewGroup(groupUsers,currentChatRoom);
    }

    public void recieveMsgFromAdmin(Message newMsg, Users onlineUser) throws RemoteException {
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

    public void fileSendAccepted(Users users) throws RemoteException {
        serverConnectionController.getServicesInterface().fileSendAccepted(users);
    }

    public void sendFileToReceiver() {
        homeController.sendFileToReceiver();
    }

    public void reveiveTheActualFile(String newMsg, RemoteInputStream remoteFileData) {
        homeController.reveiveTheActualFile(newMsg , remoteFileData);
    }

    public void updateCurrentUserIcon(Users currentUser) throws RemoteException{
        serverConnectionController.getServicesInterface().updateCurrentUserIcon(currentUser);
    }

    public void recieveUpdateCurrentUser(Users currentUser) throws RemoteException{
        homeController.recieveUpdateCurrentUser(currentUser);
    }


    public boolean checkUserLoggedIn(String phonenumber_input) throws RemoteException{
       return serverConnectionController.getServicesInterface().checkUserLoggedIn(phonenumber_input);
    }
}