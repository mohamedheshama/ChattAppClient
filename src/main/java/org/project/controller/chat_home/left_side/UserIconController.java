package org.project.controller.chat_home.left_side;

;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.project.model.dao.users.Users;

import java.net.URL;
import java.util.ResourceBundle;

public class UserIconController {

    public ChoiceBox choicebox;
    public Circle Userimage;
    public ImageView settings_icon;
    public JFXComboBox settings;
    public Users user;
    public Label userName;

    public void setUser(Users user) {
        this.user = user;
        Image userPicture = new Image(getClass().getResource("/org/project/images/iman.jpg").toExternalForm(), false);
        Userimage.setFill(new ImagePattern(userPicture));
        choicebox.getItems().addAll("Online", "Busy", "Away");
        choicebox.setValue("Online");
        settings_icon.setImage(new Image(getClass().getResource("/org/project/images/settings.png").toExternalForm()));
        settings.getItems().addAll("Update Profile","Save ChatSession","Logout");
        userName.setText(user.getName());
    }


}
