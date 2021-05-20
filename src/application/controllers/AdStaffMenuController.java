package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AdStaffMenuController {
    
    @FXML
    Text adStaffMenuWelcomeText;
    @FXML
    Pane paneCreatePatient;
    @FXML
    Pane paneWelcome;

    public void displayWelcomeText(String name) {
        adStaffMenuWelcomeText.setText("FUCK YEAH " + name +  " get fkd.");
        
    }

    public void name(ActionEvent aEvent) {
        paneWelcome.setVisible(false);
        paneCreatePatient.setVisible(true);
    }
}

