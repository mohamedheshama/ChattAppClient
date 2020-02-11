package org.project.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.project.App;
import org.project.controller.MainDeligator;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController implements Initializable , LoginInterface {
    String phonenumber_input;
    String password_input;
    boolean keepme = false;

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
    MainDeligator mainDeligator;
    boolean userIsExist;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainDeligator = new MainDeligator();
    }

    @FXML
    public void registerNewUser() throws IOException {
        App.setRoot("views/register_view");
    }

    @FXML
    public void view(ActionEvent actionEvent) throws IOException {
        phonenumber_input = phonenumber_Txtfield.getText();
        password_input = password_TxtField.getText();
        App.setRoot("views/home");
        userIsExist = checkUserLogin(phonenumber_input,password_input);
        if (userIsExist)
        {
            keepme = keep_me_login_Chkbox.isSelected();
            //App.setRoot("views/home");
        }
        else {
            ShowAlertError();
            phonenumber_Txtfield.clear();
            password_TxtField.clear();
            keep_me_login_Chkbox.setSelected(false);
        }

    }

    @Override
    public Users getUserData(String phoneNumber) {
        return null;
    }

    @Override
    public Boolean checkUserLogin(String phoneNumber, String password) {
        boolean userExist = false;
        try {
            userExist = mainDeligator.checkUserLogin(phoneNumber , password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return userExist;
    }
    private void ShowAlertError()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error alert");
        alert.setHeaderText("Not Allow To Login");
        alert.setContentText("incorrect phoneNumber or Password!");

        alert.showAndWait();
    }

}
