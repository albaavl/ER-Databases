package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

import db.jdbc.SQL;
import db.jpa.JPAUserManager;
import db.pojos.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
// import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
// import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pojos.users.Role;
import pojos.users.User;

public class AdStaffMenuController implements Initializable {
    
    private static SQL jdbc;
    private static JPAUserManager userman;
    private ErrorPopup ErrorPopup = new application.controllers.ErrorPopup();

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
    @FXML
    Pane paneDeletePatient;
    @FXML
    Pane paneDeleteWorker;

    //welcome screen
    @FXML
    Pane paneWelcomeView;

    public void displayWelcomeText(String name, SQL databasecontroller, JPAUserManager userManager) {
        jdbc = databasecontroller;
        userman = userManager;
        adStaffMenuWelcomeText.setText("FUCK YEAH " + name +  " get fkd.\n It works!");
        hideAll();
        paneWelcomeView.setVisible(true);
        paneWelcomeView.setDisable(false);
    }
    /**
     * hide+disable every pane
     */
    private void hideAll(){

        paneWelcomeView.setVisible(false);
        paneWelcomeView.setDisable(true);
        //PatientViewOptions
        paneCreatePatientView.setVisible(false);
        paneCreatePatientView.setDisable(true);
        paneChangePatientDataView.setVisible(false);
        paneChangePatientDataView.setDisable(true);
        paneAssignANewDoctorView.setVisible(false);
        paneAssignANewDoctorView.setDisable(true);
        paneDeletePatient.setVisible(false);
        paneDeletePatient.setDisable(true);
        //WorkerViewOptions
        paneCreateWorkerView.setVisible(false);
        paneCreateWorkerView.setDisable(true);
        paneChangeWorkerDataView.setVisible(false);
        paneChangeWorkerDataView.setDisable(true);
        paneEditShiftView.setVisible(false);
        paneEditShiftView.setDisable(true);
        paneDeleteWorker.setVisible(false);
        paneDeleteWorker.setDisable(true);

    }

    public void displayCreatePatientView(ActionEvent aEvent) {
        hideAll();
        resetCreatePatientScene();
        paneCreatePatientView.setDisable(false);
        paneCreatePatientView.setVisible(true);

    }
    public void displayChangePatientDataView(ActionEvent aEvent) {
        hideAll();
        resetCreatePatientScene();
        paneChangePatientDataView.setDisable(false);
        paneChangePatientDataView.setVisible(true);
    }
    public void displayAssignANewDoctorView(ActionEvent aEvent) {
        hideAll();
        resetCreatePatientScene();
        paneAssignANewDoctorView.setDisable(false);
        paneAssignANewDoctorView.setVisible(true);
    }

    public void displayCreateWorkerView(ActionEvent actionEvent) {
        hideAll();
        resetCreatePatientScene();
        paneCreateWorkerView.setDisable(false);
        paneCreateWorkerView.setVisible(true);
    }
    public void displayChangeWorkerView(ActionEvent actionEvent) {
        hideAll();
        resetCreatePatientScene();
        paneChangePatientDataView.setDisable(false);
        paneChangeWorkerDataView.setVisible(true);
    }
    public void displayEditShiftView(ActionEvent actionEvent) {
        hideAll();
        resetCreatePatientScene();
        paneEditShiftView.setDisable(false);
        paneEditShiftView.setVisible(true);
    }

    public void displayDeletePatient(ActionEvent aEvent) {
        hideAll();
        resetCreatePatientScene();
        paneDeletePatient.setDisable(false);
        paneDeletePatient.setVisible(true);
    }
    public void displayDeleteWorker(ActionEvent aEvent) {
        hideAll();
        resetCreatePatientScene();
        paneDeleteWorker.setDisable(false);
        paneDeleteWorker.setVisible(true);
    }

    //Patient options fxml objects

    @FXML
    private TextField patientIdTextField;
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
    @FXML
    private DatePicker checkInDatePicker;
    @FXML 
    private ComboBox<String> hospitalizedChoiceBox;
    private String[] hospOptionStrings = {"Yes","No"};
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
    private TextField workerNameTextField;
    @FXML
    private TextField workerSurnameTextField;
    @FXML 
    private ComboBox<String> workerTypeComboBox;
    // private String[] workerTypeStrings = {"Doctor", "Nurse", "Administation Staff", "Technician"}; //TODO - creo q technician sobra mucho pq era el de las ambulance y nunca lo llegamos a quitar, not sure tho

