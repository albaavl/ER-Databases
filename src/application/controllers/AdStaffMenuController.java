package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class AdStaffMenuController {
    
    @FXML
    Text adStaffMenuWelcomeText;

    public void displayWelcomeText(String name) {
        adStaffMenuWelcomeText.setText("FUCK YEAH " + name +  " get fkd.");
    }
}
