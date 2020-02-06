package org.project.controller.messages;


import java.io.Serializable;

public class Message implements Serializable {

    private String name;
    private MessageType type;
    private String msg;
    // private ArrayList<Users> listUsers;
    // private ArrayList<Users> users;
    //private UserStatus status;
    private String picture;

    public Message() {
    }

    public String getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
   /* public ArrayList<Users> getUserlist() {
        return listUsers;
    }
    public void setUserlist(HashMap<String, Users> userList) {
        this.listUsers = new ArrayList<>(userList.values());
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public ArrayList<Users> getUsers() {
        return users;
    }
    public void setUsers(ArrayList<Users> users) {
        this.users = users;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    public UserStatus getStatus() {
        return status;
    }*/
}
