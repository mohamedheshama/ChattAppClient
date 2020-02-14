package org.project.controller.chat_home.right_side;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.project.controller.chat_home.HomeController;
import org.project.controller.messages.Message;
import org.project.controller.messages.MessageType;
import org.project.model.ChatRoom;
import org.project.model.dao.users.Users;

import java.net.URL;
import java.rmi.RemoteException;
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
    ChatRoom chatRoom;

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
        System.out.println("s;geod");
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
        newMsg.setType(MessageType.USER);
        newMsg.setFontFamily(fontFamily);
        newMsg.setTextFill(colorPicked);
        newMsg.setFontSize(sizePicked);
        newMsg.setUser(mUser);
        newMsg.setFontWeight(getFontWeight().name());
        //System.out.println(newMsg.getUser().getName()+" "+newMsg.getMsg());
        homeController.sendMsg(newMsg, chatRoom);
        msgTxtField.setText("");
    }

    public void displayMessagesFromArrList() {
        for (Message message : chatRoom.getChatRoomMessage()) {
            if (message.getUser().getId() == mUser.getId()) {
                // todo set allignment to right and displayMsg
            } else {
                // todo set alignment to left nad display message
            }
        }
    }


    public void reciveMsg(Message newMsg) {
        if (newMsg.getUser().getId() == mUser.getId()) {
            displayMsg(newMsg, Pos.TOP_RIGHT);
        } else {
            displayMsg(newMsg, Pos.TOP_LEFT);
        }
    }

    private void displayMsg(Message msg, Pos pos) {
        Platform.runLater(() -> {
            showMsgsBox.getChildren().addAll(recipientChatLine(msg, pos));
        });

    }

    public HBox recipientChatLine(Message msg, Pos pos) {
        HBox hb = new HBox();
        try {
            Label name = new Label(msg.getName());
            // ImageView imageView = new ImageView();
            Text text = new Text(msg.getMsg());
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hb;
    }
}