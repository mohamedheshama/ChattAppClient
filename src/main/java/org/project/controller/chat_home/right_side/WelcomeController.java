package org.project.controller.chat_home.right_side;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.project.controller.chat_home.HomeController;
import org.project.model.dao.users.Users;

import javax.imageio.ImageIO;
//import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class WelcomeController implements Initializable {

    @FXML public AnchorPane mainpPage;
    @FXML
    private Circle upd_image;
    @FXML
    private Label nameOfUser;
    @FXML
    private ImageView personImg;
    @FXML
    private Label phoneNoLbl;
    HomeController homeController;
    Users existUser;
    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    public void setExistUser(Users existUser) {
        this.existUser = existUser;
        try {
            if (existUser.getDisplayPicture() != null) {
                BufferedImage image = null;
                image = ImageIO.read(new ByteArrayInputStream(existUser.getDisplayPicture()));
                Image card = SwingFXUtils.toFXImage(image, null);
                upd_image.setFill(new ImagePattern(card));

            }
            nameOfUser.setText(existUser.getName());
            phoneNoLbl.setText(existUser.getPhoneNumber());
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }




    @FXML
    public void moveToUpdate() throws IOException {
        System.out.println("in the registerNewUser");
        //App.setRoot("/org/project/views/update_info_view");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


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
