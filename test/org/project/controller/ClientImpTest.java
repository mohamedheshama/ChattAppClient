package org.project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.controller.chat_home.HomeController;
import org.project.model.dao.users.Users;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class ClientImpTest {
    Users users=new Users();
    MainDeligator mainDeligator=new MainDeligator();
    HomeController homeController=new HomeController();
    ClientImp clientImpTest;

    {
        try {
            clientImpTest = new ClientImp(users,mainDeligator,homeController);
            System.out.println("created");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUser() {
        System.out.println("user"+users.getId());

assertEquals(users,clientImpTest.getUser());
    }

    @Test
    void recieveUpdateStatus() {
    }

    @Test
    void recieveMsg() {
    }

    @Test
    void recieveFile() {
    }

    @Test
    void addChatRoom() {
    }

    @Test
    void recieveContactRequest() {
    }

    @Test
    void recieveNewGroupChat() {
    }

    @Test
    void notifyUserLoggedOut() {
    }

    @Test
    void recieveServerDown() {
    }

    @Test
    void recieveServerUp() {
    }

    @Test
    void isAlive() {
    }

    @Test
    void reveiveTheActualFile() {
    }

    @Test
    void recieveUpdatedNotifications() {
    }

    @Test
    void sendFile() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void recieveMsgFromAdmin() {
    }
}