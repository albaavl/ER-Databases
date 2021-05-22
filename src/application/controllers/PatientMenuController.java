package application.controllers;

import javafx.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import db.jdbc.*;


public class PatientMenuController implements Initializable {

    //TODO - Lo ideal sería no usar una sola y utilizar esas dos funciones (displayUserX()) para q
    // se encarguen de cambiar el nombre/apellido etc en todos los sitios en los q se necesite

    @FXML
    private Label userName;
    @FXML
    private ComboBox<String> orderByComboBox;
    private String[] orderByStrings = {""}; //TODO - @Alba pon aqui las opciones q quieras "Opcion1", "Opcion2", ...

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        orderByComboBox.getItems().addAll(orderByStrings);    
    } 


    public void displayUserName(String surname) {
        userName.setText(surname);
    }

    
     
}
