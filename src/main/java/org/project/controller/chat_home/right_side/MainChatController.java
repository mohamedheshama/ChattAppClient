package org.project.controller.chat_home.right_side;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.project.controller.MainDeligator;
import org.project.controller.chat_home.HomeController;
import org.project.controller.messages.Message;
import org.project.controller.messages.MessageType;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainChatController implements Initializable {
    @FXML
    private JFXButton sendMsgImgBtn;
    @FXML
    private TextArea msgTxtField;
    @FXML
    private VBox showMsgsBox;
    @FXML
    AnchorPane stagePane;
    @FXML
    private ImageView attachFileImgBtn;
    @FXML
    private JFXButton addGroupMembersImgBtn;
    @FXML
    private Label chatReceiversTxtLabel;
    @FXML
    private JFXButton saveChatImgBtn;
    @FXML
    private JFXToggleButton italicButton;
    @FXML
    private JFXToggleButton boldButton;
    @FXML
    private JFXComboBox sizeComboBox;
    @FXML
    private JFXComboBox fontComboBox;
    @FXML
    private JFXColorPicker fontColorPicker;

    Users mUser;
    HomeController homeController;

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    ChatRoom chatRoom;
    MainDeligator mainDeligator;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public void setmUser(Users mUser) {
        this.mUser = mUser;
    }

    private String colorPicked;
    private String fontFamily = "Arial";
    private int sizePicked;
    private boolean italic;
    private boolean bold;

    public ImageView getAttachFileImgBtn() {
        return attachFileImgBtn;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatReceiversTxtLabel.setText("myFrined");
        try {
            mainDeligator = new MainDeligator();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        msgTxtField.setWrapText(true);
        msgTxtField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    sendMsgToHomeController();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        fontComboBox.getItems().addAll(Font.getFontNames());
        fontColorPicker.setValue(Color.BLACK);
        colorPicked = toRGBCode(fontColorPicker.getValue());
        fontColorPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
                    Color color = (Color) newVal;
                    System.out.println("Color : " + toRGBCode(color));
                    colorPicked = toRGBCode(color);
                    setTextFieldStyle();
                }
        );

        sizeComboBox.getItems().addAll(IntStream.rangeClosed(8, 28).boxed().collect(Collectors.toList()));
        sizeComboBox.setValue(14);
        sizePicked = 14;
        sizeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                    //Country current = (Country) newVal;
                    int size = (int) newVal;
                    System.out.println("Size : " + size);
                    sizePicked = size;
                    setTextFieldStyle();
                }
        );
        fontComboBox.setValue(fontFamily);
        fontComboBox.valueProperty().addListener((observableValue, o, t1) -> {
            String fontName = t1.toString();
            System.out.println(fontName);
            this.fontFamily = fontName;
            setTextFieldStyle();
        });
        boldButton.setOnAction((ActionEvent e) -> {
            if (bold) {
                bold = false;
            } else {
                bold = true;
            }
            System.out.println("Now bold is " + bold);
            setTextFieldStyle();
        });
        italicButton.setOnAction((ActionEvent e) -> {
            if (italic) {
                italic = false;
            } else {
                italic = true;
            }
            System.out.println("Now italic is " + italic);
            setTextFieldStyle();
        });
        setTextFieldStyle();
    }

    public String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public FontWeight getFontWeight() {
        if (bold) {
            return FontWeight.BOLD;
        } else {
            return FontWeight.LIGHT;
        }
    }

    public FontPosture getFontPosture() {
        if (italic) {
            return FontPosture.ITALIC;
        } else {
            return FontPosture.REGULAR;
        }
    }

    public void setTextFieldStyle() {
       // System.out.println("s;geod");
        String str = msgTxtField.getText().toString();
        msgTxtField.setText("");
        msgTxtField.setStyle("-fx-font-family: \"" + fontFamily + "\"; " + "-fx-text-fill: " + colorPicked + ";" + "-fx-font-size: " + sizePicked + ";" + " -fx-font-weight:" + getFontWeight().name() + ";" + " -fx-font-style:" + getFontPosture().name());
        msgTxtField.setText(str);
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void sendMessage(ActionEvent actionEvent) {
        try {
            sendMsgToHomeController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void sendMsgToHomeController() throws RemoteException {
        Message newMsg = new Message();
        newMsg.setMsg(msgTxtField.getText());
        chatRoom.getChatRoomMessage().add(newMsg);
        System.out.println("chat room id : "+chatRoom.getChatRoomId()+" message is " +chatRoom.getChatRoomMessage());
        newMsg.setType(MessageType.USER);
        newMsg.setFontFamily(fontFamily);
        newMsg.setTextFill(colorPicked);
        newMsg.setFontSize(sizePicked);
        newMsg.setUser(mUser);
        newMsg.setChatId(chatRoom.getChatRoomId());
        newMsg.setFontWeight(getFontWeight().name());
        homeController.sendMsg(newMsg, chatRoom);
        msgTxtField.setText("");
    }



    public void reciveMsg(Message newMsg, ChatRoom chatRoom) {
        if (newMsg.getUser().getId() == mUser.getId()) {
            displayMsg(newMsg, Pos.TOP_RIGHT);
        } else {
            if (chatRoom.getChatRoomId().equals(this.chatRoom.getChatRoomId())) {
                displayMsg(newMsg, Pos.TOP_LEFT);
            } else {
                showMessageIncommingNotification(newMsg);
            }
        }
    }


    private void showMessageIncommingNotification(Message newMsg) {
        System.out.println("in the show Notification -> " + newMsg.getMsg());

        Platform.runLater(() -> {
            Notifications notificationBuilder = Notifications.create()
                    .title("Announcement")
                    .graphic(new ImageView(new Image(getClass().getResource("/org/project/images/birthday.png").toExternalForm())))// todo  newMsg.getUser().getDisplayPicture()
                    .text("New Message From : " + newMsg.getMsg())
                    .hideAfter(Duration.seconds(8))
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
                System.out.println("in URI EX");
                e.printStackTrace();
            }
            clip.play();
            notificationBuilder.show();
        });


    }

    private void displayMsg(Message msg, Pos pos) {
        Platform.runLater(() -> {
            showMsgsBox.getChildren().addAll(recipientChatLine(msg, pos));
        });

    }

    Stage getStage() {
        return ((Stage) stagePane.getScene().getWindow());
    }

    public HBox recipientChatLine(Message msg, Pos pos) {
        HBox hb = new HBox();
        try {
            Label name = new Label(msg.getName());
            // ImageView imageView = new ImageView();
            System.out.println(msg.getTextFill() + "  >");
            Text text = new Text(msg.getMsg());
            text.setFill(Color.valueOf(msg.getTextFill()));
            text.setStyle("-fx-font-family: \"" + msg.getFontFamily() + "\"; "
                    + ";" + "-fx-font-size: " + msg.getFontSize()
                    + ";" + " -fx-font-weight:" + msg.getFontWeight()
                    + ";" + " -fx-font-style:" + FontPosture.REGULAR);
            if (msg.getMsg().length() > 50)
                text.setWrappingWidth(500);
            VBox vb = new VBox();
            //BufferedImage image = javax.imageio.ImageIO.read(new ByteArrayInputStream(msg.getUser().getDisplayPicture()));
            //Image card = SwingFXUtils.toFXImage(image, null);
            //imageView.setImage(card);
            //imageView.setFitWidth(15);
            //imageView.setPreserveRatio(true);
            hb.setAlignment(pos);
            vb.getChildren().add(name);
            //vb.getChildren().add(imageView);
            vb.setSpacing(2);
            hb.getChildren().add(vb);
            hb.getChildren().add(text);
            hb.setPadding(new Insets(15, 12, 15, 12));
            hb.setSpacing(10);
            hb.setBackground(new Background(new BackgroundFill(Color.valueOf(msg.getTextFill()).invert() , new CornerRadii(25) , new Insets(10.0f))));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hb;
    }


// strart HEND

    public boolean notifyrecieveFile(Message newMsg, ChatRoom chatRoom) {
       // if (newMsg.getUser().getId() == mUser.getId()) {
         //   displayNotifyForFile(newMsg);
       // } else {
            if (chatRoom.getChatRoomId().equals(this.chatRoom.getChatRoomId())) {
                if(displayNotifyForFile(newMsg)){
                    return true;
                }
            } else {
                showMessageIncommingNotification(newMsg);
                return true;
            }
        //}
        return false;
    }

    public void sendFile() throws IOException, NotBoundException {

        // Message message, File file, ChatRoom chatRoom
        FileChooser SaveFileChooser = new FileChooser();
        // Stage stage= (Stage) showMsgsBox.getScene().getWindow();

        File file = SaveFileChooser.showOpenDialog(getStage());
        String path = file.getAbsolutePath();
        Message newMsg = new Message();
        msgTxtField.setText(file.getName());
        newMsg.setMsg(msgTxtField.getText());
        newMsg.setType(MessageType.USER);
        newMsg.setFontFamily(fontFamily);
        newMsg.setTextFill(colorPicked);
        newMsg.setFontSize(sizePicked);
        newMsg.setUser(mUser);
        newMsg.setChatId(chatRoom.getChatRoomId());
        newMsg.setFontWeight(getFontWeight().name());
        homeController.sendMsg(newMsg, chatRoom);
        msgTxtField.setText("");
      if(homeController.fileNotifyUser(newMsg, chatRoom)){
          InputStream inputStream = new FileInputStream(file.getAbsolutePath());
          RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inputStream);
          mainDeligator.sendFile(newMsg, remoteFileData);
      }







    }

    private boolean displayNotifyForFile(Message msg) {
        final boolean[] flage = {true};

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Recive File ");
            alert.setHeaderText("Look,There is a File Coming");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                flage[0] =true;
            } else {
                flage[0]=false;
            }







           // showMsgsBox.getChildren().addAll(alertFile(msg));
        });
