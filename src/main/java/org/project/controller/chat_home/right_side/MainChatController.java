package org.project.controller.chat_home.right_side;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.project.controller.MainDeligator;
import org.project.controller.XmlTransformer;
import org.project.controller.chat_home.HomeController;
import org.project.controller.createXML.SaveXml;
import org.project.controller.messages.Message;
import org.project.controller.messages.MessageType;
import org.project.controller.messages.voiceMessage.VoicePlayback;
import org.project.controller.messages.voiceMessage.VoiceRecorder;
import org.project.controller.messages.voiceMessage.VoiceUtil;
import org.project.controller.security.RSAEncryptionWithAES;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;
import java.util.Date;
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
    VBox stagePane;
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
    @FXML
    private ScrollPane showMsgsScrollPane;
    Users mUser;
    HomeController homeController;


    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    ChatRoom chatRoom;
    @FXML
    ImageView microphoneImageView;
    MainDeligator mainDeligator;
    Image microphoneActiveImage = new Image(getClass().getResource("/org/project/images/microphone-active.png").toExternalForm());
    Image microphoneInactiveImage = new Image(getClass().getResource("/org/project/images/microphone.png").toExternalForm());

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public void setmUser(Users mUser) {
        this.mUser = mUser;
    }
    public Users getmUser(){return mUser;}

    private String colorPicked;
    private String fontFamily = "Arial";
    private int sizePicked;
    private boolean italic;
    private boolean bold;
    RSAEncryptionWithAES rsaEncryptionWithAES;

    public ImageView getAttachFileImgBtn() {
        return attachFileImgBtn;
    }

    File file ;

    public void setFriendName(String friendName) {
        this.chatReceiversTxtLabel.setText(friendName);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            rsaEncryptionWithAES = new RSAEncryptionWithAES();
        } catch (Exception e) {
            e.printStackTrace();
        }



        chatReceiversTxtLabel.setText("myFrined");
        mainDeligator = new MainDeligator();
        msgTxtField.setWrapText(true);
        msgTxtField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    sendMsgToHomeController();
                } catch (RemoteException e) {
                    e.printStackTrace();
                    try {
                        homeController.setsetverIsAlive();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        fontComboBox.getItems().addAll(Font.getFontNames());
        updateFontComboBoxcell();
        fontColorPicker.setValue(Color.BLACK);
        colorPicked = toRGBCode(fontColorPicker.getValue());
        fontColorPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
                    Color color = (Color) newVal;
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
                    sizePicked = size;
                    setTextFieldStyle();
                }
        );
        fontComboBox.setValue(fontFamily);
        fontComboBox.valueProperty().addListener((observableValue, o, t1) -> {
            String fontName = t1.toString();
            this.fontFamily = fontName;
            setTextFieldStyle();
        });
        boldButton.setOnAction((ActionEvent e) -> {
            if (bold) {
                bold = false;
            } else {
                bold = true;
            }
            setTextFieldStyle();
        });
        italicButton.setOnAction((ActionEvent e) -> {
            if (italic) {
                italic = false;
            } else {
                italic = true;
            }
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

    public void updateFontComboBoxcell() {
        fontComboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                final ListCell<String> cell = new ListCell<String>() {
                    {
                        super.setPrefWidth(100);
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            this.setFont(new Font(item, 15));

                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMsgToHomeController() throws Exception {
       // String encryptedText = rsaEncryptionWithAES.encryptTextUsingAES(msgTxtField.getText(), rsaEncryptionWithAES.getSecretAESKeyString());
        Message newMsg = new Message();
        newMsg.setMsg(msgTxtField.getText());
        chatRoom.getChatRoomMessage().add(newMsg);
        //System.out.println("chat room id : "+chatRoom.getChatRoomId()+" message is " +chatRoom.getChatRoomMessage());
        newMsg.setType(MessageType.USER);
        newMsg.setFontFamily(fontFamily);
        newMsg.setTextFill(colorPicked);
        newMsg.setFontSize(sizePicked);
        newMsg.setFontPosture(getFontPosture().name());
        newMsg.setUser(mUser);
        newMsg.setPublicKey(rsaEncryptionWithAES.getPublicKey());
        newMsg.setEncryptedAESKeyString(rsaEncryptionWithAES.getEncryptedAESKeyString());
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
        Platform.runLater(() -> {
            createNotificationUI("New Message From : " + newMsg.getMsg(), "message.png");
        });


    }

    private void displayMsg(Message msg, Pos pos) {
        Platform.runLater(() -> {
            try {
                if (msg.getType() == MessageType.VOICE) {
                    ImageView imageview = new ImageView(new Image((getClass().getResource("/org/project/images/birthday.png").toExternalForm())));
                    showMsgsBox.getChildren().addAll(recipientChatLine(msg, pos));
                    VoicePlayback.playAudio(msg.getVoiceMsg());
                } else {
                    if(!msg.getMsg().trim().equals("")){
                        showMsgsBox.getChildren().addAll(recipientChatLine(msg, pos));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    Stage getStage() {
        return ((Stage) stagePane.getScene().getWindow());
    }

    public HBox recipientChatLine(Message msg, Pos pos) throws Exception {
        HBox hb = new HBox();
        JFXButton fileBtnLoad=new JFXButton();
        try {
            Label name = new Label(msg.getName());
            // ImageView imageView = new ImageView();
            Text text = new Text(msg.getMsg());
            Text userNameText = new Text(mUser.getName());
            text.setFill(Color.valueOf(msg.getTextFill()));
            text.setStyle("-fx-font-family: \"" + msg.getFontFamily() + "\"; "
                    + ";" + "-fx-font-size: " + msg.getFontSize()
                    + ";" + " -fx-font-weight:" + msg.getFontWeight()
                    + ";" + " -fx-font-style:" + msg.getFontPosture());
            if (msg.getMsg().length() > 50)
                text.setWrappingWidth(500);
            VBox vb = new VBox();
            //BufferedImage image = javax.imageio.ImageIO.read(new ByteArrayInputStream(msg.getUser().getDisplayPicture()));
            //Image card = SwingFXUtils.toFXImage(image, null);
            //imageView.setImage(card);
            //imageView.setFitWidth(15);
            //imageView.setPreserveRatio(true);
            hb.setAlignment(pos);
            vb.getChildren().add(userNameText);
            if(msg.getType().equals(MessageType.NOTIFICATION)){
                ImageView loadFile=new ImageView();
            loadFile.setImage(new Image(getClass().getResource("/org/project/images/download.png").toExternalForm()));
            loadFile.setFitHeight(50);
            loadFile.setFitWidth(50);
            fileBtnLoad.setGraphic(loadFile);

                vb.getChildren().add(fileBtnLoad);
            }
            vb.setSpacing(2);
            hb.getChildren().add(vb);
            hb.getChildren().add(text);
            hb.setPadding(new Insets(15, 12, 15, 12));
            hb.setSpacing(10);
            hb.setBackground(new Background(new BackgroundFill(Color.valueOf(msg.getTextFill()).invert() , new CornerRadii(25) , new Insets(10.0f))));
            //hb.maxWidthProperty().bindBidirectional(msg.getMsg());
            showMsgsScrollPane.vvalueProperty().bind(showMsgsBox.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileBtnLoad.setOnMouseClicked(mouseEvent -> {
            try {
                Users users = null;
                for (Users user : chatRoom.getUsers()) {
                    if (user.getId() != mUser.getId()){
                        users = user;
                        break;
                    }
                }

                fileSendAccepted(users);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        return hb;
    }

    private void fileSendAccepted(Users users) throws RemoteException {
        homeController.fileSendAccepted(users);
    }


// strart HEND


    public void sendFile() throws RemoteException,IOException, NotBoundException {
        FileChooser SaveFileChooser = new FileChooser();
        file = SaveFileChooser.showOpenDialog(getStage());
        if(file!=null) {
            String path = file.getAbsolutePath();
            Message newMsg = new Message();
            msgTxtField.setText(file.getName()+ new Date());
            newMsg.setMsg(msgTxtField.getText());
            newMsg.setType(MessageType.NOTIFICATION);
            newMsg.setFontFamily(fontFamily);
            newMsg.setTextFill(colorPicked);
            newMsg.setFontSize(sizePicked);
            newMsg.setFontPosture(getFontPosture().name());
            newMsg.setUser(mUser);
            newMsg.setChatId(chatRoom.getChatRoomId());
            newMsg.setFontWeight(getFontWeight().name());
            homeController.sendMsg(newMsg, chatRoom);
            msgTxtField.setText("");
        }
    }


    // END HEND


    //start AMR
    public void displayMessagesFromArrList() {
        Pos pos;
        if (chatRoom.getChatRoomMessage() != null){
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

    }

    public ArrayList<Message> getMessagesFromArrayList() {
        //System.out.println("Messages are " + chatRoom.getChatRoomMessage() + "chat room id : " + chatRoom.getChatRoomId()   );
        return chatRoom.getChatRoomMessage();
    }


    // END AMR


    public void CreateGroupBtnHandler(MouseEvent mouseEvent) throws IOException {
        //set scene of Group_chat.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/views/chat_home/right_side/Group_Chat.fxml"));
        Parent root = loader.load();
        AddGroupChat addGroupChat = loader.getController();
        addGroupChat.setUser(mUser);
        addGroupChat.setChatRoom(chatRoom);
        addGroupChat.setHomeController(homeController);
        // todo pass the Arraylist of the current chat room to tha page
        homeController.getBorderBaneStage().setCenter(root);

    }

    public void recordVoiceMessage(MouseEvent event) {
        if (VoiceUtil.isRecording()) {
            Platform.runLater(() -> {
                        microphoneImageView.setImage(microphoneInactiveImage);
                    }
            );
            VoiceUtil.setRecording(false);
        } else {
            Platform.runLater(() -> {
                        microphoneImageView.setImage(microphoneActiveImage);
                    }
            );
            VoiceRecorder.captureAudio(this);
        }
    }

    public void sendVoiceMessage(byte[] audio) throws RemoteException {
        Message createMessage = new Message();
        createMessage.setName(mUser.getName());
        createMessage.setUser(mUser);
        createMessage.setMsg("voice note");
        createMessage.setType(MessageType.VOICE);
        createMessage.setFontFamily(fontFamily);
        createMessage.setTextFill(colorPicked);
        createMessage.setFontSize(sizePicked);
        createMessage.setVoiceMsg(audio);
        createMessage.setChatId(chatRoom.getChatRoomId());
        createMessage.setFontWeight(getFontWeight().name());
        homeController.sendMsg(createMessage, chatRoom);
        msgTxtField.setText("");
    }

    public void sendFileToReceiver() {
        if (file != null) {
            Thread thread = new Thread(new RMIFileTransfer(file, mUser.getId(), chatRoom, mainDeligator));
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void userIsLoggedOf() {
        Platform.runLater(() -> {
            createNotificationUI("this user is logged of", "offlineUser.png");
        });
    }

    public void createNotificationUI(String notificationText, String imageName) {
        HBox hBox = new HBox();
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResource("/org/project/images/" + imageName).toExternalForm()));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        Text text = new Text(notificationText);
        hBox.setPadding(new Insets(20, 20, 20, 20));
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
        //notificationBuilder.darkStyle();
        if (!getStage().isShowing())
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
    }

    public void saveChatSession() throws JAXBException {
        XmlTransformer xmlTransformer= new XmlTransformer(chatRoom.getChatRoomMessage() , chatRoom.getUsers());
        xmlTransformer.transform();
    }

    public void reveiveTheActualFile(String newMsg, RemoteInputStream remoteFileData) {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream fileData = null;
                ByteBuffer buffer = null;
                WritableByteChannel to = null;
                ReadableByteChannel from = null;
                try {
                    fileData = RemoteInputStreamClient.wrap(remoteFileData);
                    System.out.println("server 2 write");
                    from = Channels.newChannel(fileData);
                    buffer = ByteBuffer.allocateDirect(fileData.available());
                    String home = System.getProperty("user.home");
                    to = FileChannel.open(Paths.get(home + "/Downloads/" + newMsg), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
                    while ((from.read(buffer) != -1)) {
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            System.out.println("server write");
                            to.write(buffer);
                        }
                        buffer.clear();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        to.close();
                        from.close();
                        fileData.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.setDaemon(true);
        th.start();
    }
}