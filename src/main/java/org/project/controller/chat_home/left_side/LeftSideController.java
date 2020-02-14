package org.project.controller.chat_home.left_side;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.project.controller.MainDeligator;
import org.project.model.dao.users.Users;

import java.net.URL;
import java.util.ResourceBundle;

public class LeftSideController implements Initializable {
    public BorderPane borderPane;

    MainDeligator mainDeligator;
    ObservableList<Users> chatsObservableList;
    private ObservableList<Users> requestsObservableList;

    public LeftSideController() {

    }

    Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public MainDeligator getMainDeligator() {
        return mainDeligator;
    }

    public void setMainDeligator(MainDeligator mainDeligator) {
        this.mainDeligator = mainDeligator;
    }

    public void setTabPane(Users user) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/TabPaneNotifications.fxml"));
        Pane root = (Pane) loader.load();
        TabPaneNotifications_Controller notificationsController = loader.getController();
        notificationsController.setUser(user);
        notificationsController.setRequestListView();
        notificationsController.setChatListView();
        borderPane.setLeft(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setTabPane(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
