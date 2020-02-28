package org.project.controller.chat_home;

import org.project.controller.messages.Message;
import org.project.controller.messages.MessageType;


public class ChatListener {

//Todo estblishing connection with RMI Server

    public static void send(String msg) {
        Message createMessage = new Message();
        createMessage.setType(MessageType.USER);
        createMessage.setMsg(msg);

    }
}
