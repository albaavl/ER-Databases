package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import db.jdbc.SQL;
import db.jpa.JPAUserManager;
import db.pojos.MedicalTest;
import db.pojos.Patient;
import db.pojos.Shift;
import db.pojos.Worker;
import db.xml.XMLManager;
import pojos.users.Role;
import pojos.users.User;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdStaffMenuController implements Initializable { 
    
    private static SQL jdbc;
    private static JPAUserManager userman;
    private ErrorPopup ErrorPopup = new ErrorPopup();
    private SuccessPopup SuccessPopup = new SuccessPopup();

    @FXML
    Text adStaffMenuWelcomeText;
    //Patient menu views
    @FXML
    Pane paneCreatePatientView;
    @FXML
    Pane paneChangePatientDataView; //Here u get the patient
    @FXML
    Pane paneEditPatientDataView; //Here u update the patient data
    @FXML
    Pane paneAssignANewDoctorView; 
    @FXML
    Pane paneCreateMedicalTest;
    //Worker menu views
    @FXML
    Pane paneCreateWorkerView;
    @FXML
    Pane paneChangeWorkerDataView; //Select a worker to update
    @FXML
    Pane paneEditWorkerDataView; //Actual edit
    @FXML
    Pane paneChangeShiftView; //Select a woker and create a new or update an existing shift
    @FXML
    Pane paneEditShiftView; //Actual edit/create
    @FXML
    Pane paneDeletePatient; 
    @FXML
    Pane paneDeleteWorker;
    //Select Views
    @FXML
    Pane paneSelectPatient;
    @FXML
    Pane paneSelectWorker;
    @FXML
    Pane paneSelectShift;
    @FXML
    Pane paneCreateShift;
    //welcome screen
    @FXML
    Pane paneWelcomeView;

    //PATIENT TABLES
    private TableColumn<Patient,String> columnPatientName = new TableColumn<>("Name");
    private TableColumn<Patient,String> columnPatientSurname = new TableColumn<>("Surname");
    private TableColumn<Patient,String> columnPatientId = new TableColumn<>("Medical Card");
    private TableColumn<Patient,String> columnPatientGender = new TableColumn<>("Gender");
    private TableColumn<Patient,String> columnPatientBloodtype = new TableColumn<>("Blood Type");
    private TableColumn<Patient,String> columnPatientAllergies = new TableColumn<>("Allergies");
    private TableColumn<Patient,String> columnPatientAddress = new TableColumn<>("Address");
    private TableColumn<Patient,String> columnPatientBirthDate = new TableColumn<>("Birthdate");
    private TableColumn<Patient,String> columnPatientCheckInDate = new TableColumn<>("Check-in");
    private TableColumn<Patient,String> columnPatientHospitalized = new TableColumn<>("Hospitalized");
    //WORKER TABLES
    private TableColumn<Worker,String> columnWorkerName = new TableColumn<>("Name");
    private TableColumn<Worker,String> columnWorkerSurname = new TableColumn<>("Surname");
    private TableColumn<Worker,String> columnWorkerId = new TableColumn<>("Id");
    private TableColumn<Worker,String> columnWorkerSpecialtyId = new TableColumn<>("Specialty");
    private TableColumn<Worker,String> columnWorkerType = new TableColumn<>("Worker Type");
    //SHIFT TABLES 
    private TableColumn<Shift,String> columnShiftId = new TableColumn<>("Id");
    private TableColumn<Shift,String> columnShiftRoomER = new TableColumn<>("Room ER");
    private TableColumn<Shift,String> columnShiftTurn = new TableColumn<>("Turn");
    private TableColumn<Shift,String> columnShiftDate = new TableColumn<>("Date");
    private TableColumn<Shift,String> columnShiftWorkerId = new TableColumn<>("Worker assignated");

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

    //Edit patient objects
    @FXML
    private TextField editPatientIdTextField;
    @FXML
    private ToggleGroup genderRadioButtonEdit;
    @FXML
    private RadioButton editMaleRadioButton;
    @FXML
    private RadioButton editFemaleRadioButton;
    @FXML
    private TextField editNameTextField;
    @FXML
    private TextField editSurnameTextField;
    @FXML
    private DatePicker editBirthDatePicker;
    @FXML
    private DatePicker editCheckInDatePicker;
    @FXML 
    private ComboBox<String> editHospitalizedChoiceBox;
    @FXML
    private ComboBox<String> editBloodTypeChoiceBox;
    @FXML
    private TextArea editAllergiesTextArea;
    @FXML
    private TextField editAddressTextField;

    //Worker options fxml objects
    @FXML
    private TextField workerNameTextField;
    @FXML
    private TextField workerSurnameTextField;
    @FXML
    private TextField workerSpecialtyTextField;
    @FXML 
    private ComboBox<String> workerTypeComboBox;
    private String[] workerTypeStrings = {"Doctor", "Nurse", "Administration Staff", "Technician"}; 

    //CreateShift View Stuff
    @FXML
    private TextField rooomERTextField;
    @FXML
    private DatePicker shiftDatePicker;
    @FXML
    private ComboBox<String> shiftTurnComboBox;
    @FXML
    private TextField editRooomERTextField;
    @FXML
    private DatePicker editShiftDatePicker;
    @FXML
    private ComboBox<String> editShiftTurnComboBox;
    
    private String[] shiftTurnStrings = {"Morning", "Afternoon", "Night"};

    public void displayWelcomeText(String name, SQL databasecontroller, JPAUserManager userManager) {
        jdbc = databasecontroller;
        userman = userManager;

        adStaffMenuWelcomeText.setText("Welcome " + name +  ", thank you for using Quiron's ER database.\n"
                                      + "On the right side you can find all of your aviable options.");
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
        paneEditPatientDataView.setVisible(false);
        paneEditPatientDataView.setDisable(true);
        paneAssignANewDoctorView.setVisible(false);
        paneAssignANewDoctorView.setDisable(true);
        paneDeletePatient.setVisible(false);
        paneDeletePatient.setDisable(true);
        paneSelectPatient.setVisible(false);
        paneSelectPatient.setDisable(true);
        paneCreateMedicalTest.setVisible(false);
        paneCreateMedicalTest.setDisable(true);
        //WorkerViewOptions
        paneCreateWorkerView.setVisible(false);
        paneCreateWorkerView.setDisable(true);
        paneEditPatientDataView.setVisible(false);
        paneEditPatientDataView.setDisable(true);
        paneChangeWorkerDataView.setVisible(false);
        paneChangeWorkerDataView.setDisable(true);
        paneChangeShiftView.setVisible(false);
        paneChangeShiftView.setDisable(true);
        paneEditShiftView.setVisible(false);
        paneEditShiftView.setDisable(true);
        paneDeleteWorker.setVisible(false);
        paneDeleteWorker.setDisable(true);
        paneSelectWorker.setVisible(false);
        paneSelectWorker.setDisable(true);
        paneSelectShift.setDisable(true);
        paneSelectShift.setVisible(false);
        paneCreateShift.setDisable(true);
        paneCreateShift.setVisible(false);

    }
    private void resetAll(){
        resetCreatePatientScene();
        clearCurrentEditPatientTable();
    }

    public void displayWelcomeView() {
        hideAll();
        resetAll();
        paneWelcomeView.setDisable(false);
        paneWelcomeView.setVisible(true);
    }

    public void displayCreatePatientView(ActionEvent aEvent) {
        hideAll();
        resetAll();
        paneCreatePatientView.setDisable(false);
        paneCreatePatientView.setVisible(true);

    }

    @FXML
    private Button editPatientSelectPatient;
    @FXML
    private Button editPatientChangePatient;

    public void displayChangePatientDataView() {
        hideAll();
        resetAll();

        editPatientSelectPatient.setVisible(true);
        editPatientChangePatient.setVisible(false);
        editPatientChangePatient.setDisable(true);
        editPatientSelectPatient.setDisable(false);

        if(currentSelectedPatient != null){
            setCurrentEditPatientTable();
            editPatientSelectPatient.setVisible(false);
            editPatientChangePatient.setVisible(true);
            editPatientChangePatient.setDisable(false);
            editPatientSelectPatient.setDisable(true);
        }

        paneChangePatientDataView.setDisable(false);
        paneChangePatientDataView.setVisible(true);
    }

    @FXML
    private Button fromDeleteSelectPatientButton;
    @FXML
    private Button fromEditSelectPatientButton;
    @FXML
    private Button fromLinkDocSelectPatientButton;

    private void hideAllPatientSelectButton() {
        fromDeleteSelectPatientButton.setVisible(false);
        fromDeleteSelectPatientButton.setDisable(true);
        fromEditSelectPatientButton.setVisible(false);
        fromEditSelectPatientButton.setDisable(true);
        fromLinkDocSelectPatientButton.setVisible(false);
        fromLinkDocSelectPatientButton.setDisable(true);
        fromMedTestSelectPatientButton.setVisible(false);
        fromMedTestSelectPatientButton.setDisable(true);
    }

    public void displaySelectPatientFromDelete() throws IOException{
        hideAll();
        resetAll();

        try {
            setPatientTables();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPopup.errorPopup(0);
        }

        paneSelectPatient.setDisable(false);
        paneSelectPatient.setVisible(true);
        
        hideAllPatientSelectButton();
        fromDeleteSelectPatientButton.setVisible(true);
        fromDeleteSelectPatientButton.setDisable(false);

    }
    public void displaySelectPatientFromEdit() throws IOException{
        hideAll();
        resetAll();
    
        try {
            setPatientTables();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPopup.errorPopup(0);
        }

        paneSelectPatient.setDisable(false);
        paneSelectPatient.setVisible(true);

        hideAllPatientSelectButton();
        fromEditSelectPatientButton.setVisible(true);
        fromEditSelectPatientButton.setDisable(false);

    }
    public void displaySelectPatientFromLinkDoc() throws IOException{
        hideAll();
        resetAll();

        try {
            setPatientTables();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPopup.errorPopup(0);
        }

        paneSelectPatient.setDisable(false);
        paneSelectPatient.setVisible(true);
        
        hideAllPatientSelectButton();
        fromLinkDocSelectPatientButton.setVisible(true);
        fromLinkDocSelectPatientButton.setDisable(false);

    }
    
    @FXML
    private Button fromDeleteSelectWorkerButton;
    @FXML
    private Button fromEditSelectWorkerButton;
    @FXML
    private Button fromLinkDocSelectWorkerButton;
    @FXML
    private Button fromEditShiftSelectWorkerButton;

    private void hideAllWorkerSelectButton() {
        fromDeleteSelectWorkerButton.setVisible(false);
        fromDeleteSelectWorkerButton.setDisable(true);
        fromEditSelectWorkerButton.setVisible(false);
        fromEditSelectWorkerButton.setDisable(true);
        fromLinkDocSelectWorkerButton.setVisible(false);
        fromLinkDocSelectWorkerButton.setDisable(true);
        fromEditShiftSelectWorkerButton.setVisible(false);
        fromEditShiftSelectWorkerButton.setDisable(true);
    }

    
    public void displaySelectWorkerFromDelete() throws IOException {
        hideAll();
        resetAll();

        try {
            setWorkerTables();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPopup.errorPopup(0);
        } 

        paneSelectWorker.setVisible(true);
        paneSelectWorker.setDisable(false);

        hideAllWorkerSelectButton();
        fromDeleteSelectWorkerButton.setVisible(true);
        fromDeleteSelectWorkerButton.setDisable(false);
    }
    public void displaySelectWorkerFromEdit() throws IOException {
        hideAll();
        resetAll();

        try {
            setWorkerTables();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPopup.errorPopup(0);
        } 

        paneSelectWorker.setVisible(true);
        paneSelectWorker.setDisable(false);

        hideAllWorkerSelectButton();
        fromEditSelectWorkerButton.setVisible(true);
        fromEditSelectWorkerButton.setDisable(false);
    }
    public void displaySelectWorkerFromLinkDoc() throws IOException {
        hideAll();
        resetAll();

        try {
            setWorkerTables();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPopup.errorPopup(0);
        } 

        paneSelectWorker.setVisible(true);
        paneSelectWorker.setDisable(false);

        hideAllWorkerSelectButton();
        fromLinkDocSelectWorkerButton.setVisible(true);
        fromLinkDocSelectWorkerButton.setDisable(false);
    }
    public void displaySelectWorkerFromEditShift() throws IOException {
        hideAll();
        resetAll();

        try {
            setWorkerTables();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPopup.errorPopup(0);
        } 

        paneSelectWorker.setVisible(true);
        paneSelectWorker.setDisable(false);

        hideAllWorkerSelectButton();
        fromEditShiftSelectWorkerButton.setVisible(true);
        fromEditShiftSelectWorkerButton.setDisable(false);
    }

    public void displaySelectShifts() throws IOException{

        if(currentSelectedWorker != null){
            hideAll();
            resetAll();
        
            try {
                setShiftTables();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ErrorPopup.errorPopup(12);
            return;
        }

        paneSelectShift.setVisible(true);
        paneSelectShift.setDisable(false);
    }

    public void displayAssignANewDoctorView() {
        hideAll();
        resetAll();

        if (currentSelectedPatient != null) {
            setLinkDocSelectedPatientTable();
        }

        if (currentSelectedWorker != null) {
            setLinkDocSelectedWorkerTable();
        }        

        paneAssignANewDoctorView.setDisable(false);
        paneAssignANewDoctorView.setVisible(true);
    }

    public void displayCreateWorkerView(ActionEvent actionEvent) {
        hideAll();
        resetAll();
        paneCreateWorkerView.setDisable(false);
        paneCreateWorkerView.setVisible(true);
    }

    @FXML
    private Button editWorkerSelectButton;
    @FXML
    private Button editWorkerChangeButton;

    public void displayChangeWorkerDataView() {
        hideAll();
        resetAll();

        editWorkerSelectButton.setVisible(true);
        editWorkerChangeButton.setVisible(false);
        editWorkerChangeButton.setDisable(true);
        editWorkerSelectButton.setDisable(false);

        if(currentSelectedWorker != null){
            setCurrentEditWorkerTable();
            editWorkerSelectButton.setVisible(false);
            editWorkerChangeButton.setVisible(true);
            editWorkerChangeButton.setDisable(false);
            editWorkerSelectButton.setDisable(true);
        }

        paneChangeWorkerDataView.setDisable(false);
        paneChangeWorkerDataView.setVisible(true);
    }

    public void displayEditWorkerView() {
        hideAll();
        resetAll();
        paneEditWorkerDataView.setDisable(false);
        paneEditWorkerDataView.setVisible(true);
    }
    
    @FXML
    private Button editShiftSelectButton;
    @FXML
    private Button editShiftChangeButton;
    @FXML
    private Button editShiftNewShiftButton;
    @FXML
    private Button editShiftModifyExistingButton;

    public void displayChangeShiftDataView() {
        hideAll();
        resetAll();

        editShiftSelectButton.setVisible(true);
        editShiftNewShiftButton.setVisible(true);
        editShiftChangeButton.setVisible(false);
        editShiftModifyExistingButton.setVisible(false);
        editShiftChangeButton.setDisable(true);
        editShiftModifyExistingButton.setDisable(true);
        editShiftNewShiftButton.setDisable(false);
        editShiftSelectButton.setDisable(false);

        if(currentSelectedWorker != null){
            try {
                setCurrentWorkerEditShiftTables();
            } catch (Exception e) {
                e.printStackTrace();
            }
            editShiftChangeButton.setVisible(true);
            editShiftChangeButton.setDisable(false);
            editShiftSelectButton.setDisable(true);
            editShiftSelectButton.setVisible(false);
        } else {
            currentSelectedWorkerEditShiftTableView.getItems().clear();
        }

        if(currentSelectedShift != null){
            try {
                setCurrentWorkerEditShiftTables();
            } catch (SQLException | NotBoundException e) {
                e.printStackTrace();
            }
            setCurrentEditShiftTable(); 
            editShiftNewShiftButton.setVisible(false);
            editShiftModifyExistingButton.setVisible(true);
            editShiftModifyExistingButton.setDisable(false);
            editShiftNewShiftButton.setDisable(true);
    
        } else {
            currentSelectedShiftTableView.getItems().clear();
        }
        
        paneChangeShiftView.setDisable(false);
        paneChangeShiftView.setVisible(true);
    }

    public void selectShift() throws IOException { 
        Integer shiftId = null;
        currentSelectedShift = null;

        try {
            shiftId = Integer.parseInt(selectShiftTextField.getText());
            currentSelectedShift = jdbc.selectShift(shiftId);
        } catch (Exception e) {
            ErrorPopup.errorPopup(4);
            return;
        }
        if(currentSelectedShift == null){  
            ErrorPopup.errorPopup(4);
            return;
        }

        selectShiftTextField.clear();
        hideAll();
        displayChangeShiftDataView();
        
    }

    public void displayCreateShift() throws IOException{ 

        if(currentSelectedWorker == null) {
            ErrorPopup.errorPopup(12);
            return;
        }

        hideAll();
        resetAll();
        paneCreateShift.setDisable(false);
        paneCreateShift.setVisible(true);
    }


    @FXML
    private Button deletePatientSelectPatient;
    @FXML
    private Button deletePatientChangePatient;

    public void displayDeletePatient() {
        hideAll();
        resetAll();
        deletePatientSelectPatient.setVisible(true);
        deletePatientChangePatient.setVisible(false);
        deletePatientChangePatient.setDisable(true);
        deletePatientSelectPatient.setDisable(false);

        if(currentSelectedPatient != null){

            setCurrentDeletePatientTable();
            deletePatientSelectPatient.setVisible(false);
            deletePatientChangePatient.setVisible(true);
            deletePatientChangePatient.setDisable(false);
            deletePatientSelectPatient.setDisable(true);
        }

        paneDeletePatient.setDisable(false);
        paneDeletePatient.setVisible(true);
    }

    @FXML
    private Button deleteWorkerSelect;
    @FXML
    private Button deleteWorkerChange;

    public void displayDeleteWorker() {
        hideAll();
        resetAll();
        deleteWorkerSelect.setVisible(true);
        deleteWorkerSelect.setDisable(false);
        deleteWorkerChange.setVisible(false);
        deleteWorkerChange.setDisable(true);
        
        if(currentSelectedWorker != null){
            
            setCurrentDeleteWorkerTable();
            deleteWorkerSelect.setVisible(false);
            deleteWorkerSelect.setDisable(true);
            deleteWorkerChange.setVisible(true);
            deleteWorkerChange.setDisable(false);
        }

        paneDeleteWorker.setDisable(false);
        paneDeleteWorker.setVisible(true);
    }

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    public void logOut(ActionEvent actionEvent) throws IOException {
        try {
            jdbc.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorPopup.errorPopup(0);
            return;
        }
        userman.disconnect();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("logInMenu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //Initialize function

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        bloodTypeChoiceBox.getItems().addAll(bloodTypeStrings);
        hospitalizedChoiceBox.getItems().addAll(hospOptionStrings);
        editBloodTypeChoiceBox.getItems().addAll(bloodTypeStrings);
        editHospitalizedChoiceBox.getItems().addAll(hospOptionStrings);
        workerTypeComboBox.getItems().addAll(workerTypeStrings);
        shiftTurnComboBox.getItems().addAll(shiftTurnStrings);
        editShiftTurnComboBox.getItems().addAll(shiftTurnStrings);

        columnPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        columnPatientSurname.setCellValueFactory(new PropertyValueFactory<>("patientSurname"));
        columnPatientAllergies.setCellValueFactory(new PropertyValueFactory<>("allergieType"));
        columnPatientGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnPatientBloodtype.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        columnPatientAddress.setCellValueFactory(new PropertyValueFactory<>("patientAddress"));
        columnPatientId.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMedicalCardId())));
        columnPatientBirthDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getbDate().toString()));
        columnPatientCheckInDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCheckInDate().toString()));
        columnPatientHospitalized.setCellValueFactory(data -> new SimpleStringProperty(Boolean.toString(data.getValue().getHospitalized()))); 
    
        columnWorkerId.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getWorkerId())));
        columnWorkerName.setCellValueFactory(new PropertyValueFactory<>("workerName"));
        columnWorkerSurname.setCellValueFactory(new PropertyValueFactory<>("workerSurname"));
        columnWorkerSpecialtyId.setCellValueFactory(new PropertyValueFactory<>("specialtyId"));
        columnWorkerType.setCellValueFactory(new PropertyValueFactory<>("typeWorker"));
        
        columnShiftId.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getShiftId())));
        columnShiftWorkerId.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getWorker().getWorkerId())));
        columnShiftDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));
        columnShiftTurn.setCellValueFactory(new PropertyValueFactory<>("turn"));
        columnShiftRoomER.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getRoom())));
    }

    //Menu functions

    public void createPatient(ActionEvent actionEvent) throws Exception { 

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
        
        String surname = surnameTextField.getText();
        if (surname == "") {
            ErrorPopup.errorPopup(2);
            return;
        } 
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


        if( !bloodTypeChoiceBox.getSelectionModel().isEmpty() ){
            String bloodType = bloodTypeChoiceBox.getValue(); 
            p.setBloodType(bloodType);
        } else {
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
        } else {
            ErrorPopup.errorPopup(1);
            return;
        }

        if(checkInDatePicker.getEditor().getText() == ""){
            ErrorPopup.errorPopup(2);
            return;
        }

        Date cInDate = Date.valueOf(checkInDatePicker.getValue());
        if ((cInDate.before(Date.valueOf(LocalDate.now()))) || cInDate.equals(Date.valueOf(LocalDate.now()))) {
            p.setCheckInDate(cInDate);
        } else {
            ErrorPopup.errorPopup(3);
            return;
        }

        if( !hospitalizedChoiceBox.getSelectionModel().isEmpty() ){
            String hosp = hospitalizedChoiceBox.getValue(); 
            if (hosp.equalsIgnoreCase("yes")) {
                p.setHospitalized(true);
            } else {
                p.setHospitalized(false);
            }
        } else {
            ErrorPopup.errorPopup(2);
            return;
        }


        String allergies = allergiesTextArea.getText();
        if (allergies == "") {
            allergies = null;
        }
        p.setAllergieType(allergies);

        String address = addressTextField.getText();
        if (address == "") {
            ErrorPopup.errorPopup(2);
            return;
        }
        p.setPatientAddress(address);

        
        jdbc.addPatient(p);
        lastCreatedPatient = p;

        String[] uspassw = register(p.getPatientName(),p.getPatientSurname(), p.getMedicalCardId(), 1);

        addPatientSuccess(uspassw);

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

    //Delete Patient View

    @FXML
    private TableView<Patient> currentDeletePatientTableView;

    @SuppressWarnings("unchecked")
    private void setCurrentDeletePatientTable(){
        currentDeletePatientTableView.getItems().clear();
        currentDeletePatientTableView.getColumns().clear();
        currentDeletePatientTableView.getColumns().addAll(columnPatientId, columnPatientName, columnPatientSurname, columnPatientGender, columnPatientBloodtype, columnPatientAllergies, columnPatientBirthDate, columnPatientCheckInDate, columnPatientAddress, columnPatientHospitalized);
        currentDeletePatientTableView.getItems().add(currentSelectedPatient);
    }
    @FXML
    public void executeDeletePatient() throws IOException{
        try {
            jdbc.deletePatientByMedicalCardId(currentSelectedPatient.getMedicalCardId());
            SuccessPopup.successPopup(7);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        cancelDeletePatient();
    }
    @FXML
    public void cancelDeletePatient(){
        currentDeletePatientTableView.getItems().clear();
        currentSelectedPatient = null;
        displayDeletePatient();
    }

    //Edit patient view

    @FXML
    private TableView<Patient> currentEditPatientTableView;

    @SuppressWarnings("unchecked")
    private void setCurrentEditPatientTable(){
        currentEditPatientTableView.getItems().clear();
        currentEditPatientTableView.getColumns().clear();
        currentEditPatientTableView.getColumns().addAll(columnPatientId, columnPatientName, columnPatientSurname, columnPatientGender, columnPatientBloodtype, columnPatientAllergies, columnPatientBirthDate, columnPatientCheckInDate, columnPatientAddress, columnPatientHospitalized);
        currentEditPatientTableView.getItems().add(currentSelectedPatient);
    }
    
    public void clearCurrentEditPatientTable() {
        currentEditPatientTableView.getItems().clear();
        currentEditPatientTableView.getColumns().clear();
    }

    @FXML
    public void executeEditPatient(){

        hideAll();
        //prepare the editPatientDataView with the current data of the selected patient
        try {
            editPatientIdTextField.setText(currentSelectedPatient.getMedicalCardId().toString());
            editPatientIdTextField.setEditable(false);
            editNameTextField.setPromptText(currentSelectedPatient.getPatientName());
            editSurnameTextField.setPromptText(currentSelectedPatient.getPatientSurname());
            editBirthDatePicker.setPromptText(currentSelectedPatient.getbDate().toString());
            editCheckInDatePicker.setPromptText(currentSelectedPatient.getCheckInDate().toString());
            editBloodTypeChoiceBox.getSelectionModel().select(currentSelectedPatient.getBloodType());
            if (currentSelectedPatient.getHospitalized()) {
                editHospitalizedChoiceBox.getSelectionModel().select("Yes");
            } else {
                editHospitalizedChoiceBox.getSelectionModel().select("No");
            }
            if(currentSelectedPatient.getGender().equalsIgnoreCase("female")){
                editMaleRadioButton.setSelected(false);
                editFemaleRadioButton.setSelected(true);
            } else {
                editFemaleRadioButton.setSelected(false);
                editMaleRadioButton.setSelected(true);
            }
            editAddressTextField.setPromptText(currentSelectedPatient.getPatientAddress());
            if (currentSelectedPatient.getAllergieType() != null) {
                editAllergiesTextArea.setPromptText(currentSelectedPatient.getAllergieType());
            }

        } catch (Exception e) {
        }

        paneEditPatientDataView.setVisible(true);
        paneEditPatientDataView.setDisable(false);

    }

    @FXML
    public void editPatient() throws Exception { 

        String name = editNameTextField.getText();
        if (name == "") {
            name = null;
        }
        
        String surname = editSurnameTextField.getText();
        if (surname == "") {
            surname = null;
        } 

        String gender = "";
        if (editFemaleRadioButton.isSelected()) {
            gender = "Female";
        } else if (editMaleRadioButton.isSelected()) {
            gender = "Male";
        } else {
            gender = null;
        }

        String bloodType;
        if( !editBloodTypeChoiceBox.getSelectionModel().isEmpty() ){
            bloodType = editBloodTypeChoiceBox.getValue(); 
        } else {
            bloodType = null;
        }
        
        Date bDate;
        if(editBirthDatePicker.getEditor().getText() == ""){
            bDate = null;
        } else {
            bDate = Date.valueOf(editBirthDatePicker.getValue());
            if (!((bDate.before(Date.valueOf(LocalDate.now()))) || bDate.equals(Date.valueOf(LocalDate.now())))) {
                ErrorPopup.errorPopup(1);
                return;
            }  
        }

        Date cInDate;
        if(editCheckInDatePicker.getEditor().getText() == ""){
            cInDate = null;
        } else {
            cInDate = Date.valueOf(editCheckInDatePicker.getValue());
            if (!((cInDate.before(Date.valueOf(LocalDate.now()))) || cInDate.equals(Date.valueOf(LocalDate.now())))) {
                ErrorPopup.errorPopup(3);
                return;
            } 
    
        }

        Boolean hospitalized;
        if( !editHospitalizedChoiceBox.getSelectionModel().isEmpty() ){
            String hosp = editHospitalizedChoiceBox.getValue(); 
            if (hosp.equalsIgnoreCase("yes")) {
                hospitalized = true;
            } else {
                hospitalized = false;
            }
        } else {
            hospitalized = currentSelectedPatient.getHospitalized();
        }

        String allergies = editAllergiesTextArea.getText();
        if (allergies == "") {
            allergies = null;
        }

        String address = editAddressTextField.getText();
        if (address == "") {
            address= null;
        }

        jdbc.editPatient(currentSelectedPatient.getMedicalCardId(), name, surname, gender, bloodType, allergies, address, bDate, cInDate, hospitalized);

        SuccessPopup.successPopup(1);

        resetEditPatientScene();
        hideAll();
        currentSelectedPatient = null;
        displayChangePatientDataView();

    }

    @FXML
    public void cancelEditPatient(){
        currentEditPatientTableView.getItems().clear();
        currentSelectedPatient = null;
        displayChangePatientDataView();
    }

    private static Patient currentSelectedPatient = null;
    private static Patient lastCreatedPatient = null;
    public void setLastCreatedAsCurrent() {
        currentSelectedPatient = lastCreatedPatient;
    }
    public void setLastCreatedNull() {
        lastCreatedPatient = null;
    }

    @FXML
    private TextField selectPatientTextField;
    @FXML
    private TableView<Patient> selectPatientTableView;

    
    @SuppressWarnings("unchecked")
    private void setPatientTables() throws SQLException, NotBoundException{

        List<Patient> patientList = jdbc.selectAllPatients();
        selectPatientTableView.getItems().clear();
        selectPatientTableView.getColumns().clear();
        selectPatientTableView.getColumns().addAll(columnPatientId, columnPatientName, columnPatientSurname, columnPatientGender, columnPatientBloodtype, columnPatientAllergies, columnPatientBirthDate, columnPatientCheckInDate, columnPatientAddress, columnPatientHospitalized);
        selectPatientTableView.getItems().addAll(patientList);

    }

    @FXML
    public void selectPatientBackToDelete(ActionEvent actionEvent) throws IOException {
        
        Integer patientId = null;
        currentSelectedPatient = null;

        try {
            patientId = Integer.parseInt(selectPatientTextField.getText());
            currentSelectedPatient = jdbc.selectPatient(patientId);
        } catch (Exception e) {
            ErrorPopup.errorPopup(4);
            return;
        }
        if(currentSelectedPatient == null){  
            ErrorPopup.errorPopup(4);
            return;
        }

        selectPatientTextField.clear();
        hideAll();
        displayDeletePatient();
    }
    
    @FXML
    public void selectPatientBackToEdit(ActionEvent actionEvent) throws IOException {
        
        Integer patientId = null;
        currentSelectedPatient = null;

        try {
            patientId = Integer.parseInt(selectPatientTextField.getText());
            currentSelectedPatient = jdbc.selectPatient(patientId);
        } catch (Exception e) {
            ErrorPopup.errorPopup(4);
            return;
        }
        if(currentSelectedPatient == null){  
            ErrorPopup.errorPopup(4);
            return;
        }
        selectPatientTextField.clear();
        hideAll();
        displayChangePatientDataView();
    }
    @FXML
    public void selectPatientBackToLinkDoc(ActionEvent actionEvent) throws IOException {
        
        Integer patientId = null;
        currentSelectedPatient = null;

        try {
            patientId = Integer.parseInt(selectPatientTextField.getText()); 
            currentSelectedPatient = jdbc.selectPatient(patientId);        
        } catch (Exception e) {
            ErrorPopup.errorPopup(4);
            return;
        }
        if(currentSelectedPatient == null){  
            ErrorPopup.errorPopup(4);
            return;
        }
        selectPatientTextField.clear();
        hideAll();
        displayAssignANewDoctorView();
    }

    public void createWorker() throws Exception { 

        Worker nWorker = new Worker();

        String name = workerNameTextField.getText();
        if(name == ""){
            ErrorPopup.errorPopup(2);
            return;
        }
        nWorker.setWorkerName(name);

        String surename = workerSurnameTextField.getText();
        if(surename == ""){
            ErrorPopup.errorPopup(2);
            return;
        }
        nWorker.setWorkerSurname(surename);

        String specitalty = workerSpecialtyTextField.getText();
        if(specitalty == ""){
            specitalty = null;
        }
        nWorker.setSpecialtyId(specitalty);

        int roleId;
        if(!workerTypeComboBox.getSelectionModel().isEmpty()){
            String workerType = workerTypeComboBox.getSelectionModel().getSelectedItem();
            nWorker.setTypeWorker(workerType);
            if(workerType.equalsIgnoreCase("Administration Staff")){ 
                roleId = 3;//Adstaff
            } else {
                roleId = 2;//Non-adStaff
            }
        } else {
            ErrorPopup.errorPopup(2);
            return;
        }

        jdbc.addWorker(nWorker);
        nWorker.setWorkerId(jdbc.getLastIdIntroduced());
        System.out.println();
        String[] uspassw = register(nWorker.getWorkerName(), nWorker.getWorkerSurname(), nWorker.getWorkerId(), roleId);

        addWorkerSuccess(uspassw);

        resetCreateWorkerScene();

    }

    private void addWorkerSuccess(String[] uspass) throws IOException {
        FXMLLoader loaderAddWorkerSuccess;
        Parent rootAddWorkerSuccess;
        Scene sceneAddWorkerSuccess;
        Stage stageAddWorkerSuccess;
        AddWorkerSuccessController addWorkerSuccessController;

        loaderAddWorkerSuccess = new FXMLLoader(getClass().getResource("AddWorkerSuccess.fxml"));
        rootAddWorkerSuccess = loaderAddWorkerSuccess.load();
        addWorkerSuccessController = loaderAddWorkerSuccess.getController();
        addWorkerSuccessController.setUsernamePassword(uspass[0], uspass[1]);

        sceneAddWorkerSuccess = new Scene(rootAddWorkerSuccess);
        stageAddWorkerSuccess = new Stage();
        stageAddWorkerSuccess.setScene(sceneAddWorkerSuccess);

        Image icon = new Image("application/images/healthcare.png");
        stageAddWorkerSuccess.getIcons().add(icon);	

        stageAddWorkerSuccess.setResizable(false);
        stageAddWorkerSuccess.setTitle("Success");
        stageAddWorkerSuccess.show();

    }

    @FXML
    private TableView<Worker> currentLinkDocWorkerTableView;
    @FXML
    private TableView<Patient> currentLinkDocPatientTableView;

    @SuppressWarnings("unchecked")
    private void setLinkDocSelectedPatientTable(){
        currentLinkDocPatientTableView.getItems().clear();
        currentLinkDocPatientTableView.getColumns().clear();
        currentLinkDocPatientTableView.getColumns().addAll(columnPatientId, columnPatientName, columnPatientSurname, columnPatientGender, columnPatientBloodtype, columnPatientAllergies, columnPatientBirthDate, columnPatientCheckInDate, columnPatientAddress, columnPatientHospitalized);
        currentLinkDocPatientTableView.getItems().add(currentSelectedPatient);
    }

    @SuppressWarnings("unchecked")
    private void setLinkDocSelectedWorkerTable(){
        currentLinkDocWorkerTableView.getItems().clear();
        currentLinkDocWorkerTableView.getColumns().clear();
        currentLinkDocWorkerTableView.getColumns().addAll(columnWorkerId, columnWorkerName, columnWorkerSurname, columnWorkerType, columnWorkerSpecialtyId);
        currentLinkDocWorkerTableView.getItems().add(currentSelectedWorker);
    }

    public void linkDocPatient() throws SQLException, IOException{
        if (currentSelectedWorker == null) { 
            ErrorPopup.errorPopup(12);
            return;
        } else if (currentSelectedPatient == null) {
            ErrorPopup.errorPopup(11);
            return;
        } else {
            jdbc.createLinkDoctorPatient(currentSelectedPatient.getMedicalCardId(), currentSelectedWorker.getWorkerId());
            SuccessPopup.successPopup(3);
            currentSelectedPatient = null;
            currentSelectedWorker = null;
            displayAssignANewDoctorView();
        }
    }

    public void cancelLinkDocPatient(){
        currentSelectedPatient = null;
        currentSelectedWorker = null;
    }

    //SELECT WORKER

    private static Worker currentSelectedWorker = null;

    @FXML
    private TextField selectWorkerTextField;
    @FXML
    private TableView<Worker> selectWorkerTableView;

    
    @SuppressWarnings("unchecked")
    private void setWorkerTables() throws SQLException, NotBoundException{

        List<Worker> workerList = jdbc.selectAllWorkers();
        selectWorkerTableView.getItems().clear();
        selectWorkerTableView.getColumns().clear();
        selectWorkerTableView.getColumns().addAll(columnWorkerId, columnWorkerName, columnWorkerSurname, columnWorkerType, columnWorkerSpecialtyId);
        selectWorkerTableView.getItems().addAll(workerList);

    }

    @FXML
    public void selectWorkerBackToDelete(ActionEvent actionEvent) throws IOException {
        
        Integer workerId = null;
        currentSelectedWorker = null;

        try {
            workerId = Integer.parseInt(selectWorkerTextField.getText());
            currentSelectedWorker = jdbc.selectWorker(workerId);
        } catch (Exception e) {
            ErrorPopup.errorPopup(4);
            return;
        }
        if(currentSelectedWorker == null){  
            ErrorPopup.errorPopup(4);
            return;
        }

        selectWorkerTextField.clear();
        hideAll();
        displayDeleteWorker();
    }
    @FXML
    public void selectWorkerBackToEdit(ActionEvent actionEvent) throws IOException {
        
        Integer workerId = null;
        currentSelectedWorker = null;

        try {
            workerId = Integer.parseInt(selectWorkerTextField.getText());
            currentSelectedWorker = jdbc.selectWorker(workerId);
        } catch (Exception e) {
            ErrorPopup.errorPopup(4);
            return;
        }
        if(currentSelectedWorker == null){  
            ErrorPopup.errorPopup(4);
            return;
        }
        selectWorkerTextField.clear();
        hideAll();
        displayChangeWorkerDataView();
    }
    @FXML
    public void selectWorkerBackToEditShift(ActionEvent actionEvent) throws IOException {
        
        Integer workerId = null;
        currentSelectedWorker = null;

        try {
            workerId = Integer.parseInt(selectWorkerTextField.getText());
            currentSelectedWorker = jdbc.selectWorker(workerId);
        } catch (Exception e) {
            ErrorPopup.errorPopup(4);
            return;
        }
        if(currentSelectedWorker == null){  
            ErrorPopup.errorPopup(4);
            return;
        }
        selectWorkerTextField.clear();
        hideAll();
        displayChangeShiftDataView();
    }
    @FXML
    public void selectWorkerBackToLinkDoc(ActionEvent actionEvent) throws IOException {
        
        Integer workerId = null;
        currentSelectedWorker = null;

        try {
            workerId = Integer.parseInt(selectWorkerTextField.getText()); 
            currentSelectedWorker = jdbc.selectWorker(workerId);        
        } catch (Exception e) {
            ErrorPopup.errorPopup(4);
            return;
        }
        if(currentSelectedWorker == null){  
            ErrorPopup.errorPopup(4);
            return;
        }
        selectWorkerTextField.clear();
        hideAll();
        displayAssignANewDoctorView();
    }

    //Edit worker

    @FXML
    private TableView<Worker> currentEditWorkerTableView;

    @SuppressWarnings("unchecked")
    private void setCurrentEditWorkerTable(){
        currentEditWorkerTableView.getItems().clear();
        currentEditWorkerTableView.getColumns().clear();
        currentEditWorkerTableView.getColumns().addAll(columnWorkerId, columnWorkerName, columnWorkerSurname, columnWorkerType, columnWorkerSpecialtyId);
        currentEditWorkerTableView.getItems().add(currentSelectedWorker);
    }
    
    @FXML
    TextField workerEditNameTextField;
    @FXML
    TextField workerEditSurnameTextField;
    @FXML
    TextField workerEditSpecialtyTextField;
    @FXML
    ComboBox<String> workerEditRoleComboBox;

    @FXML
    public void executeUpdateWorker() {
        hideAll();
        
        try {
            workerEditNameTextField.setPromptText(currentSelectedWorker.getWorkerName());
            workerEditSurnameTextField.setPromptText(currentSelectedWorker.getWorkerSurname());
            workerEditSpecialtyTextField.setPromptText(currentSelectedWorker.getSpecialtyId());
            workerEditRoleComboBox.getSelectionModel().select(currentSelectedWorker.getTypeWorker());
        } catch (Exception e) {
        }

        paneEditWorkerDataView.setVisible(true);
        paneEditWorkerDataView.setDisable(false);
    }

    // @FXML
    // public void editWorker() throws IOException {
    //     String name = workerEditNameTextField.getText();
    //     if(name == ""){
    //         name = null;
    //     }
        
    //     String surName = workerEditSurnameTextField.getText();
    //     if(surName == ""){
    //         surName = null;
    //     }

    //     String specialty = workerEditSpecialtyTextField.getText();
    //     if(specialty == ""){
    //         specialty = null;
    //     }

    //     String workerType;
    //     if(!workerEditRoleComboBox.getSelectionModel().isEmpty()){
    //         workerType = workerTypeComboBox.getSelectionModel().getSelectedItem();
    //     } else {
    //         workerType = null;
    //     }

    //     //todo - edit worker on db here.
        
    //     SuccessPopup.successPopup(2);

    //     resetEditWorkerScene();
    //     hideAll();
    //     currentSelectedWorker = null;
    //     displayChangeWorkerDataView();

    // }

    @FXML
    public void cancelEditWorker(){
        currentEditWorkerTableView.getItems().clear();
        currentSelectedWorker = null;
        displayEditWorkerView();
    }

    //Delete Worker View

    @FXML
    private TableView<Worker> currentDeleteWorkerTableView;

    @SuppressWarnings("unchecked")
    private void setCurrentDeleteWorkerTable(){
        currentDeleteWorkerTableView.getItems().clear();
        currentDeleteWorkerTableView.getColumns().clear();
        currentDeleteWorkerTableView.getColumns().addAll(columnWorkerId, columnWorkerName, columnWorkerSurname, columnWorkerType, columnWorkerSpecialtyId);
        currentDeleteWorkerTableView.getItems().add(currentSelectedWorker);
    }

    @FXML
    public void executeDeleteWorker() throws IOException{
        try {
            jdbc.deleteWorkerById(currentSelectedWorker.getWorkerId());
            SuccessPopup.successPopup(8);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        cancelDeleteWorker();
    }
    @FXML
    public void cancelDeleteWorker(){
        currentDeleteWorkerTableView.getItems().clear();
        currentSelectedWorker = null;
        displayDeleteWorker();
    }

    //SHIFT BS Edit 

    private static Shift currentSelectedShift = null;

    @FXML
    private TextField selectShiftTextField;
    @FXML
    private TableView<Shift> selectShiftTableView;

    
    @SuppressWarnings("unchecked")
    private void setShiftTables() throws Exception{

        List<Shift> shiftList = jdbc.searchShiftByWorkerId(currentSelectedWorker.getWorkerId()); 
        selectShiftTableView.getItems().clear();
        selectShiftTableView.getColumns().clear();
        selectShiftTableView.getColumns().addAll(columnShiftId, columnShiftWorkerId, columnShiftRoomER, columnShiftTurn, columnShiftDate);
        selectShiftTableView.getItems().addAll(shiftList);

    }

    @FXML
    private TableView<Shift> currentSelectedShiftTableView;
    @FXML
    private TableView<Worker> currentSelectedWorkerEditShiftTableView;
    
    @SuppressWarnings("unchecked")
    private void setCurrentEditShiftTable(){
        currentSelectedShiftTableView.getItems().clear();
        currentSelectedShiftTableView.getColumns().clear();
        currentSelectedShiftTableView.getColumns().addAll(columnShiftId, columnShiftWorkerId, columnShiftRoomER, columnShiftTurn, columnShiftDate);
        currentSelectedShiftTableView.getItems().add(currentSelectedShift);
    }

    @FXML
    public void cancelEditShift(){
        currentDeleteWorkerTableView.getItems().clear();
        currentSelectedShift = null;
        displayChangeShiftDataView();
    }
    
    @SuppressWarnings("unchecked")
    private void setCurrentWorkerEditShiftTables() throws SQLException, NotBoundException{

        currentSelectedWorkerEditShiftTableView.getItems().clear();
        currentSelectedWorkerEditShiftTableView.getColumns().clear();
        currentSelectedWorkerEditShiftTableView.getColumns().addAll(columnWorkerId, columnWorkerName, columnWorkerSurname, columnWorkerType, columnWorkerSpecialtyId);
        currentSelectedWorkerEditShiftTableView.getItems().addAll(currentSelectedWorker);

    }

    @FXML
    public void cancelEditShiftWorker(){
        currentSelectedWorkerEditShiftTableView.getItems().clear();
        currentSelectedWorker = null;
        displayChangeShiftDataView();
    }

    public void executeUpdateShift() {

        if(currentSelectedShift != null){
            hideAll();
            resetAll();
    
            editRooomERTextField.setPromptText(Integer.toString(currentSelectedShift.getRoom()));
            editShiftDatePicker.setPromptText(currentSelectedShift.getDate().toString());
            editShiftTurnComboBox.getSelectionModel().select(currentSelectedShift.getTurn());
        } 

        paneEditShiftView.setDisable(false);
        paneEditShiftView.setVisible(true);
    
    }

    @FXML
    public void editShift() throws NumberFormatException, Exception {
        String roomER = editRooomERTextField.getText();
        if(roomER == ""){
            roomER = null;
        } 
        Integer roomInt = null;
        try {
            roomInt = Integer.parseInt(roomER);
        } catch (Exception e) {
            ErrorPopup.errorPopup(4);
        }
        String shiftTurn;
        if(editShiftTurnComboBox.getSelectionModel().isEmpty()){
            shiftTurn = null;
        } else {
            shiftTurn = editShiftTurnComboBox.getSelectionModel().getSelectedItem();
        }
        Date date;
        if(editShiftDatePicker.getEditor().getText() == ""){
            date = null;
        } else {
            date = Date.valueOf(editShiftDatePicker.getValue());
            if (!((date.after(Date.valueOf(LocalDate.now()))) || date.equals(Date.valueOf(LocalDate.now())))) {
                ErrorPopup.errorPopup(14);
                return;
            } 
        }

        jdbc.editShift(currentSelectedShift.getShiftId(), currentSelectedShift.getWorker().getWorkerId(), shiftTurn, roomInt, date);
    
        SuccessPopup.successPopup(4);
        resetEditShiftScene();
        hideAll();

        currentSelectedShift = null;
        currentSelectedWorker = null;
        displayChangeShiftDataView();
    }

    @FXML
    public void createShift() throws Exception {
        Shift nShift = new Shift();
        String room = rooomERTextField.getText();
        if(room == ""){
            ErrorPopup.errorPopup(2);
            return;
        } else {
            try {
                nShift.setRoom(Integer.parseInt(room));
            } catch (Exception e) {
                ErrorPopup.errorPopup(4);
                return;
            }
        }

        if(shiftTurnComboBox.getSelectionModel().isEmpty()){
            ErrorPopup.errorPopup(2);
            return;
        } else {
           nShift.setTurn(shiftTurnComboBox.getSelectionModel().getSelectedItem());
        }

        if(shiftDatePicker.getEditor().getText() == ""){
            ErrorPopup.errorPopup(2);
            return;
        } 
        
        Date date = Date.valueOf(shiftDatePicker.getValue());
        if (!((date.after(Date.valueOf(LocalDate.now()))) || date.equals(Date.valueOf(LocalDate.now())))) {
            ErrorPopup.errorPopup(14);
            return;
        } 
        nShift.setDate(date);
        nShift.setWorker(currentSelectedWorker);

        jdbc.addShift(nShift);
        SuccessPopup.successPopup(5);

        resetCreateShift();
        hideAll();

        currentSelectedShift = null;
        currentSelectedWorker = null;
        displayChangeShiftDataView();
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

    private void resetCreateWorkerScene() {
        workerNameTextField.clear();
        workerSurnameTextField.clear();
        workerSpecialtyTextField.clear();
        workerTypeComboBox.getSelectionModel().clearSelection();
    }

    public void resetEditPatientScene() {
        editAddressTextField.clear();
        editNameTextField.clear();
        editSurnameTextField.clear();
        editAllergiesTextArea.clear();
        editMaleRadioButton.setSelected(false);
        editFemaleRadioButton.setSelected(false);
        editBloodTypeChoiceBox.getSelectionModel().clearSelection();
        editHospitalizedChoiceBox.getSelectionModel().clearSelection();
        editBirthDatePicker.setValue(null);
        editCheckInDatePicker.setValue(null);
    }

    public void resetEditWorkerScene(){
        workerEditNameTextField.clear();
        workerEditSurnameTextField.clear();
        workerSpecialtyTextField.clear();
        workerEditRoleComboBox.getSelectionModel().clearSelection();
    }

    public void resetCreateMedTest() {
        typeMedTestTextField.clear();
        resultMedTestTextArea.clear();
        medicaltestDatePicker.setValue(null); 
    }

    private void resetEditShiftScene(){
        editShiftDatePicker.setValue(null);
        editShiftTurnComboBox.getSelectionModel().clearSelection();
        editRooomERTextField.clear();
    }

    private void resetCreateShift() {
        shiftDatePicker.setValue(null);
        shiftDatePicker.getEditor().clear();
        shiftTurnComboBox.getSelectionModel().clearSelection();
        rooomERTextField.clear();
        cancelEditShiftWorker();
        cancelEditShift();
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
     * @param id ROL ID, look up
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
		} else {
			jdbc.createLinkUserWorker(user.getUserId(), idUser); 
		} 
        String[] userpass = {username, password};
        return userpass;
	} 

    @FXML
    private TableView<Patient> currentCreateMTPatientTableView;

    @SuppressWarnings("unchecked")
    private void setCurrentCreateMTPatientTable(){
        currentCreateMTPatientTableView.getItems().clear();
        currentCreateMTPatientTableView.getColumns().clear();
        currentCreateMTPatientTableView.getColumns().addAll(columnPatientId, columnPatientName, columnPatientSurname, columnPatientGender, columnPatientBloodtype, columnPatientAllergies, columnPatientBirthDate, columnPatientCheckInDate, columnPatientAddress, columnPatientHospitalized);
        currentCreateMTPatientTableView.getItems().add(currentSelectedPatient);
    }

    @FXML
    private Button fromMedTestSelectPatientButton;

    public void displaySelectPatientFromMedTest() throws IOException {
        hideAll();
        resetAll();

        try {
            setPatientTables();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPopup.errorPopup(0);
        }

        paneSelectPatient.setDisable(false);
        paneSelectPatient.setVisible(true);
        
        hideAllPatientSelectButton();
        fromMedTestSelectPatientButton.setVisible(true);
        fromMedTestSelectPatientButton.setDisable(false);
    }

    @FXML
    public void selectPatientBackToMedTest() throws IOException{
        
        Integer patientId = null;
        currentSelectedPatient = null;

        try {
            patientId = Integer.parseInt(selectPatientTextField.getText()); 
            currentSelectedPatient = jdbc.selectPatient(patientId);        
        } catch (Exception e) {
            ErrorPopup.errorPopup(4);
            return;
        }
        if(currentSelectedPatient == null){  
            ErrorPopup.errorPopup(4);
            return;
        }
        selectPatientTextField.clear();
        hideAll();
        displayCreateMedicalTest();

    }

    public void displayCreateMedicalTest() {
        hideAll();
        resetAll();

        if(currentSelectedPatient != null){
            setCurrentCreateMTPatientTable();
        }

        paneCreateMedicalTest.setVisible(true);
        paneCreateMedicalTest.setDisable(false);
    }

    @FXML
    private DatePicker medicaltestDatePicker;
    @FXML
    private TextField typeMedTestTextField;
    @FXML TextArea resultMedTestTextArea;

    @FXML
    public void createMedicalTest() throws IOException, SQLException {
        if (currentSelectedPatient == null) {
            ErrorPopup.errorPopup(11);
            return;
        }
        String type = typeMedTestTextField.getText();
        if (type == "") {
            ErrorPopup.errorPopup(2);
            return;
        }
        String result = resultMedTestTextArea.getText();
        if(result == ""){
            ErrorPopup.errorPopup(2);
            return;
        }

        if(medicaltestDatePicker.getEditor().getText() == ""){
            ErrorPopup.errorPopup(2);
            return;
        }

        Date date = Date.valueOf(medicaltestDatePicker.getValue());
        if (!((date.before(Date.valueOf(LocalDate.now()))) || date.equals(Date.valueOf(LocalDate.now())))) { //TODO - not sure bout this (atm esta igual q en el main)
            ErrorPopup.errorPopup(21);
            return;
        } 


        MedicalTest medtest = new MedicalTest(date, type, result, currentSelectedPatient.getMedicalCardId());
        jdbc.addMedicalTest(medtest);

        SuccessPopup.successPopup(16); 

        resetCreateMedTest();
        hideAll();
        currentSelectedPatient = null;
        displayCreateMedicalTest();
    }

    @FXML
    public void convertWorkersToXML() throws IOException {

        try {
            XMLManager.java2XmlWorker();
            SuccessPopup.successPopup(14);
        } catch (Exception e) {
            ErrorPopup.errorPopup(20);
        }
    }

    @FXML
    public void importWorkerFromXML() throws IOException {

        try {
            XMLManager.xml2JavaWorker();
            SuccessPopup.successPopup(15);
        } catch (Exception e) {
            ErrorPopup.errorPopup(19);
        }
    }

    @FXML
    public void convertXMLToHTML() throws IOException{

        try {
            XMLManager.simpleTransform("./xmls/External-Worker.xml", "./xmls/Worker-Style.xslt", "./xmls/Worker.html");
            SuccessPopup.successPopup(13);
        } catch (Exception e) {
            ErrorPopup.errorPopup(18);
        }
    }

}