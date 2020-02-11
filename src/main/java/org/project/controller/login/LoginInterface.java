package org.project.controller.login;

import org.project.model.dao.users.Users;

public interface LoginInterface {
    public Users getUserData(String phoneNumber);
    public Boolean checkUserLogin(String phoneNumber,String password);
}
