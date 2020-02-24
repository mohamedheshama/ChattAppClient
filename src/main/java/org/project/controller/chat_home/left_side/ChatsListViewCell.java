package org.project.controller.chat_home.left_side;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.project.model.ChatRoom;

import java.io.IOException;

;

public class ChatsListViewCell extends ListCell<ChatRoom> {
    public Circle picture;
    public Label status;
    public AnchorPane pane;
    public Label name;


    @Override
    protected void updateItem(ChatRoom chatRoom, boolean empty) {
        super.updateItem(chatRoom, empty);

        if (empty || chatRoom == null) {

            setText(null);
            setGraphic(null);

        } else {
            FXMLLoader mLLoader = null;
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/FriendsIcon.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();

                    name.setText(String.valueOf("Group"));

                } catch (IOException e) {
                    e.printStackTrace();
                }


                setText(null);
                setGraphic(pane);


            }
        }
    }

}