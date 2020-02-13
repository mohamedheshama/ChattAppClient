package org.project.controller.register;

import javafx.scene.input.KeyEvent;

public interface RegisterInterface {

    public Boolean Register();

    public boolean userPhoneNumber();

    public boolean validateUserName();

    public boolean userPhoneValid();

    public boolean validateEmail();

    public void validatePasswordMatch(KeyEvent keyEvent);
}
