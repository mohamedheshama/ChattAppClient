package org.project.controller.update_user;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.project.App;
import org.project.controller.MainDeligator;
import org.project.controller.ServicesInterface;
import org.project.controller.login.LoginController;
import org.project.model.dao.users.Gender;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class UpdateController implements Initializable, UpdateInterface {

    ServicesInterface obj;
    /* @FXML upd_phoneNumError
     private RadioButton male;
     @FXML
     private RadioButton female;
     static char gender;
 */
    Users existUser;
    public Circle upd_image;

    /*
        @FXML
        private JFXTextField upd_bio;
        @FXML
        private JFXDatePicker upd_birthday;

     */
    MainDeligator mainDeligator;
    LoginController logincontroller;
    String user_phone_number;
    byte [] imageBytes=null;
    private boolean upd_checkConfirmPass;
    @FXML
    private JFXTextField upd_phone_num;
    @FXML
    private JFXTextField upd_username;
    @FXML
    private JFXTextField upd_userEmail;
    @FXML
    private JFXTextField upd_bio;
    @FXML
    private Label upd_nameError;
    @FXML
    private Label upd_emailError;
    @FXML
    private Label upd_phoneNumError;
    @FXML
    private JFXPasswordField upd_userPassword;
    @FXML
    private JFXRadioButton upd_male;
    @FXML
    private JFXRadioButton upd_female;
    @FXML
    private JFXPasswordField upd_userPasswordConfirm;
    @FXML
    private Label upd_passError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logincontroller = new LoginController();
        upd_checkConfirmPass = true;

        try {
            mainDeligator = new MainDeligator();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        user_phone_number = logincontroller.getPhoneNumber();
        System.out.println(user_phone_number);
        try {
            existUser = mainDeligator.login(user_phone_number);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(existUser);
        upd_bio.setText(existUser.getBio());
        upd_phone_num.setText(existUser.getPhoneNumber());
        upd_userEmail.setText(existUser.getEmail());
        upd_username.setText(existUser.getName());
        upd_userPassword.setText(existUser.getPassword());
        upd_userPasswordConfirm.setText(existUser.getPassword());


        //upd_username.setText(newUser.getName());

    }

    @FXML
    public boolean userPhoneNumber() {
        if (upd_phone_num.getText().matches("^01[0125]{1}(\\-)?[^0\\D]{1}\\d{7}$")) {
            upd_phone_num.setStyle("-fx-border: 0px 0px 0px 0px ;");
            upd_phoneNumError.setText("");

        } else {
            upd_phoneNumError.setText("Phone error");


        }
        return upd_phone_num.getText().matches("^01[0125]{1}(\\-)?[^0\\D]{1}\\d{7}$");
    }

    @FXML
    public boolean validateUserName() {
        if ((!upd_username.getText().isEmpty() || upd_username.getText() != " ") && upd_username.getText().matches("^[a-zA-Z_-][ a-zA-Z0-9_-]{6,14}$")) {
            upd_username.setStyle("-fx-border: 0px 0px 0px 0px ;");
            upd_nameError.setText("");
        } else {
            upd_nameError.setText("User Name is not valid");
        }
        return (!upd_username.getText().isEmpty() || upd_username.getText() != " ") && upd_username.getText().matches("^[a-zA-Z_-][ a-zA-Z0-9_-]{6,14}$");
    }


    @FXML
    public boolean validateEmail() {
        if (!upd_userEmail.getText().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
            upd_userEmail.setStyle("-fx-border: 0px 0px 2px 0px ; -fx-border-color: #f60");
            upd_emailError.setText("E-mail Is Not Valid");
        } else {
            upd_userEmail.setStyle("-fx-border: 0px 0px 2px 0px;");
            upd_emailError.setText("");
        }
        return upd_userEmail.getText().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    public void validatePasswordMatch() {
        if (upd_userPasswordConfirm.getText().equals(upd_userPassword.getText()) && upd_userPassword.getText().matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\\\\\\\s]).{6,}")) {
            upd_userPasswordConfirm.setFocusColor(Color.GREEN);
            upd_userPassword.setUnFocusColor(Color.GREEN);
            upd_checkConfirmPass = true;
        } else {
            upd_userPasswordConfirm.setFocusColor(Color.DARKRED);
            upd_userPassword.setUnFocusColor(Color.DARKRED);
            upd_checkConfirmPass = false;
        }

    }

    public void changeUpd() throws RemoteException {
        existUser.setPhoneNumber(upd_phone_num.getText());
        existUser.setName(upd_username.getText());
        existUser.setEmail(upd_userEmail.getText());
        existUser.setPassword(upd_userPassword.getText());
        existUser.setPhoneNumber(upd_phone_num.getText());
        existUser.setGender(Gender.Female);
        existUser.setStatus(UserStatus.Available);
        existUser.setBio(upd_bio.getText());
        existUser.setDisplayPicture(imageBytes);
        if (userDataValid()) {
            System.out.println("update is done");
            mainDeligator.updateUser(existUser);
            try {
                App.setRoot("/org/project/views/chat_home/home");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean userDataValid() {
        return userPhoneNumber() && validateUserName() && validateEmail() && upd_checkConfirmPass;
    }

    public void chooseImg(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new javafx.stage.FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            System.out.println(file.getPath());
            String path = file.toURI().toString();
            Image image1 = new Image(path);
            System.out.println(image1);
            upd_image.setFill(new ImagePattern(image1));
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image1, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                javax.imageio.ImageIO.write(bufferedImage, "jpg", baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageBytes= baos.toByteArray();
        }
/*
    public  boolean checkGender(ToggleGroup genders){

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
*/


    }
}
