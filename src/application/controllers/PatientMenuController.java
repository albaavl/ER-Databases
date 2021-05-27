package application.controllers;

// import javafx.*;
// import javafx.beans.value.ChangeListener;
// import javafx.beans.value.ObservableValue;
// import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;


import db.jdbc.*;
import db.jpa.*;
import db.pojos.*;

// import db.jdbc.*;


public class PatientMenuController {

    @FXML
    private Label welcomeText;

    private static SQL jdbc;
    private static JPAUserManager userman;


    public void displayPatientView(Patient p, SQL sqlman, JPAUserManager jpaman) {
        welcomeText.setText("");
    }

    
     
}
