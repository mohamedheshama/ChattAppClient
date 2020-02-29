package org.project.controller.chat_home.left_side;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.project.controller.MainDeligator;
import org.project.controller.chat_home.HomeController;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import java.net.URL;
import java.util.ResourceBundle;

public class LeftSideController implements Initializable {
    public BorderPane borderPane;
    MainDeligator mainDeligator;
    HomeController homeController;



    TabPaneNotifications_Controller notificationsController;
    ChatRoom chatRoom;
    Users user;

    private ObservableList<Users> requestsObservableList;

    public LeftSideController() {
    }
    public TabPaneNotifications_Controller getNotificationsController() {
        return notificationsController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setMainDeligator(MainDeligator mainDeligator) {
        this.mainDeligator = mainDeligator;
    }

    public void setTabPane(Users user, HomeController homeController) throws Exception {



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/TabPaneNotifications.fxml"));
        Pane root = (Pane) loader.load();
        notificationsController = loader.getController();
        notificationsController.setUser(user,homeController);
        notificationsController.setRequestListView();
        notificationsController.setChatListView();
        notificationsController.setContactListView();
        borderPane.setLeft(root);
    }

    public void setUserIcon(Users user,HomeController homeController) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/UserIcon.fxml"));
        Pane root = (Pane) loader.load();
        UserIconController userIconController = loader.getController();
        userIconController.setUser(user,homeController);

        //userIconController.setHomeController(homeController);
        borderPane.setTop(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void recieveContactRequest(Users user) {
         notificationsController.recieveContactRequest(user);
    }
}