    //Initialize function

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        bloodTypeChoiceBox.getItems().addAll(bloodTypeStrings);
        hospitalizedChoiceBox.getItems().addAll(hospOptionStrings);
        //workerTypeComboBox.getItems().addAll(workerTypeStrings);
    }

    //Menu functions

    public void createPatient(ActionEvent actionEvent) throws Exception { //TODO - quitar strings de prueba jej

        Patient p = new Patient();

        try {
            int patientId = Integer.parseInt( patientIdTextField.getText());
            p.setMedicalCardId(patientId);
        } catch (Exception e) {
            ErrorPopup.errorPopup(4);
            return;
        }

        String name = nameTextField.getText();
        if (name == "") {
            ErrorPopup.errorPopup(2);
            return;
        }
        p.setPatientName(name);
        System.out.println(name);
        
        String surname = surnameTextField.getText();
        if (surname == "") {
            ErrorPopup.errorPopup(2);
            return;
        } 
        p.setPatientSurname(surname);
        System.out.println(surname);

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


        if( !bloodTypeChoiceBox.getSelectionModel().isEmpty() ){
            String bloodType = bloodTypeChoiceBox.getValue(); 
            p.setBloodType(bloodType);
            System.out.println(bloodType);
        } else {
            System.out.println("aqui no hay na (blood)");
            ErrorPopup.errorPopup(2);
            return;
        }
        
        if(birthDatePicker.getEditor().getText() == ""){
            ErrorPopup.errorPopup(2);
            return;
        }

        Date bDate = Date.valueOf(birthDatePicker.getValue());
        if ((bDate.before(Date.valueOf(LocalDate.now()))) || bDate.equals(Date.valueOf(LocalDate.now()))) {
            p.setbDate(bDate);
            System.out.println("yay");
            System.out.println(bDate.toString());
        } else {
            ErrorPopup.errorPopup(1);
            System.out.println("nain");
            return;
        }

        if(checkInDatePicker.getEditor().getText() == ""){
            ErrorPopup.errorPopup(2);
            return;
        }

        Date cInDate = Date.valueOf(checkInDatePicker.getValue());
        if ((cInDate.before(Date.valueOf(LocalDate.now()))) || cInDate.equals(Date.valueOf(LocalDate.now()))) {
            p.setCheckInDate(cInDate);
            System.out.println("yay");
            System.out.println(cInDate.toString());
        } else {
            ErrorPopup.errorPopup(3);
            System.out.println("nain");
            return;
        }

        if( !hospitalizedChoiceBox.getSelectionModel().isEmpty() ){
            String hosp = hospitalizedChoiceBox.getValue(); 
            if (hosp.equalsIgnoreCase("yes")) {
                p.setHospitalized(true);
            } else {
                p.setHospitalized(false);
            }
            System.out.println(hosp);
        } else {
            System.out.println("aqui no hay na (hosp)");
            ErrorPopup.errorPopup(2);
            return;
        }


        String allergies = allergiesTextArea.getText();
        if (allergies == "") {
            allergies = null;
        }
        p.setAllergieType(allergies);
        System.out.println(allergies);

        String address = addressTextField.getText();
        if (address == "") {
            ErrorPopup.errorPopup(2);
            return;
        }
        p.setPatientAddress(address);
        System.out.println(address);

        
        jdbc.addPatient(p);

        String[] uspassw = register(p.getPatientName(),p.getPatientSurname(), p.getMedicalCardId(), 1);

        addPatientSuccess(uspassw);

        // addDoctorToPatientPopup(); 

        resetCreatePatientScene();
    }   

    //LinkDocPopup Stuff here

    public void addDoctorToPatientPopup() throws IOException {
        FXMLLoader loaderLinkDoc;
        Parent rootLinkDoc;
        Scene sceneLinkDoc;
        Stage stageLinkDoc;
        LinkDocPopupController linkDocPopupController;

        loaderLinkDoc = new FXMLLoader(getClass().getResource("linkDocPopup.fxml")); 
        rootLinkDoc = loaderLinkDoc.load();
        linkDocPopupController = loaderLinkDoc.getController();
        linkDocPopupController.setAdStaffController();

        sceneLinkDoc = new Scene(rootLinkDoc);
        stageLinkDoc = new Stage();
        stageLinkDoc.setScene(sceneLinkDoc);

        Image icon = new Image("application/images/healthcare.png");
        stageLinkDoc.getIcons().add(icon);	

        stageLinkDoc.setResizable(false);
        stageLinkDoc.setTitle("Link a doctor to this patient");
        stageLinkDoc.show();
    }

    //Success! Stuff here

    private void addPatientSuccess(String[] uspass) throws IOException {
        FXMLLoader loaderAddPatientSuccess;
        Parent rootAddPatientSuccess;
        Scene sceneAddPatientSuccess;
        Stage stageAddPatientSuccess;
        AddPatientSuccessController addPatientSuccessController;

        loaderAddPatientSuccess = new FXMLLoader(getClass().getResource("AddPatientSuccess.fxml"));
        rootAddPatientSuccess = loaderAddPatientSuccess.load();
        addPatientSuccessController = loaderAddPatientSuccess.getController();
        addPatientSuccessController.setAdStaffController();
        addPatientSuccessController.setUsernamePassword(uspass[0], uspass[1]);

        sceneAddPatientSuccess = new Scene(rootAddPatientSuccess);
        stageAddPatientSuccess = new Stage();
        stageAddPatientSuccess.setScene(sceneAddPatientSuccess);

        Image icon = new Image("application/images/healthcare.png");
        stageAddPatientSuccess.getIcons().add(icon);	

        stageAddPatientSuccess.setResizable(false);
        stageAddPatientSuccess.setTitle("Success");
        stageAddPatientSuccess.show();

    }

    //HardResetScenes
    
    private void resetCreatePatientScene() {
        patientIdTextField.clear();
        nameTextField.clear();
        surnameTextField.clear();
        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(false);
        bloodTypeChoiceBox.getSelectionModel().clearSelection();
        hospitalizedChoiceBox.getSelectionModel().clearSelection();
        birthDatePicker.setValue(null);
        birthDatePicker.getEditor().clear();
        checkInDatePicker.setValue(null);
        checkInDatePicker.getEditor().clear();
        allergiesTextArea.clear();
        addressTextField.clear();
    }

    //Controller stuff

    public static AdStaffMenuController thisAdStaffMenuController;

    public void setAdStaffController( AdStaffMenuController adStaffMenuController ) {
        thisAdStaffMenuController = adStaffMenuController;
    }

    public static AdStaffMenuController passAdStaffMenuController() {
        return thisAdStaffMenuController;
    }

    //Login stuff

    /**
     * ROLES ID:
     * <p>{@code 1} patient
     * <p>{@code 2} medicalStaff
     * <p>{@code 3} adStaff
     * @param name self explanatory
     * @param surname I mean...
     * @param idUser ehem
     * @param id look up
     * @throws Exception
     */
    private static String[] register(String name, String surname, Integer idUser, int id) throws Exception {
		//autogenerateID
		String username = ""+name.charAt(0)+"."+surname+""+Integer.valueOf(surname.charAt(1));
		//autogenerated password
		String[] symbols = {"0", "1", "9", "7", "K", "Q", "a", "b", "c", "U","w","3","0"};
        int length = 14;
        Random random;
            random = SecureRandom.getInstanceStrong();
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                 int indexRandom = random.nextInt ( symbols.length );
                 sb.append( symbols[indexRandom] );
            }
            String password = sb.toString();
		Role role = userman.getRole(id);
		//generate the hash
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		User user = new User(username, hash, role);
		userman.newUser(user);
		System.out.println("The autogenerated username is:"+ username);
		System.out.println("The autogenerated password is:"+ password);
		if(user.getRole().getRole().equalsIgnoreCase("patient")) {
			jdbc.createLinkUserPatient(user.getUserId(), idUser); 
		} else if(user.getRole().getRole().equalsIgnoreCase("medstaff")||user.getRole().getRole().equalsIgnoreCase("adstaff")) {
			jdbc.createLinkUserWorker(user.getUserId(), idUser); 
		} 
        String[] userpass = {username, password};
        return userpass;
	} 

}

