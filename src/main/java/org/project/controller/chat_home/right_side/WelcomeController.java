package org.project.controller.chat_home.right_side;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.project.controller.chat_home.HomeController;

import java.io.IOException;

public class WelcomeController {
    @FXML public AnchorPane mainpPage;
    @FXML
    private ImageView personImg;
    HomeController homeController;

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    public void moveToUpdate() throws IOException {
        //App.setRoot("/org/project/views/update_info_view");
    }

    public void setsetverIsAlive() {
            Platform.runLater(() -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/serverDown.fxml"));
                Pane root = null;
                try {
                    root = (Pane) loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (getStage() != null){
                    getStage().setScene(new Scene(root));
                }

            });
    }

    private Stage getStage() {

        return ((Stage) mainpPage.getScene().getWindow());
    }
}
