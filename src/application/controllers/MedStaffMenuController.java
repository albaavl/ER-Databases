package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.jdbc.SQL;
import db.jpa.JPAUserManager;
import db.pojos.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MedStaffMenuController{
	
	private static SQL jdbc;
    private static JPAUserManager userman;
    private ErrorPopup ErrorPopup = new application.controllers.ErrorPopup();
    private static Worker medStaff;

	
	@FXML
	Pane mainMedStaffView;
	
	
	 //Patient menus
    @FXML
    Pane consultMedicalTestsView;
    @FXML
    Pane editTreatmentView;
    @FXML
    Pane addTreatmentView;
    @FXML
    Pane selectPatientView;
    @FXML
    Pane selectTreatmentView;
    
    
    //Shift menus
    @FXML
    Pane allShiftsView;
    @FXML
    Pane specificShiftView;
    
    
    //Menu buttons
    @FXML
    Button consultMedicalTest;
    @FXML
    Button addTreatmentDiagnosis;
    @FXML
    Button editTreatmentDiagnosis;
    @FXML
    Button allShifts;
    @FXML
    Button specificShift;
    @FXML
    Button shiftsToXML;
    @FXML
    Button xmlToShift;
    @FXML
    Button xmlToHTML;
    
    //ConsultMedTestView
    @FXML
    Label titleMedicalTest;
    @FXML
    TableView<MedicalTest> medicalTestsTable;
    @FXML
    TableColumn<MedicalTest, Integer> medicalTestId;
    @FXML
    TableColumn<MedicalTest, Date> medicalTestDate;
    @FXML
    TableColumn<MedicalTest, String> medicalTestType;
    @FXML
    TableColumn<MedicalTest, String> medicalTestResult;
    
    //AddTreatment
    @FXML
    Label titleAddTreatment;
    @FXML
    DatePicker startDateCTreatment;
    @FXML
    TextField durationCTreatment;
    @FXML
    TextField medicationCTreatment;
    @FXML
    TextArea diagnosisCTreatment;
    @FXML
    TextArea adviceCTreatment;
    @FXML
    Button addTreatment;
    
    //EditTreatment
    @FXML
    Label titleEditTreatment;
    @FXML
    DatePicker startDateETreatment;
    @FXML
    TextField durationETreatment;
    @FXML
    TextField medicationETreatment;
    @FXML
    TextArea diagnosisETreatment;
    @FXML
    TextArea adviceETreatment;
    @FXML
    Button editTreatment;
    
    //SelectPatient
    @FXML
    TableView<Patient> patientsTable;
    @FXML
    TableColumn<Patient, Integer> medicalCard;
    @FXML
    TableColumn<Patient, String> patientName;
    @FXML
    TableColumn<Patient, String> patientSurname;
    @FXML
    TableColumn<Patient, String> patientGender;
    @FXML
    TableColumn<Patient, String> patientBlood;
    @FXML
    TableColumn<Patient, String> patientAllergies;
    @FXML
    TableColumn<Patient, String> patientAddress;
    @FXML
    TableColumn<Patient, Date> patientBirthdate;
    @FXML
    TableColumn<Patient, Date> patientCheckIn;
    @FXML
    TableColumn<Patient, Boolean> patientHospitalized;
    @FXML
    Button selectPatient1;
    @FXML
    Button selectPatient2;
    @FXML
    Button selectPatient3;
    @FXML
    TextField chosenPatientId;
    
    //SelectTreatment
    @FXML
    TableView<Treatment> treatmentsTable;
    @FXML
    TableColumn<Treatment, Integer> treatmentId;
    @FXML
    TableColumn<Treatment, Date> dateTreatment;
    @FXML
    TableColumn<Treatment, String> durationTreatment;
    @FXML
    TableColumn<Treatment, String> medicationTreatment;
    @FXML
    TableColumn<Treatment, String> adviceTreatment;
    @FXML
    TableColumn<Treatment, String> diagnosisTreatment;
    @FXML
    Button selectTreatment;
    @FXML
    TextField chosenTreatmentId;
    
    
    //AllShifts
    @FXML
    TableView<Shift> shiftsTable;
    @FXML
    TableColumn<Shift, Integer> shiftId;
    @FXML
    TableColumn<Shift, Date> shiftDate;
    @FXML
    TableColumn<Shift, String> shiftTurn;
    @FXML
    TableColumn<Shift, Integer> shiftRoom;
    
    //SpecificShift
    @FXML
    DatePicker ssSelectedDate;
    @FXML
    TableView<Shift> ssShiftTable;
    @FXML
    TableColumn<Shift, Integer> ssId;
    @FXML
    TableColumn<Shift, Date> ssDate;
    @FXML
    TableColumn<Shift, String> ssTurn;
    @FXML
    TableColumn<Shift, Integer> ssRoom;
    
  //Controller stuff
    public static MedStaffMenuController thisMedStaffMenuController;

    public void setMedStaffController( MedStaffMenuController medStaffMenuController ) {
    	thisMedStaffMenuController = medStaffMenuController;
    }

    public static MedStaffMenuController passMedStaffMenuController() {
        return thisMedStaffMenuController;
    }
    
    //Hide all
    private void hideAll() {
    	addTreatmentView.setVisible(false);
    	addTreatmentView.setDisable(true);
    	consultMedicalTestsView.setVisible(false);
    	consultMedicalTestsView.setDisable(true);
    	editTreatmentView.setVisible(false);
    	editTreatmentView.setDisable(true);
    	selectPatientView.setVisible(false);
    	selectPatientView.setDisable(true);
    	selectTreatmentView.setVisible(false);
    	selectTreatmentView.setDisable(true);
    	allShiftsView.setVisible(false);
    	allShiftsView.setDisable(true);
    	specificShiftView.setVisible(false);
    	specificShiftView.setDisable(true);
    }
    
    //Reset all
    private void resetAll() {
    	startDateCTreatment.setValue(null);
    	startDateCTreatment.getEditor().clear();
        durationCTreatment.clear();;
        medicationCTreatment.clear();;
        diagnosisCTreatment.clear();
        adviceCTreatment.clear();
        startDateETreatment.setValue(null);
        startDateETreatment.getEditor().clear();
        durationETreatment.clear();
        medicationETreatment.clear();;
        diagnosisETreatment.clear();
        adviceETreatment.clear();
        chosenPatientId.clear();
        chosenTreatmentId.clear();
        ssSelectedDate.setValue(null);
        ssSelectedDate.getEditor().clear();
    }
    
    private void hideButtons() {
    	selectPatient1.setVisible(false);
    	selectPatient2.setVisible(false);
    	selectPatient3.setVisible(false);
    }
    
    public void displayAddTreatmentView(ActionEvent aEvent) {
        hideAll();
        resetAll();
        addTreatmentView.setDisable(false);
        addTreatmentView.setVisible(true);

    }
    
    public void displayConsultMedicalTestsView(ActionEvent aEvent) {
        hideAll();
        resetAll();
        consultMedicalTestsView.setDisable(false);
        consultMedicalTestsView.setVisible(true);

    }
    
    public void displayEditTreatmentView(ActionEvent aEvent) {
        hideAll();
        resetAll();
        editTreatmentView.setDisable(false);
        editTreatmentView.setVisible(true);

    }
    
    public void displaySelectPatientView1(ActionEvent aEvent) {
        hideAll();
        resetAll();
        hideButtons();
        selectPatientView.setDisable(false);
        selectPatientView.setVisible(true);
        selectPatient1.setVisible(true);

    }
    
    public void displaySelectPatientView2(ActionEvent aEvent) {
        hideAll();
        resetAll();
        hideButtons();
        selectPatientView.setDisable(false);
        selectPatientView.setVisible(true);
        selectPatient2.setVisible(true);

    }
    
    public void displaySelectPatientView3(ActionEvent aEvent) {
        hideAll();
        resetAll();
        hideButtons();
        selectPatientView.setDisable(false);
        selectPatientView.setVisible(true);
        selectPatient3.setVisible(true);

    }
    
    public void displaySelectTreatmentView(ActionEvent aEvent) {
        hideAll();
        resetAll();
        selectTreatmentView.setDisable(false);
        selectTreatmentView.setVisible(true);

    }
    
    public void displayAllShiftsView(ActionEvent aEvent) {
        hideAll();
        resetAll();
        allShiftsView.setDisable(false);
        try {
			allShifts();
		} catch (Exception e) {
			// TODO error popup
			e.printStackTrace();
		}
        allShiftsView.setVisible(true);
        

    }
    
    public void displaySpecificShiftView(ActionEvent aEvent) {
        hideAll();
        resetAll();
        specificShiftView.setDisable(false);
        specificShiftView.setVisible(true);

    }
    
    private Parent root;
    private Stage stage;
    private Scene scene;
    
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
    
    //database functions
    
    public void displayWelcomeText(Worker w, SQL sqlman, JPAUserManager userm) {
    	try {
			medStaff = new Worker(w);
			jdbc = sqlman;
			userman = userm;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void addTreatment(ActionEvent aE)throws Exception{
    	
    }
    
    public void editTreatment(ActionEvent aE)throws Exception{
    	
    }
    
    private void allShifts() throws Exception{
    	List<Shift> shiftsList = new ArrayList<>();
    	shiftsList.addAll(jdbc.searchShiftByWorkerId(medStaff.getWorkerId()));
    	
    }
    
    public void selectedShift(ActionEvent aE) throws Exception{
    	
    }
    
}