return flage[0];
    }

/*
    public VBox alertFile(Message msg){

        VBox vb = new VBox();
        try {
            Label name = new Label(msg.getTextFill());
            // ImageView imageView = new ImageView();
            System.out.println(msg.getTextFill() + "  >");
            Text text = new Text(msg.getMsg());
            text.setFill(Color.valueOf(msg.getTextFill()));
            text.setStyle("-fx-font-family: \"" + msg.getFontFamily() + "\"; "
                    + ";" + "-fx-font-size: " + msg.getFontSize()
                    + ";" + " -fx-font-weight:" + msg.getFontWeight()
                    + ";" + " -fx-font-style:" + FontPosture.REGULAR);
            Button acceptBtn=new Button("accept");
            Button rejectBtn=new Button("reject");
           // VBox vb = new VBox();
            //BufferedImage image = javax.imageio.ImageIO.read(new ByteArrayInputStream(msg.getUser().getDisplayPicture()));
            //Image card = SwingFXUtils.toFXImage(image, null);
            //imageView.setImage(card);
            //imageView.setFitWidth(15);
            //imageView.setPreserveRatio(true);
            vb.getChildren().add(acceptBtn);
            vb.getChildren().add(rejectBtn);
            //vb.getChildren().add(imageView);
            vb.setSpacing(2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vb;


    }

*/



















































    // END HEND


    //start AMR
    public void displayMessagesFromArrList() {
        Pos pos;
        System.out.println("chat room messages " + chatRoom.getChatRoomMessage());
        for (Message message : chatRoom.getChatRoomMessage()) {
            if (message.getUser().getId() == mUser.getId()) {
                pos = Pos.TOP_RIGHT;
            } else {
                pos = Pos.TOP_LEFT;
                // todo set alignment to left nad display message
            }
            displayMsg(message , pos);
        }

    }

    public ArrayList<Message> getMessagesFromArrayList() {
        System.out.println("Messages are " + chatRoom.getChatRoomMessage() + "chat room id : " + chatRoom.getChatRoomId()   );
        return chatRoom.getChatRoomMessage();
    }


    // END AMR


}