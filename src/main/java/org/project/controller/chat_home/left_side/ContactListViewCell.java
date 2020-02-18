package org.project.controller.chat_home.left_side;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ContactListViewCell extends ListCell<String> {
    HBox hBox;
    ImageView imageView;
    Label phoneLbl;
    Button deleteBtn;

    public ContactListViewCell() {

        hBox = new HBox();
        hBox.setPrefWidth(600);
        hBox.setPrefHeight(50);
        hBox.setAlignment(Pos.CENTER_LEFT);
        Pane pane = new Pane();
        phoneLbl = new Label();
        phoneLbl.setPrefHeight(26);
        phoneLbl.setPrefWidth(314);
        phoneLbl.setFont(Font.font("Amble CN", FontWeight.BOLD, 14));
        phoneLbl.setOpaqueInsets(new Insets(0, 5, 0, 10));
        deleteBtn = new Button("x");
        deleteBtn.setPrefHeight(35);
        deleteBtn.setPrefWidth(49);
        deleteBtn.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
        deleteBtn.setStyle("-fx-background-color:  #6fc9e5;-fx-background-radius:20;-fx-padding: 0");
        hBox.getChildren().addAll(phoneLbl, pane, deleteBtn);
        hBox.setHgrow(pane, Priority.ALWAYS);
        deleteBtn.setOnAction(actionEvent -> getListView().getItems().remove(getItem()));

    }

    @Override
    protected void updateItem(String phoneNo, boolean empty) {
        super.updateItem(phoneNo, empty);
        setText(null);
        setGraphic(null);
        if (phoneNo != null && !empty) {
            phoneLbl.setText(phoneNo);
            setGraphic(hBox);
        }
    }
}
