package org.project.controller.chat_home.right_side;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import org.project.controller.MainDeligator;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import java.io.*;
import java.nio.charset.Charset;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

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
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            String token = bytes.toString();
            mainDeligator.sendFile(token+file.getName(), remoteFileData, chatRoom, userId);
        } catch (FileNotFoundException | RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


