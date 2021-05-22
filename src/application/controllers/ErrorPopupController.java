package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ErrorPopupController {
    @FXML
    Text errorDisplayText;

    public void displayErrorText(String text) {
        errorDisplayText.setText(text);
    }
}
