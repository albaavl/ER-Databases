package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SuccessPopupController {
    @FXML
    Label successDisplayText;

    public void displaySuccessText(String text) {
        successDisplayText.setText(text);
    }

}
