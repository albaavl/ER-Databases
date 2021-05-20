package application.controllers;

import javafx.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import db.jdbc.*;


public class PatientMenuController {

    //TODO - Lo ideal sería no usar una sola y utilizar esas dos funciones (displayUserX()) para q
    // se encarguen de cambiar el nombre/apellido etc en todos los sitios en los q se necesite

    @FXML
    Label userName;
    @FXML
    ChoiceBox order = new ChoiceBox (FXCollections.observableArrayList(
    	    "Date", "Medication", "Duration", "Look for specific meds"));


    public void displayUserName(String surname) {
        userName.setText(surname);
    } 
    
    order.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
    	  
        // if the item of the list is changed
        public void changed(ObservableValue ov, Number value, Number new_value)
        {

        }
    });
     
}
