package org.project.controller.chat_home.left_side;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatListView implements Initializable {

    @FXML
    private javafx.scene.control.ListView<User> listView;

    private ObservableList<User> studentObservableList;

    public ChatListView() {

        studentObservableList = FXCollections.observableArrayList();

        //add some Students
        studentObservableList.addAll(
                new User("John Doe", User.Status.Online),
                new User("Jane Doe", User.Status.Online),
                new User("Donte Dunigan", User.Status.Online),
                new User("Gavin Genna", User.Status.Online),
                new User("Darin Dear", User.Status.Online),
                new User("Pura Petty", User.Status.Online),
                new User("Herma Hines", User.Status.Online)
        );


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(studentObservableList);
        listView.setCellFactory(studentListView -> new ChatsListViewCell());
        listView.setCellFactory(new Callback<javafx.scene.control.ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(javafx.scene.control.ListView<User> UserListView) {
                return new ChatsListViewCell();
            }
        });


    }
}

