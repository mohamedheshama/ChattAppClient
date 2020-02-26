package org.project.controller.chat_home.left_side;

;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.project.controller.chat_home.HomeController;
import org.project.model.dao.users.Users;

import java.rmi.RemoteException;
import java.util.List;


public class RequestsListView {


    public ListView requestListView;
    private ObservableList<Users> requestsObservableList;
    Users user;
    HomeController homeController;


    public void setRequestListView(Users user,HomeController homeController,ChatListView chatListView){
        System.out.println(chatListView);
        this.user=user;
        this.homeController=homeController;
        System.out.println("user notifications"+user.getName()+": "+user.getRequest_notifications());

        requestsObservableList = FXCollections.observableArrayList(user.getRequest_notifications());
        requestListView.setItems(requestsObservableList);
        requestListView.setCellFactory(requestListView -> new RequestListViewCell(homeController,user,chatListView));
        requestListView.setCellFactory(new Callback<javafx.scene.control.ListView<Users>, ListCell<Users>>() {
            @Override
            public ListCell<Users> call(javafx.scene.control.ListView<Users> UserListView) {
                return new RequestListViewCell(homeController,user,chatListView);
            }
        });

    }

    public void recieveContactRequest(Users user) {
        Platform.runLater(() -> {
            try {

                homeController.getLeftSideController().setTabPane(user,homeController);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }


}

