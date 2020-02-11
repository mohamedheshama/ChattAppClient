package org.project.controller.chat_home.left_side;;

import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class UserIconController implements Initializable {
    public Circle image;
    public ChoiceBox choicebox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image userPicture= new Image(getClass().getResource("/org/project/images/iman.jpg").toExternalForm(),false);
        image.setFill(new ImagePattern(userPicture));
        choicebox.getItems().addAll("Online","Busy","Away");
        choicebox.setValue("Online");

    }
}
