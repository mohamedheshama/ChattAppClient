package org.project.controller.register;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.project.controller.MainDeligator;
import org.project.controller.ServicesInterface;
import org.project.model.dao.users.Gender;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class RegisterController implements Initializable {

    ServicesInterface obj;
    private boolean checkConfirmPass;
    @FXML private HBox mainPane;
    @FXML
    private ChoiceBox choicebox;
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
        List<String> collect = Arrays.asList(Locale.getAvailableLocales()).stream().map(Locale::getDisplayCountry).filter(s -> !s.isEmpty()).sorted().collect(Collectors.toList());
        ObservableList<String> AllCountries = FXCollections.observableArrayList(collect);
        choicebox.setItems(AllCountries);
        choicebox.setValue("Egypt");
    }


    @FXML
    public boolean userPhoneNumber() {
        if (phone_num.getText().matches("^01[0125]{1}(\\-)?[^0\\D]{1}\\d{7}$")) {
            phone_num.setStyle("-fx-border: 0px 0px 0px 0px ;");
            phoneNumError.setText("");
        } else {
            phoneNumError.setText("Phone error");
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

    public void validatePasswordMatch() {
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


    public void register() throws SQLException, IOException, URISyntaxException {

        if (userDataValid()) {
            newUser.setName(username.getText());
            newUser.setEmail(userEmail.getText());
            newUser.setPassword(userPassword.getText());
            newUser.setPhoneNumber(phone_num.getText());
            newUser.setGender(Gender.Female);
            newUser.setStatus(UserStatus.Available);
            final Map<String, String> env = new HashMap<>();

            URL url = getClass().getResource("/org/project/images/unknown.png");
            final String[] array = url.toString().split("!");
            final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);
            final Path path = fs.getPath(array[1]);
            //Path dest = Paths.get(url.toURI());
            if (path != null) {
                byte[] bytes = Files.readAllBytes(path);
                System.out.println("bytes are " + bytes);
                newUser.setDisplayPicture(bytes);
                System.out.println("user defalult image : " + newUser.getDisplayPicture());
            }
            choicebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

                public void changed(ObservableValue ov, Number value, Number new_value) {

                    choicebox.setValue(new_value.intValue());
                    System.out.println(new_value.intValue());
                    System.out.println("choice" + choicebox.getSelectionModel().getSelectedItem());

                }
            });
            newUser.setCountry(choicebox.getSelectionModel().getSelectedItem().toString());

            if (mainDeligator.registerUser(newUser)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/login_view.fxml"));
                Parent root = loader.load();
                getStage().setScene(new Scene(root));
            }


            // todo send user to deligator
        }

    }

    public boolean userDataValid() {
        return userPhoneNumber() && validateUserName() && userPhoneValid() && validateEmail() && checkConfirmPass;
    }
    public Stage getStage() {
        return ((Stage) mainPane.getScene().getWindow());
    }
    public void Login () throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/login_view.fxml"));
        Parent root = loader.load();
        getStage().setScene(new Scene(root));
      //  App.setRoot("/org/project/views/login_view");

    }



}