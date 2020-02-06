package org.project.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    String phonenumber_input = null;
    String password_input = null;
    boolean keepme = false;
    String password_test = "karima11";
    String phonenumber_test = "0123456789";
    @FXML
    private TextField phonenumber_Txtfield;
    @FXML
    private TextField password_TxtField;
    @FXML
    private CheckBox keep_me_login_Chkbox;
    @FXML
    private Button login_Btn;
    @FXML
    private Label new_user_register_label;
    @FXML
    private Label error_msg_password_label;
    @FXML
    private Label error_msg_phone_label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void view(ActionEvent actionEvent) {
        phonenumber_input = phonenumber_Txtfield.getText();
        password_input = password_TxtField.getText();
        System.out.println(phonenumber_input);
        System.out.println(password_input);
        keepme = keep_me_login_Chkbox.isSelected();
        System.out.println(keepme);
        if (!phonenumber_test.equals(phonenumber_input)) {
            error_msg_phone_label.setVisible(true);
            phonenumber_Txtfield.clear();
            password_TxtField.clear();

        } else if (!password_test.equals(password_input)) {
            error_msg_password_label.setVisible(true);
            password_TxtField.clear();
            //password_TxtField.setOnAction();

        } else if (phonenumber_test.equals(phonenumber_input) && password_test.equals(password_input)) {
            //phone & password is true yb2a hyd5ol 3la l home
            System.out.println("true");
        }

    }
}
