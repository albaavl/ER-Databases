package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdStaffMenuController implements Initializable {
    
    @FXML
    Text adStaffMenuWelcomeText;
    //Patient menus
    @FXML
    Pane paneCreatePatientView;
    @FXML
    Pane paneChangePatientDataView;
    @FXML
    Pane paneAssignANewDoctorView;
    //Worker menus
    @FXML
    Pane paneCreateWorkerView;
    @FXML
    Pane paneChangeWorkerDataView;
    @FXML
    Pane paneEditShiftView;
    //welcome screen
    @FXML
    Pane paneWelcomeView;

    public void displayWelcomeText(String name) {
        adStaffMenuWelcomeText.setText("FUCK YEAH " + name +  " get fkd.\n It works!");
        
    }

    public void displayCreatePatientView(ActionEvent aEvent) {
        paneWelcomeView.setVisible(false);
        paneCreatePatientView.setVisible(true);
        paneChangePatientDataView.setVisible(false);
        paneAssignANewDoctorView.setVisible(false);
    }
    public void displayChangePatientDataView(ActionEvent aEvent) {
        paneWelcomeView.setVisible(false);
        paneCreatePatientView.setVisible(false);
        paneChangePatientDataView.setVisible(true);
        paneAssignANewDoctorView.setVisible(false);
    }
    public void displayAssignANewDoctorView(ActionEvent aEvent) {
        paneWelcomeView.setVisible(false);
        paneCreatePatientView.setVisible(false);
        paneChangePatientDataView.setVisible(false);
        paneAssignANewDoctorView.setVisible(true);
    }

    public void displayCreateWorkerView(ActionEvent actionEvent) {

        paneWelcomeView.setVisible(false);
        paneCreateWorkerView.setVisible(true);
        paneChangeWorkerDataView.setVisible(false);
        paneEditShiftView.setVisible(false);
    }
    public void displayChangeWorkerView(ActionEvent actionEvent) {
        paneWelcomeView.setVisible(false);
        paneCreateWorkerView.setVisible(false);
        paneChangeWorkerDataView.setVisible(true);
        paneEditShiftView.setVisible(false);
    }
    public void displayEditShiftView(ActionEvent actionEvent) {
        paneWelcomeView.setVisible(false);
        paneCreateWorkerView.setVisible(false);
        paneChangeWorkerDataView.setVisible(false);
        paneEditShiftView.setVisible(true);
    }

    @FXML
    private ToggleGroup genderRadioButton;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private DatePicker birthDatePicker;
    //BLOODTYPE STUFF
    @FXML
    private ComboBox<String> bloodTypeChoiceBox;
    private String[] bloodTypeStrings = {"A+", "A-", "B+", "B-", "AB+", "AB-", "0+", "0-"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        bloodTypeChoiceBox.getItems().addAll(bloodTypeStrings);
        
    }

    @FXML
    private TextArea allergiesTextArea;
    @FXML
    private TextField addressTextField;

    public void createPatient(ActionEvent actionEvent) { //TODO - quitar strings de prueba jej

        String name = nameTextField.getText();
        System.out.println(name);
        String surname = surnameTextField.getText();
        System.out.println(surname);
        String gender = "";
        if (femaleRadioButton.isSelected()) {
            gender = "Female";
        } else if (maleRadioButton.isSelected()) {
            gender = "Male";
        } else {
            //TODO - needs to send an exception
        }
        System.out.println(gender);
        String bloodType = bloodTypeChoiceBox.getValue();

        Date bDate = Date.valueOf(birthDatePicker.getValue());
        if ((bDate.before(Date.valueOf(LocalDate.now()))) || bDate.equals(Date.valueOf(LocalDate.now()))) {
            System.out.println("yay");
            System.out.println(bDate.toString());
        } else {
            System.out.println("nain"); //TODO - obviamente aqui va el popup de q la fecha ta mal
        }
        String allergies = allergiesTextArea.getText();
        System.out.println(allergies);
        String address = addressTextField.getText();
        System.out.println(address);

        //Añadir patient a la db

        //Preguntar al subnormal de turno si quiere asignar un médico al paciente #popup

        //Congratulaciones has hecho las cosas bien y el paciente esta en la db! #popup y reset de todas las opciones
        // nameTextField.clear();
        // surnameTextField.clear();
        
    }   


    /**
     * When called it displays a new window with an error msg in function of the int passed to the function
     * <p> 0 - General error (unspecified) </p>
     * <p> 1 - ur birthdate cant be tomorrow nor on any future date bruh
     * <p> 2 - Please fill all the values lmao.
     * <p> X - idk keep adding stuff here...

     * @param errorType - int
     * @throws IOException
     */
    private void errorPopup(int errorType) throws IOException {
        Parent rootError;
        Scene sceneError;
        Stage stageError;
        switch (errorType) {
            case 0:
                rootError = FXMLLoader.load(getClass().getResource("controllers/errorPopup.fxml")); //TODO - Cambiar este loader por el de error general
			    sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);
                break;
            case 1:
                rootError = FXMLLoader.load(getClass().getResource("controllers/errorPopup.fxml")); //TODO - Cambiar este loader por el de fecha futura
			    sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);
                break;
            default:
                break;
        }
    }
}

