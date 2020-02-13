package org.project.controller.chat_home.left_side;;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class ChatsListViewCell extends ListCell<User> {
    public Circle picture;
    public Label status;
    public AnchorPane pane;
    public Label name;

    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);

        if (empty || user == null) {

            setText(null);
            setGraphic(null);

        } else {
            FXMLLoader mLLoader = null;
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/FriendsIcon.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                name.setText(String.valueOf(user.getName()));
                status.setStyle("-fx-background-color: red; -fx-background-radius: 100%;");


                setText(null);
                setGraphic(pane);


            }
        }
    }
}