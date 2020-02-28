package org.project.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.project.App;
import org.project.controller.MainDeligator;
import org.project.controller.chat_home.HomeController;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController implements Initializable , LoginInterface {


    @FXML private HBox mainPane;
    static String phonenumber_input;
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
    HomeController homecontroller;

    boolean userIsExist;


    public Stage getStage() {
        return ((Stage) mainPane.getScene().getWindow());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void registerNewUser() throws IOException {
        App.setRoot("/org/project/views/register_view");
    }

    @FXML
    public void view(ActionEvent actionEvent) {
        try {
            mainDeligator = new MainDeligator();
            phonenumber_input = phonenumber_Txtfield.getText();

            password_input = password_TxtField.getText();
            userIsExist = checkUserLogin(phonenumber_input, password_input);
            if (userIsExist) {
                keepme = keep_me_login_Chkbox.isSelected();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/home.fxml"));
                Parent root = fxmlLoader.load();
                HomeController homeController = fxmlLoader.getController();
                homeController.setMainDeligator(mainDeligator);
                homeController.setStage(getStage());
                homeController.setPhoneNumber(phonenumber_input);
                getStage().setScene(new Scene(root));
            }
            else {
                ShowAlertError();
                phonenumber_Txtfield.clear();
                password_TxtField.clear();
                keep_me_login_Chkbox.setSelected(false);
            }
        } catch (RemoteException e) {
            System.out.println("asdffffffffffffffffffffffffffffffffffffffffffff");
            try {
                App.setRoot("/org/project/views/chat_home/serverDown.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            if (mainDeligator != null){
                userExist = mainDeligator.checkUserLogin(phoneNumber , password);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return userExist;
    }

    private void ShowAlertError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error alert");
        alert.setHeaderText("Not Allow To Login");
        alert.setContentText("incorrect phoneNumber or Password!");

        alert.showAndWait();
    }

    public String getPhoneNumber() {
        return phonenumber_input;
    }

}
