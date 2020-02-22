package org.project.controller.chat_home.left_side;

;


import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.project.controller.MainDeligator;
import org.project.controller.chat_home.HomeController;
import org.project.model.dao.users.Users;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestListViewCell extends ListCell<Users> {

    public AnchorPane pane;
    public Label RequestName;
    public JFXButton Accept;
    public JFXButton Decline;
    private HomeController homeController;
    private ChatListView chatListView;
    Users currentUser;
    public Circle requestPicture;

    public RequestListViewCell(HomeController homeController, Users currentUser,ChatListView chatListView) {
      this.homeController=homeController;
       this.currentUser=currentUser;
       this.chatListView=chatListView;
    }




    @Override
    protected void updateItem(Users user, boolean empty) {
        super.updateItem(user, empty);

        if (empty || user == null) {

            setText(null);
            setGraphic(null);

        } else {
            FXMLLoader mLLoader = null;
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/RequestIcon.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                RequestName.setText(String.valueOf(user.getName()));
                try {
                    System.out.println("user pic"+user.getDisplayPicture());
                    if (user.getDisplayPicture() != null) {
                        BufferedImage image = null;
                        System.out.println("inside tag"+user.getName()+" "+user.getDisplayPicture());
                        image = javax.imageio.ImageIO.read(new ByteArrayInputStream(user.getDisplayPicture()));
                        if (image != null){
                            Image card = SwingFXUtils.toFXImage(image, null);
                            requestPicture.setFill(new ImagePattern(card));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Accept.setOnAction(event -> {
                    homeController.acceptRequest(currentUser,this.getItem());
                    ArrayList<Users> usersToUpdate= new ArrayList<>();
                    usersToUpdate.add(currentUser);
                    usersToUpdate.add(this.getItem());
                    homeController.updateRequestNotifications(usersToUpdate);
                    System.out.println("Done Accepted");
                    this.getListView().refresh();
                });
                Decline.setOnAction(event -> {
                    homeController.declineRequest(currentUser,this.getItem());
                    ArrayList<Users> usersToUpdate= new ArrayList<>();
                    usersToUpdate.add(currentUser);
                    homeController.updateRequestNotifications(usersToUpdate);
                    System.out.println("Done Declined");
                    this.getListView().refresh();

                });



                setText(null);
                setGraphic(pane);



            }
        }

    }




}