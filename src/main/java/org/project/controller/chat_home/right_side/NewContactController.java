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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.project.controller.chat_home.HomeController;
import org.project.controller.chat_home.left_side.ContactListViewCell;
import org.project.model.dao.users.Users;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

public class NewContactController implements Initializable {

    ObservableList<String> contactListViewCell = FXCollections.observableArrayList();
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
    private Set<String> possibleSuggestionContacts;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private List<String> unFriendList;
    private String phoneNo = "";

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
        unFriendList = getUsersList(user.getId());
        if (unFriendList != null) {
            possibleSuggestionContacts = new HashSet<>(getUsersList(user.getId()));
            TextFields.bindAutoCompletion(phoneNoTxt, unFriendList);
        }
        phoneNo = phoneNoTxt.getText();


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
            if (contactListViewCell.size() == 0) {
                if (validatePhoneNo(phoneNo) && !isPhoneNoAdded(phoneNo)) {
                    contactListViewCell.add(phoneNo);
                    contactList.setItems(contactListViewCell);
                    errorMessageLbl.setText("");
                } else if (validatePhoneNo(phoneNo) && isPhoneNoAdded(phoneNo)) {
                    errorMessageLbl.setText("This contact is Already Added");
                } else {
                    errorMessageLbl.setText("Please Enter Valid contact");
                }
            } else {
                if (validatePhoneNo(phoneNo) && !isPhoneNoAdded(phoneNo)) {
                    contactList.getItems().add(phoneNo);
                    errorMessageLbl.setText("");
                } else if (validatePhoneNo(phoneNo) && isPhoneNoAdded(phoneNo)) {
                    errorMessageLbl.setText("This contact is Already Added");
                }else {
                    errorMessageLbl.setText("Sorry this is not Existed User or is Already Added ");
                }
            }
            System.out.println(contactListViewCell.size());
            phoneNoTxt.setText("");

        }
    }

    private boolean validatePhoneNo(String phoneNo) {
        boolean isValidPhoneNo = false;
        unFriendList = getUsersList(user.getId());
        System.out.println(unFriendList);
        if (unFriendList != null) {
            for (String phoneNum : unFriendList) {
                if (phoneNo.equals(phoneNum) && !isValidPhoneNo) {
                    isValidPhoneNo = true;
                }
            }
        } else {
            errorMessageLbl.setText("there is no Contacts");
        }
        return isValidPhoneNo;
    }

    private boolean isPhoneNoAdded(String phoneNo) {
        boolean isAdded = false;
        for (String phoneNum : contactListViewCell) {
            if (phoneNo.equals(phoneNum) && !isAdded) {
                isAdded = true;
            }
        }

        return isAdded;
    }

    @FXML
    private void handleSaveBtn(ActionEvent event) {
        System.out.println(contactListViewCell);
        if (contactListViewCell.size() > 0 && phoneNoTxt.getText().trim().equals("")) {
            try {
                for (String phoneNo : contactListViewCell) {
                    contactRequestList.add(phoneNo);
                    System.out.println(phoneNo);
                }
                homeController.addUsersToFriedNotifications(contactRequestList, user);
                homeController.recieveContactRequest(user);
                contactListViewCell.clear();
                contactRequestList.clear();
                errorMessageLbl.setText("");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            errorMessageLbl.setText("Please Add Phone Number to List ");
        }
        unFriendList = getUsersList(user.getId());
    }

    public void setCellFactory() {
        contactList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> ContactListView) {
                return new ContactListViewCell();
            }

        });

    }

    public List<String> getUsersList(int userId) {
        try {
            usersList = homeController.getUsersList(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return usersList;
    }


    public void handleAutoComplete(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            phoneNo = phoneNoTxt.getText();
            learnWord(phoneNo);
            errorMessageLbl.setText("");
        }
    }

    private void learnWord(String text) {
        unFriendList = getUsersList(user.getId());
        System.out.println("learnword"+unFriendList+"///");
        if (unFriendList != null || unFriendList.size() >0) {
            possibleSuggestionContacts = new HashSet<>(getUsersList(user.getId()));
            TextFields.bindAutoCompletion(phoneNoTxt, unFriendList);
        }
       if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(phoneNoTxt, possibleSuggestionContacts);
    }
}

