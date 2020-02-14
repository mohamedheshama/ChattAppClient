package org.project.controller.chat_home.left_side;

;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class UserIconController implements Initializable {

    public ChoiceBox choicebox;
    public Circle Userimage;
    public ImageView settings_icon;
    public JFXComboBox settings;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image userPicture = new Image(getClass().getResource("/org/project/images/iman.jpg").toExternalForm(), false);
        Userimage.setFill(new ImagePattern(userPicture));
        choicebox.getItems().addAll("Online", "Busy", "Away");
        choicebox.setValue("Online");




        settings_icon.setImage(new Image(getClass().getResource("/org/project/images/settings.png").toExternalForm()));
        settings.getItems().addAll("Update Profile","Logout");







    }
}
