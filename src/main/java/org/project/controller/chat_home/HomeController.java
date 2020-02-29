package org.project.controller.chat_home;

import com.healthmarketscience.rmiio.RemoteInputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.project.App;
import org.project.controller.ClientImp;
import org.project.controller.MainDeligator;
import org.project.controller.chat_home.left_side.LeftSideController;
import org.project.controller.chat_home.right_side.MainChatController;
import org.project.controller.chat_home.right_side.WelcomeController;
import org.project.controller.login.LoginController;
import org.project.controller.messages.Message;
import org.project.model.ChatRoom;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class HomeController implements Initializable, Serializable {

    public Parent getPrevScene() {
        return prevScene;
    }

    public void setPrevScene(Parent prevScene) {
        this.prevScene = prevScene;
    }

    Parent prevScene;
    public void recieveServerDown() {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/serverDown.fxml"));
            try {
                Parent root = loader.load();
                HomeController homeController = loader.getController();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public BorderPane getBorderBaneStage() {
        return borderBaneStage;
    }

    @FXML
    private transient BorderPane borderBaneStage;
    transient MainDeligator mainDeligator;
    private LoginController loginController;

    public ArrayList<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(ArrayList<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    ArrayList<ChatRoom> chatRooms = new ArrayList<>();
    Users user;
    String phoneNumber;
    transient Stage stage;
    ClientImp clientImp;
    transient MainChatController mainChatController;

    public LeftSideController getLeftSideController() {
        return leftSideController;
    }

    transient LeftSideController leftSideController;
    transient WelcomeController welcomeController;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        try {
            user = getUserData(this.phoneNumber);
            mainDeligator.setUser(user);
            mainDeligator.setHomeController(this);
            initClient();
            initLeftSide();
            initRightSide();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initClient() throws RemoteException {
        clientImp = new ClientImp(user, mainDeligator , this);
        mainDeligator.registerClient(clientImp);
    }

    public Users getUserData(String phoneNumber) throws RemoteException {
        return mainDeligator.login(phoneNumber);
    }

    public MainDeligator getMainDeligator() {
        return mainDeligator;
    }

    public void setMainDeligator(MainDeligator mainDeligator) {
        this.mainDeligator = mainDeligator;
    }

    public Stage getStage() {
        return ((Stage) borderBaneStage.getScene().getWindow());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // todo get user data
    }

    private void initRightSide() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/right_side/welcome_view.fxml"));
        Pane root=(Pane)loader.load();
        welcomeController=loader.getController();
        System.out.println("welcome controller"+welcomeController);
        welcomeController.setExistUser(user);
        //welcomeController.setHomeController(this);
        System.out.println("from init right side user is"+user.getName());
        borderBaneStage.setCenter(root);
        ChatRoom chatRoom = new ChatRoom();
    }

    private void initLeftSide() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/left_side/Left_Chat_pane.fxml"));
        Pane root = (Pane) loader.load();
        leftSideController = loader.getController();
        leftSideController.setTabPane(user, this);
        leftSideController.setUserIcon(user, this);
        leftSideController.setMainDeligator(mainDeligator);
        leftSideController.setHomeController(this);
        borderBaneStage.setLeft(root);
    }

    public void sendMsg(Message newMsg, ChatRoom chatRoom) throws RemoteException {
        mainDeligator.sendMsg(newMsg, chatRoom);
    }

    /*public boolean fileNotifyUser(Message newMsg, ChatRoom chatRoom) throws RemoteException {
        return mainDeligator.fileNotifyUser(newMsg, chatRoom);
    }*/


    public void openChatRoom(ChatRoom chatRoom, boolean isChatRoomExist){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/right_side/main_chat.fxml"));
            Parent root = loader.load();
            mainChatController = loader.getController();
            mainChatController.setmUser(user);
            mainChatController.setHomeController(this);
            mainChatController.setChatRoom(chatRoom);
            borderBaneStage.setCenter(root);
            if (!isChatRoomExist) {
                mainChatController.displayMessagesFromArrList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addChatRoom(ChatRoom chatRoom) throws IOException {
        user.getChatRooms().add(chatRoom);
    }

    public void reciveMsg(Message newMsg, ChatRoom chatRoom) throws Exception {
        if (mainChatController != null){
            mainChatController.reciveMsg(newMsg, chatRoom);
        }else{
            Platform.runLater(() -> {
                HBox hBox =new HBox();
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(getClass().getResource("/org/project/images/message.png").toExternalForm()));
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                Text text =new Text("New Message from : " + newMsg.getUser().getName());
                hBox.setPadding(new Insets(20,20,20,20

                ));
                hBox.setSpacing(5);
                hBox.setStyle("-fx-background-color: aliceblue");
                hBox.getChildren().add(imageView);
                hBox.getChildren().add(text);
                Notifications notificationBuilder = Notifications.create()
                        .title("Announcement")
                        .graphic(hBox)// todo  newMsg.getUser().getDisplayPicture()
                        .hideAfter(Duration.seconds(8))
                        .position(Pos.BOTTOM_RIGHT)
                        .onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("announcement has been clicked");
                            }
                        });
                notificationBuilder.darkStyle();
                getStage().show();
                getStage().requestFocus();
                AudioClip clip = null;
                try {
                    clip = new AudioClip(getClass().getResource("/org/project/sounds/notification.wav").toURI().toString());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                clip.play();
                notificationBuilder.show();
            });

        }

    }

   /* public void notifyrecieveFile(Message newMsg, ChatRoom chatRoom, int userSendFileId) {
     // return
              mainChatController.notifyrecieveFile(newMsg, chatRoom,userSendFileId);
    }*/

    public ChatRoom requestChatRoom(ArrayList<Users> chatroomUsers) throws RemoteException {
        return mainDeligator.requestChatRoom(chatroomUsers);
    }

    public boolean changeUserStatus(Users user, UserStatus userStatus) throws RemoteException {
        return mainDeligator.changeUserStatus(user ,userStatus);
    }

    public ArrayList<Message> getMessagesFromArrayList() {
        return mainChatController.getMessagesFromArrayList();
    }

    public void addUsersToFriedNotifications(List<String> contactList, Users user) throws RemoteException {
        mainDeligator.addUsersToFriedNotifications(contactList , user);
    }

    public List<String> getUsersList(int userId)  throws RemoteException{
        return mainDeligator.getUsersList(userId);
    }

    public void recieveContactRequest(List<String> conatactsToAdd,Users user) throws RemoteException  {
        mainDeligator.recieveContactRequest(conatactsToAdd,user);
    }


    // START AMR


    //END AMR

    public boolean acceptRequest(Users currentUser, Users friend) {
        return mainDeligator.acceptRequest(currentUser,friend);
    }

    public ArrayList<Users> updateNotifications(Users currentUser){
        return mainDeligator.updateNotifications(currentUser);
    }

    public ArrayList<Users> updateFriends(Users currentUser) {
        return mainDeligator.updateFriends(currentUser);
    }


    public void updateRequestNotifications(ArrayList<Users> usersToUpdate) {
        mainDeligator.updateRequestNotifications(usersToUpdate);
    }

    public boolean declineRequest(Users currentUser, Users friend) {
        return mainDeligator.declineRequest(currentUser,friend);
    }

    public void updateStatus(Users user, UserStatus newStatus) {
        mainDeligator.updateStatus(user,newStatus);
    }

    public ArrayList<Users> getUserOnlineFriends(Users user) throws RemoteException {
        return mainDeligator.getUserOnlineFriends(user);
    }

    public void notifyNewGroup(ArrayList<Users> groupUsers, ChatRoom currentChatRoom) throws RemoteException {

        mainDeligator.notifyNewGroup(groupUsers,currentChatRoom);
    }

    public void recieveMsgFromAdmin(Message newMsg, Users onlineUser) {

        if (!newMsg.getMsg().trim().equals("") && onlineUser.getId() == user.getId()) {
            Thread thread = new Thread(() -> {
                VBox announcementVbox = drawAnnouncementVbox(newMsg);
                Notifications notificationBuilder = Notifications.create()
                        .title("Announcement")
                        .graphic(announcementVbox)
                        .hideAfter(Duration.seconds(60))
                        .position(Pos.BOTTOM_RIGHT);
                notificationBuilder.darkStyle();
                getStage().requestFocus();
                AudioClip clip = null;
                try {
                    clip = new AudioClip(getClass().getResource("/org/project/sounds/notification.wav").toURI().toString());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                clip.play();
                notificationBuilder.show();
            });
            Platform.runLater(thread);
        }
    }

    public VBox drawAnnouncementVbox(Message newMsg) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 20, 20, 10));
        Text text = new Text(newMsg.getMsg());
        text.setStyle("-fx-font-family:  \"" + newMsg.getFontFamily() + "\" "
                + ";" + "-fx-font-size: " + newMsg.getFontSize()
                + ";" + " -fx-font-weight:" + newMsg.getFontWeight()
                + ";" + " -fx-font-style:" + newMsg.getFontPosture()
                + ";" + " -fx-fill: " + newMsg.getTextFill());
        if (newMsg.getMsg().trim().length() > 100) {
            text.setWrappingWidth(570);
        }
        ScrollPane scrollPane = new ScrollPane();
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResource("/org/project/images/admin-icon.png").toExternalForm()));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 0, 10, 0));
        hBox.getChildren().add(imageView);
        hBox.setAlignment(Pos.CENTER);
        HBox hBox1 = new HBox();
        hBox1.setPadding(new Insets(10, 10, 10, 10));
        hBox1.getChildren().add(text);
        scrollPane.setVmax(500);
        scrollPane.setPrefSize(115, 150);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(hBox1);
        vBox.getChildren().add(hBox);

        vBox.getChildren().add(scrollPane);
        vBox.setStyle("-fx-background-color: aliceblue");
        vBox.setPrefWidth(650);
        vBox.setMaxWidth(650);
        vBox.setMaxHeight(500);
        return vBox;
    }

    public void setSceneForUpdateUser() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/update_info_view.fxml"));
        Parent root = loader.load();
        borderBaneStage.setCenter(root);


    }


    public boolean logout(Users user) throws RemoteException {
        return mainDeligator.logout(user);
    }

    public void switchToLoginPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/login_view.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        getStage().setScene(new Scene(root));
    }

    public void notifyUserLoggedOut(Users user) {
        Thread thread = new Thread(() -> {
            Message message = new Message();
            message.setUser(user);
            message.setMsg(user.getName() + " is logged of");
            message.setFontSize(14);
            message.setFontWeight("BOLD");
            message.setFontFamily("Arial");
            message.setTextFill("green");
            VBox announcementVbox = drawAnnouncementVbox(message);
            Notifications notificationBuilder = Notifications.create()
                    .title("Announcement")
                    .graphic(announcementVbox)
                    .hideAfter(Duration.seconds(60))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("announcement has been clicked");
                        }
                    });
            notificationBuilder.darkStyle();
            getStage().requestFocus();
            AudioClip clip = null;
            try {
                clip = new AudioClip(getClass().getResource("/org/project/sounds/notification.wav").toURI().toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            clip.play();
            notificationBuilder.show();
        });
        Platform.runLater(thread);
    }

    public void recieveServerUp() {
        Platform.runLater(() -> {
            try {
                App.setRoot("/org/project/views/login_view");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }



    public void fileSendAccepted(Users users) throws RemoteException {
        mainDeligator.fileSendAccepted(users);
    }

    public void sendFileToReceiver() {
        mainChatController.sendFileToReceiver();
    }

    public void userIsLoggedOf() {
        mainChatController.userIsLoggedOf();
    }

    public void setsetverIsAlive() throws IOException {
        if (welcomeController != null){
            welcomeController.setsetverIsAlive();
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/serverDown.fxml"));
            Pane root = (Pane) loader.load();
            getStage().setScene(new Scene(root));
        }


    }

    public void saveChatSession() throws JAXBException {
        mainChatController.saveChatSession();
    }

    public void reveiveTheActualFile(String newMsg, RemoteInputStream remoteFileData) {
        mainChatController.reveiveTheActualFile(newMsg , remoteFileData);
    }
}