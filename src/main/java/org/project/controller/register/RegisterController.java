package org.project.controller.register;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    static char gender;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private JFXTextField phone_num;
    @FXML
    private JFXTextField username;

    // @FXML
    //private JFXTextField password;
    @FXML
    private JFXTextField userEmail;
    // @FXML
    // private JFXTextField cpassword;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void userPhoneNumber() {
        if (!phone_num.getText().isEmpty() || phone_num.getText() != " ") {

            phone_num.setStyle("-fx-border: 0px 0px 0px 0px ;");
            phoneNumError.setText("");
        }

    }

    @FXML
    public void setUserName() {
        if (!username.getText().isEmpty() || username.getText() != " ") {

            username.setStyle("-fx-border: 0px 0px 0px 0px ;");
            nameError.setText("");
        }

    }

    @FXML
    public void setUserEmail() {
        if (!userEmail.getText().isEmpty() || userEmail.getText() != " ") {

            userEmail.setStyle("-fx-border: 0px 0px 0px 0px ;");
            emailError.setText("");
        }

    }

    @FXML
    public void setUserPassword() {
        confirmPasswordValid();
        if (!userPassword.getText().isEmpty() || userPassword.getText() != " ") {

            userPassword.setStyle("-fx-border: 0px 0px 0px 0px ;");
            passError.setText("");
            userPassword.setFocusColor(Color.WHITE);
            // userPasswordConfirm.setFocusColor(Color.DARKRED);

        }

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
    public boolean userNameValid() {
        userPhoneValid();


//username.getText().matches("^[a-zA-Z_-][ a-zA-Z0-9_-]{6,14}$")
        if (!username.getText().matches("^[a-zA-Z_-][ a-zA-Z0-9_-]{6,14}$")) {

            username.setStyle("-fx-border: 0px 0px 2px 0px ; -fx-border-color: #f60");
            nameError.setText("User Name Is Not Valid");
        }


        return username.getText().matches("^[a-zA-Z_-][ a-zA-Z0-9_-]{6,14}$");
    }


    public boolean emailValid() {
        userPhoneValid();
        userNameValid();
        if (!userEmail.getText().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {

            userEmail.setStyle("-fx-border: 0px 0px 2px 0px ; -fx-border-color: #f60");
            emailError.setText("E-mail Is Not Valid");
        }


        return userEmail.getText().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    public boolean passwordValid() {
        userPhoneValid();
        userNameValid();
        if (!userPassword.getText().matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\\\\\\\s]).{6,}")) {

            userPassword.setStyle("-fx-border: 0px 0px 2px 0px ; -fx-border-color: #f60");
            passError.setText("Password Weak");
        }


        return userPassword.getText().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    public void confirmPasswordValid() {
        userPhoneValid();

        if (userPasswordConfirm.getText().equals(userPassword.getText())) {
            userPasswordConfirm.setFocusColor(Color.GREEN);
            userPassword.setUnFocusColor(Color.GREEN);
        } else {
            userPasswordConfirm.setFocusColor(Color.DARKRED);
            userPassword.setUnFocusColor(Color.DARKRED);
        }


    }


    public boolean checkGender(ToggleGroup genders) {

        genders.selectedToggleProperty().addListener(
                (observable, oldToggle, newToggle) -> {
                    if (newToggle == male) {
                        gender = 'm';
                    } else if (newToggle == female) {
                        gender = 'f';
                    }
                }
        );


        return true;
    }


}
