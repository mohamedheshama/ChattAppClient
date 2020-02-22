package org.project.controller.chat_home.left_side;

;

import com.jfoenix.controls.JFXComboBox;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.project.controller.chat_home.HomeController;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserIconController {

    public ChoiceBox choicebox;
    public Circle Userimage;
    public ImageView settings_icon;
    public JFXComboBox settings;
    public Users user;
    public Label userName;
    public Label status;
    public HomeController homeController;

    public void setUser(Users user, HomeController homeController) {
        this.user = user;
        this.homeController=homeController;
        Image userPicture = new Image(getClass().getResource("/org/project/images/iman.jpg").toExternalForm(), false);
        Userimage.setFill(new ImagePattern(userPicture));
        choicebox.getItems().addAll("Available", "Busy", "Away");
        choicebox.setValue(user.getStatus().toString());
        settings_icon.setImage(new Image(getClass().getResource("/org/project/images/settings.png").toExternalForm()));
        settings.getItems().addAll("Update Profile","Save ChatSession","Logout");
        userName.setText(user.getName());
        try {
            if (user.getDisplayPicture() != null) {
                BufferedImage image = null;
                image = javax.imageio.ImageIO.read(new ByteArrayInputStream(user.getDisplayPicture()));
                Image card = SwingFXUtils.toFXImage(image, null);
                Userimage.setFill(new ImagePattern(card));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setStatus();
        System.out.println(user.getRequest_notifications());
        choicebox.setOnAction(event -> {
            UserStatus newStatus=UserStatus.valueOf(choicebox.getSelectionModel().getSelectedItem().toString());
            System.out.println(newStatus);
            System.out.println(user);
            System.out.println(homeController);
            user.setStatus(newStatus);
            homeController.updateStatus(user,newStatus);
            homeController.updateRequestNotifications(user.getFriends());
            choicebox.setValue(user.getStatus());
            setStatus();
        });


    }

    public void setStatus() {
        if (user.getStatus() == UserStatus.Available)
            status.setStyle("-fx-background-color: green; -fx-background-radius: 100%;");
        else if (user.getStatus() == UserStatus.Busy)
            status.setStyle("-fx-background-color: Orange; -fx-background-radius: 100%;");
        else if (user.getStatus() == UserStatus.Away)
            status.setStyle("-fx-background-color: Yellow; -fx-background-radius: 100%;");

    }
}
