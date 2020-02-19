package org.project.controller.chat_home.right_side;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.project.controller.chat_home.HomeController;
//import org.project.controller.chat_home.left_side.ContactListViewCell;
import org.project.model.dao.users.Users;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

public class NewContactController implements Initializable {

    ObservableList<String> contactListViewCell;
    List<String> usersList;
    List<String> contactRequestList = new ArrayList<>();
    @FXML
    private Button addBtn;
    @FXML
    private VBox contactBox;
    @FXML
    private ListView<String> contactList;
    @FXML
    private JFXTextField phoneNoTxt;
    @FXML
    private Label errorMessageLbl;
    private Set<String> possibleSuggestionContacts ;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    HomeController homeController;
    Users user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellFactory();

    }

    @FXML
    private void handleAddBtn(ActionEvent event) {
        String phoneNo = phoneNoTxt.getText();
        if (!phoneNo.trim().isEmpty()) {
            if (contactListViewCell == null) {
                contactListViewCell = FXCollections.observableArrayList(phoneNo);
                contactList.setItems(contactListViewCell);
                phoneNoTxt.setText("");
            } else {
                contactList.getItems().add(phoneNo);
                phoneNoTxt.setText("");
            }
        }
    }

    @FXML
    private void handleSaveBtn(ActionEvent event) {

        if (contactListViewCell.size() > 0) {
            try {
                for (String phoneNo : contactListViewCell) {
                    contactRequestList.add(phoneNo);
                    System.out.println(phoneNo);
                }
               homeController.addUsersToFriedNotifications(contactRequestList, user);
                contactListViewCell.clear();
                contactRequestList.clear();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            errorMessageLbl.setText("Please Add Phone Number ");
        }
    }

    public void setCellFactory() {
        contactList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> ContactListView) {
                return null;
               // return new ContactListViewCell();
            }

        });

    }
    public List<String> getUsersList(int userId){
        try {
            usersList= homeController.getUsersList(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return usersList;
    }


    public void handleAutoComplete(KeyEvent keyEvent) {
    }
}

