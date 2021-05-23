package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class linkDocPopupController {
    @FXML
    private Button yesLinkDocPopupButton;
    @FXML
    private Button noLinkDocPopupButton;
    //methods for linkDocPopup
    private AdStaffMenuController adStaffMenuController = new AdStaffMenuController();
    
    /**
     * Used to change the view into Assign a new doctor when user clicks yes.
     * closes window when user clicks one of the options
     * @param aEvent
     */
    @FXML
    private void onYesButton(ActionEvent aEvent) {
        adStaffMenuController.displayAssignANewDoctorView(aEvent); //TODO - need tofix this bs @meto@me
        Stage stage = (Stage) yesLinkDocPopupButton.getScene().getWindow();
        stage.close();
    }

    /**
     * closes window when user clicks one of the options. Does nothing else at all xD
     * @param aEvent
     */
    @FXML
    private void onNoButton(ActionEvent aEvent) {
        Stage stage = (Stage) noLinkDocPopupButton.getScene().getWindow();
        stage.close();
    }

}
