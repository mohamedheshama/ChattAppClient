package org.project.controller.chat_home.left_side;

;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import org.project.model.dao.users.Users;

import java.io.IOException;

public class RequestListViewCell extends ListCell<Users> {

    public AnchorPane pane;
    public Label RequestName;


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
                setText(null);
                setGraphic(pane);


            }
        }
    }
}