package org.project.controller.update_user;

import java.rmi.RemoteException;

public interface UpdateInterface {
    public boolean validateUserName();

    public boolean validateEmail();

    public void validatePasswordMatch();

    public void changeUpd() throws RemoteException;

}
