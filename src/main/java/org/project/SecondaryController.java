package org.project;

import javafx.fxml.FXML;

import java.io.IOException;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("views/register_view");
        //App.setRoot("views/update_info_view");
    }
}