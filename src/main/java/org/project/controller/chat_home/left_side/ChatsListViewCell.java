package org.project.controller.chat_home.left_side;

;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import java.io.IOException;

public class ChatsListViewCell extends ListCell<Users> {
    public Circle picture;
    public Label status;
    public AnchorPane pane;
    public Label name;

    @Override
    protected void updateItem(Users user, boolean empty) {
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
                if (user.getStatus() == UserStatus.Available)
                    status.setStyle("-fx-background-color: green; -fx-background-radius: 100%;");
                else if (user.getStatus() == UserStatus.Busy)
                    status.setStyle("-fx-background-color: Orange; -fx-background-radius: 100%;");
                else if (user.getStatus() == UserStatus.Away)
                    status.setStyle("-fx-background-color: Yellow; -fx-background-radius: 100%;");

                setText(null);
                setGraphic(pane);


            }
        }
    }
}