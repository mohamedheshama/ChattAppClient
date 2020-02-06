package org.project;

import javafx.fxml.FXML;

import java.io.IOException;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        //App.setRoot("views/secondary");
        // App.setRoot("views/update_info_view");
        App.setRoot("views/register_view");


    }
}
