package org.project.controller.update_user;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.project.App;
import org.project.controller.MainDeligator;
import org.project.controller.ServicesInterface;
import org.project.controller.chat_home.HomeController;
import org.project.controller.login.LoginController;
import org.project.model.dao.users.Gender;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UpdateController implements Initializable, UpdateInterface {

    public ToggleGroup gender;
    ServicesInterface obj;
    Users existUser;
    public Circle upd_image;
    @FXML
    private HBox mainPane;
    HomeController homeController;
    MainDeligator mainDeligator;
    LoginController logincontroller;
    String user_phone_number;
    byte [] imageBytes;
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
    @FXML
    private ChoiceBox choicebox;
    @FXML
    private JFXDatePicker upd_birthDate ;
    @FXML
    private Label upd_label_name;
    String Pnumber="";
    public Stage getStage() {
        return ((Stage) mainPane.getScene().getWindow());
    }
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logincontroller = new LoginController();
        upd_checkConfirmPass = true;

        mainDeligator = new MainDeligator();
        user_phone_number = logincontroller.getPhoneNumber();
        try {
            existUser = mainDeligator.login(user_phone_number);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(existUser.getDateOfBirth() !=null){
            upd_birthDate.setValue(existUser.getDateOfBirth().toLocalDate());
        }
         if(existUser.getGender().equals("Male") ){
             upd_male.setSelected(true);
             upd_female.setSelected(false);
         }else {
             upd_male.setSelected(false);
             upd_female.setSelected(true);
         }
         if(existUser.getName()!=null){
             upd_label_name.setText(existUser.getName());
         }
         if(existUser.getBio()!=null){
             upd_bio.setText((existUser.getBio()));
         }
         if(existUser.getPhoneNumber()!=null){
             upd_phone_num.setText(existUser.getPhoneNumber());
         }
         if(existUser.getEmail()!=null){
             upd_userEmail.setText(existUser.getEmail());
         }
         if(existUser.getName()!=null){
             upd_username.setText(existUser.getName());
         }
        if(existUser.getPassword()!=null){
            upd_userPassword.setText(existUser.getPassword());
        }
        if(existUser.getPassword()!=null){
            upd_userPasswordConfirm.setText(existUser.getPassword());
        }

        try {
            if (existUser.getDisplayPicture() != null) {
                BufferedImage image = null;
                image = ImageIO.read(new ByteArrayInputStream(existUser.getDisplayPicture()));
                Image card = SwingFXUtils.toFXImage(image, null);
                upd_image.setFill(new ImagePattern(card));
                imageBytes=existUser.getDisplayPicture();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }



        //upd_birthDate.setValue(existUser.getDateOfBirth().toLocalDate());
        /*try {
            if (existUser.getDisplayPicture() != null) {
                InputStream is=new ByteArrayInputStream(existUser.getDisplayPicture());
                BufferedImage imag= ImageIO.read(is);
                Image image = SwingFXUtils.toFXImage(imag, null);
                upd_image.setClip(new ImageView(image));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }*/
        List<String> collect = Arrays.asList(Locale.getAvailableLocales()).stream().map(Locale::getDisplayCountry).filter(s -> !s.isEmpty()).sorted().collect(Collectors.toList());
        ObservableList<String> AllCountries = FXCollections.observableArrayList(collect);
        choicebox.setItems(AllCountries);
        choicebox.setValue("Egypt");
         Pnumber=existUser.getPhoneNumber();




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

        existUser.setStatus(UserStatus.Available);
        existUser.setBio(upd_bio.getText());
        existUser.setDisplayPicture(imageBytes);
        existUser.setCountry(choicebox.getSelectionModel().getSelectedItem().toString());
        if (Date.valueOf(upd_birthDate.getValue()) !=null){
            existUser.setDateOfBirth(Date.valueOf(upd_birthDate.getValue()));
        }
        gender.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                existUser.setGender(Gender.valueOf(chk.getText()));
            }
        });
        if(upd_phone_num.getText().equals(Pnumber)){

            if (userDataValid()) {
                mainDeligator.updateUser(existUser);
                mainDeligator.updateCurrentUserIcon(existUser);
                mainDeligator.updateRequestNotifications(existUser.getFriends());
                homeController.setPhoneNumber(existUser.getPhoneNumber());




            }


            else{
                System.out.println("user is not valid");
            }

        }
        else
        {
            if(!mainDeligator.checkIsExistUserForUpdate(existUser)){
                upd_phone_num.setText(Pnumber);
                Alert alertForExistUser=new Alert(Alert.AlertType.ERROR);
                alertForExistUser.setContentText("This Phone Number is Already Exist");
                alertForExistUser.show();
            }
            else
            {
                if (userDataValid()) {


                    mainDeligator.updateUser(existUser);
                   /* try {
                        homeController.setSceneForWelcomHome(existUser);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    homeController.setPhoneNumber(existUser.getPhoneNumber());


                }


                else{
                    System.out.println("user is not valid");
                }

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

        //System.out.println(size);
        if (file != null) {
            double size = file.length() / (1024 * 1024);
            if (size > 64){
                Alert bigimage=new Alert(Alert.AlertType.INFORMATION);
                bigimage.setTitle("Image too big");
                bigimage.setContentText("Image is greater than 64k, please choose another image");
                bigimage.show();
            }else{
                String path = file.toURI().toString();
                Image image1 = new Image(path);
                upd_image.setFill(new ImagePattern(image1));
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image1, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    // FileNameUtils c;
                    String filenameExtension = FilenameUtils.getExtension(file.getPath());
                    javax.imageio.ImageIO.write(bufferedImage, filenameExtension, baos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageBytes = new byte[baos.size()];
                imageBytes= baos.toByteArray();
            }

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






        // ChoiceBox c = new ChoiceBox(FXCollections.observableArrayList(st));

        // add a listener
        choicebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {

                // set the text for the label to the selected item
                choicebox.setValue(new_value.intValue());

                //l1.setText(st[new_value.intValue()] + " selected");
            }
        });




    }
}
