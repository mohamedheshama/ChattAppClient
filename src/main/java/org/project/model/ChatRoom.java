package org.project.model;

import org.project.model.dao.users.Users;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatRoom implements Serializable {
    private String chatRoomId;
    private ArrayList<Users> users = new ArrayList<>();

    public ChatRoom() {
    }

    public ChatRoom(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public ArrayList<Users> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Users> users) {
        this.users = users;
    }
}
