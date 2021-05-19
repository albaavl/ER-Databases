package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class PatientMenuController {

    //TODO - Lo ideal sería no usar una sola y utilizar esas dos funciones (displayUserX()) para q
    // se encarguen de cambiar el nombre/apellido etc en todos los sitios en los q se necesite

    @FXML
    Label userName;
    @FXML
    Label userSurname;

    public void displayUserName(String name) {
        userName.setText(name);
    }

    public void displayUserSurname(String surname) {
        userSurname.setText(surname);
    }
}
