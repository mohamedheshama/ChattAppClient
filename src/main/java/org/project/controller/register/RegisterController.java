package org.project.controller.register;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.project.controller.MainDeligator;
import org.project.controller.ServicesInterface;
import org.project.model.dao.users.Gender;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    ServicesInterface obj;
    private boolean checkConfirmPass;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private JFXTextField phone_num;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField userEmail;
    @FXML
    private Label phoneNumError;
    @FXML
    private Label nameError;
    @FXML
    private Label emailError;
    @FXML
    private JFXPasswordField userPassword;
    @FXML
    private JFXPasswordField userPasswordConfirm;
    @FXML
    private Label passError;
    Users newUser;
    MainDeligator mainDeligator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newUser = new Users();
        mainDeligator = new MainDeligator();
    }


    @FXML
    public boolean userPhoneNumber() {
        if (phone_num.getText().matches("^01[0125]{1}(\\-)?[^0\\D]{1}\\d{7}$")) {
            phone_num.setStyle("-fx-border: 0px 0px 0px 0px ;");
            phoneNumError.setText("");
            System.out.println("in");
            System.out.println(phone_num.getText());
        } else {
            phoneNumError.setText("Phone error");
            System.out.println("in else");
            System.out.println(phone_num.getText());

        }
        return phone_num.getText().matches("^01[0125]{1}(\\-)?[^0\\D]{1}\\d{7}$");
    }

    @FXML
    public boolean validateUserName() {
        if ((!username.getText().isEmpty() || username.getText() != " ") && username.getText().matches("^[a-zA-Z_-][ a-zA-Z0-9_-]{6,14}$")) {
            username.setStyle("-fx-border: 0px 0px 0px 0px ;");
            nameError.setText("");
        } else {
            nameError.setText("User Name is not valid");
        }
        return (!username.getText().isEmpty() || username.getText() != " ") && username.getText().matches("^[a-zA-Z_-][ a-zA-Z0-9_-]{6,14}$");
    }


    @FXML
    public boolean userPhoneValid() {
        if (phone_num.getText().isEmpty() || phone_num.getText() == " ") {
            phone_num.setStyle("-fx-border: 0px 0px 2px 0px ; -fx-border-color: #f60");
            phoneNumError.setText("Please Enter Your Phone Number");
        } else if (!phone_num.getText().matches("^[0-9]*$")) {
            phone_num.setStyle("-fx-border: 0px 0px 2px 0px ; -fx-border-color: #f60");
            phoneNumError.setText("This Is Not a Phone Number");
        }
        return phone_num.getText().matches("^[0-9]*");
    }

    @FXML
    public boolean validateEmail() {
        if (!userEmail.getText().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
            userEmail.setStyle("-fx-border: 0px 0px 2px 0px ; -fx-border-color: #f60");
            emailError.setText("E-mail Is Not Valid");
        } else {
            userEmail.setStyle("-fx-border: 0px 0px 2px 0px;");
            emailError.setText("");
        }
        return userEmail.getText().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    public void validatePasswordMatch(KeyEvent keyEvent) {
        if (userPasswordConfirm.getText().equals(userPassword.getText()) && userPassword.getText().matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\\\\\\\s]).{6,}")) {
            userPasswordConfirm.setFocusColor(Color.GREEN);
            userPassword.setUnFocusColor(Color.GREEN);
            checkConfirmPass = true;
        } else {
            userPasswordConfirm.setFocusColor(Color.DARKRED);
            userPassword.setUnFocusColor(Color.DARKRED);
            checkConfirmPass = false;
        }

    }



   /* public boolean passwordValid() {
        userPhoneValid();
        userNameValid();
        emailValid();
        if (!userPassword.getText().matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\\\\\\\s]).{6,}")) {
            userPassword.setStyle("-fx-border: 0px 0px 2px 0px ; -fx-border-color: #f60");
            passError.setText("Password Weak");
        }

        return userPassword.getText().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }*/


    public void register() throws RemoteException {

        if (userDataValid()) {
            newUser.setName(username.getText());
            newUser.setEmail(userEmail.getText());
            newUser.setPassword(userPassword.getText());
            newUser.setPhoneNumber(phone_num.getText());
            newUser.setGender(Gender.Female);
            newUser.setStatus(UserStatus.Available);
            mainDeligator.registerUser(newUser);

            // todo send user to deligator
        }

    }

    public boolean userDataValid() {
        return userPhoneNumber() && validateUserName() && userPhoneValid() && validateEmail() && checkConfirmPass;
    }


}