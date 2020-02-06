package org.project.controller.chat_home.right_side;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import org.project.controller.chat_home.ChatListener;
import org.project.controller.messages.Message;
import org.project.controller.messages.MessageType;

import java.util.logging.Logger;

public class MainChatController {
    @FXML
    HTMLEditor messageBox;
    @FXML
    ListView chatPane;
    Logger logger = Logger.getGlobal();

    public void sendMethod(KeyEvent keyEvent) {
    }

    public void sendButtonAction(ActionEvent actionEvent) {
        String msg = messageBox.getHtmlText();
        ChatListener.send(msg);
        //todo expected that msg is sent succefuly and listner called the addTochat function
        Message recieved = new Message();
        recieved.setType(MessageType.USER);
        recieved.setMsg(msg);
        addToChat(recieved);
        System.out.println("done Adding the message");
        chatPane.setPrefSize(60, 60);
        // chatPane.getItems().add("hihihihihihi");

    }


    public void addToChat(Message msg) {
        logger.warning(msg.getMsg());
//        Image image = new Image(getClass().getClassLoader().getResource("../resources/org/project/images/bg2.jpg").toString());
        //      ImageView profileImage = new ImageView(image);
        WebView webView = new WebView();
        webView.getEngine().loadContent(msg.getMsg());

        HBox hBox = new HBox();
        hBox.setMaxWidth(chatPane.getWidth() - 20);
        hBox.setAlignment(Pos.TOP_RIGHT);
        hBox.getChildren().addAll(webView);
        chatPane.getItems().add(hBox);

    }

}
