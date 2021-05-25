package application.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AddPatientSuccessController {

    private static AdStaffMenuController adStaffMenuController;
    @FXML
    private Button okeyButton;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label usernameLabel;

    public void setAdStaffController() {
        adStaffMenuController = AdStaffMenuController.passAdStaffMenuController();
    }

    @FXML
    public void clickyclickOkey(ActionEvent actionEvent) throws IOException {
        adStaffMenuController.addDoctorToPatientPopup();
        Stage stage = (Stage) okeyButton.getScene().getWindow();
        stage.close();
    }

    public void setUsernamePassword(String username, String password) {
        passwordLabel.setText("Password: "+ password);
        usernameLabel.setText("Username: "+ username);
    }
}