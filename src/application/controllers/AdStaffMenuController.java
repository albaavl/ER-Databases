package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import db.jdbc.SQL;
import db.pojos.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    
    private SQL jdbc;
    private ErrorPopup ErrorPopup;

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

    public void displayWelcomeText(String name, SQL databasecontroller) {
        jdbc = databasecontroller;
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

    //Patient options fxml objects

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
    @FXML
    private TextArea allergiesTextArea;
    @FXML
    private TextField addressTextField;

    //Worker options fxml objects

    @FXML 
    private ComboBox<String> workerTypeComboBox;
    private String[] workerTypeStrings = {}; //TODO - @me poner las opciones aqui :)

    //Initialize function

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        bloodTypeChoiceBox.getItems().addAll(bloodTypeStrings);
        //workerTypeComboBox.getItems().addAll(workerTypeStrings);
    }

    //Menu functions

    public void createPatient(ActionEvent actionEvent) throws IOException, NotBoundException { //TODO - quitar strings de prueba jej

        Patient p = new Patient();

        String name = nameTextField.getText();
        p.setPatientName(name);
        System.out.println(name);
        String surname = surnameTextField.getText();
        System.out.println(surname);
        p.setPatientSurname(surname);

        String gender = "";
        if (femaleRadioButton.isSelected()) {
            gender = "Female";
            p.setGender(gender);
        } else if (maleRadioButton.isSelected()) {
            gender = "Male";
            p.setGender(gender);
        } else {
            ErrorPopup.errorPopup(2);
            return;
        }
        System.out.println(gender);


        if( bloodTypeChoiceBox.getSelectionModel().isEmpty() ){
            String bloodType = bloodTypeChoiceBox.getValue(); //TODO - ver q hace si no seleccionas nada.
            p.setBloodType(bloodType);
            System.out.println(bloodType);
        } else {
            System.out.println("aqui no hay na (blood)");
            ErrorPopup.errorPopup(2);
        }

        Date bDate = Date.valueOf(birthDatePicker.getValue());  //TODO - añadir algo para controlar q pones algo en este coso.
        if ((bDate.before(Date.valueOf(LocalDate.now()))) || bDate.equals(Date.valueOf(LocalDate.now()))) {
            p.setbDate(bDate);
            System.out.println("yay");
            System.out.println(bDate.toString());
        } else {
            ErrorPopup.errorPopup(1);
            System.out.println("nain");
            return;
        }


        String allergies = allergiesTextArea.getText(); //TODO - ver q pasa si no pones nada
        p.setAllergieType(allergies);
        System.out.println(allergies);

        String address = addressTextField.getText(); //TODO - ver q pasa si no pones nada
        p.setPatientAddress(address);
        System.out.println(address);

        // jdbc.addPatient(p);

        //Creo q voy a hacer q una vez salga el popup de añadido correctamente, mediante un boton de aceptar te pregunte si quieres añadir un doc
        //en vez de hacer dos popup a la vez

        //Preguntar al subnormal de turno si quiere asignar un médico al paciente #popup
        // addDoctorToPatientPopup(); 

        //Congratulaciones has hecho las cosas bien y el paciente esta en la db! #popup y reset de todas las opciones

        //Con esto debería funcionar, not sure tho
        // nameTextField.clear();
        // surnameTextField.clear();
        // maleRadioButton.setSelected(false);
        // femaleRadioButton.setSelected(false);
        // bloodTypeChoiceBox.getSelectionModel().clearSelection();
        // birthDatePicker.setValue(null);
        // birthDatePicker.getEditor().clear();
        // allergiesTextArea.clear();
        // addressTextField.clear();

    }   

    //LinkDocPopup Stuff here

    //objects on linkDocPopup
    @FXML
    private Button yesLinkDocPopupButton;
    @FXML
    private Button noLinkDocPopupButton;
    //methods for linkDocPopup

    /**
     * Used to change the view into Assign a new doctor when user clicks yes.
     * closes window when user clicks one of the options
     * @param aEvent
     */
    private void onYesButton(ActionEvent aEvent) {
        displayAssignANewDoctorView(aEvent);
        Stage stage = (Stage) yesLinkDocPopupButton.getScene().getWindow();
        stage.close();
    }

    /**
     * closes window when user clicks one of the options. Does nothing else at all xD
     * @param aEvent
     */
    private void onNoButton(ActionEvent aEvent) {
        Stage stage = (Stage) noLinkDocPopupButton.getScene().getWindow();
        stage.close();
    }

    private void addDoctorToPatientPopup() throws IOException {
        FXMLLoader loaderLinkDoc;
        Parent rootLinkDoc;
        Scene sceneLinkDoc;
        Stage stageLinkDoc;
        // LinkDocPopupController linkDocPopupController; //TODO - fuck me idk what to do w dis y ahora mismo me sobra mazo xD

        loaderLinkDoc = new FXMLLoader(getClass().getResource("controllers/linkDocPopup.fxml")); 
        rootLinkDoc = loaderLinkDoc.load();
        // linkDocPopupController = loaderLinkDoc.getController();

        sceneLinkDoc = new Scene(rootLinkDoc);
        stageLinkDoc = new Stage();
        stageLinkDoc.setScene(sceneLinkDoc);
        stageLinkDoc.setTitle("Link a doctor to this patient");
        stageLinkDoc.show();
    }

}

