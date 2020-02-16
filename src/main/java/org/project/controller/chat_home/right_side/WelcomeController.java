package org.project.controller.chat_home.right_side;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class WelcomeController {
    @FXML
    private ImageView personImg;

    @FXML
    public void moveToUpdate() throws IOException {
        System.out.println("in the registerNewUser");
        //App.setRoot("/org/project/views/update_info_view");
    }
}
