package application.controllers;

// import javafx.*;
// import javafx.beans.value.ChangeListener;
// import javafx.beans.value.ObservableValue;
// import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

// import db.jdbc.*;


public class PatientMenuController implements Initializable {

    @FXML
    private Label userName;
    @FXML
    private ComboBox<String> orderByComboBox;
    private String[] orderByStrings = {""}; //TODO - @me rellenar esto jeje

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        orderByComboBox.getItems().addAll(orderByStrings);    
    } 


    public void displayUserName(String surname) {
        userName.setText(surname);
    }

    
     
}
