package org.project.controller.chat_home.left_side;;

public class User {


    private String name;
    private Status gender;

    enum Status {
        Online,
        Offline
    }

    public User(String name, Status gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getGender() {
        return gender;
    }

    public void setGender(Status gender) {
        this.gender = gender;
    }
}

