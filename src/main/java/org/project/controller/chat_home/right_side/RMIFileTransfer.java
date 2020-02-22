package org.project.controller.chat_home.right_side;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import org.project.controller.MainDeligator;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.rmi.RemoteException;

public class RMIFileTransfer extends Thread {
    File file;
    int userId;
    ChatRoom chatRoom;
    MainDeligator mainDeligator;
    RMIFileTransfer(File file , int userId , ChatRoom chatRoom , MainDeligator mainDeligator){
        this.file = file;
        this.userId = userId;
        this.chatRoom = chatRoom;
        this.mainDeligator = mainDeligator;
    }
    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file.getAbsolutePath());
            RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inputStream);
            mainDeligator.sendFile(file.getName(), remoteFileData, chatRoom, userId);
        } catch (FileNotFoundException | RemoteException e) {
            e.printStackTrace();
        }

    }
}


