package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.jdbc.SQL;
import db.jpa.JPAUserManager;
import db.pojos.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
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

public class MedStaffMenuController {

	private static SQL jdbc;
	private static JPAUserManager userman;
	private ErrorPopup ErrorPopup = new application.controllers.ErrorPopup();
	private static Worker medStaff;

	@FXML
	Pane mainMedStaffView;

	// Patient menus
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

	// Shift menus
	@FXML
	Pane allShiftsView;
	@FXML
	Pane specificShiftView;

	// Menu buttons
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

	// Welcome
	@FXML
	Label userName;

	// ConsultMedTestView
	@FXML
	Label titleMedicalTest;
	@FXML
	TableView<MedicalTest> medicalTestsTable;
	@FXML
	TableColumn<MedicalTest, String> medicalTestId = new TableColumn<>("ID");
	@FXML
	TableColumn<MedicalTest, String> medicalTestDate = new TableColumn<>("Date");
	@FXML
	TableColumn<MedicalTest, String> medicalTestType = new TableColumn<>("Type");
	@FXML
	TableColumn<MedicalTest, String> medicalTestResult = new TableColumn<>("Result");

	// AddTreatment
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

	// EditTreatment
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

	// SelectPatient
	@FXML
	TableView<Patient> patientsTable;
	@FXML
	TableColumn<Patient, String> medicalCard = new TableColumn<>("Medical card");
	@FXML
	TableColumn<Patient, String> patientName = new TableColumn<>("Name");
	@FXML
	TableColumn<Patient, String> patientSurname = new TableColumn<>("Surname");
	@FXML
	TableColumn<Patient, String> patientGender = new TableColumn<>("Gender");
	@FXML
	TableColumn<Patient, String> patientBlood = new TableColumn<>("Blood type");
	@FXML
	TableColumn<Patient, String> patientAllergies = new TableColumn<>("Allergies");
	@FXML
	TableColumn<Patient, String> patientAddress = new TableColumn<>("Address");
	@FXML
	TableColumn<Patient, String> patientBirthdate = new TableColumn<>("Birthdate");
	@FXML
	TableColumn<Patient, String> patientCheckIn = new TableColumn<>("Check-in");
	@FXML
	TableColumn<Patient, String> patientHospitalized = new TableColumn<>("Hospitalized");
	@FXML
	Button selectPatient1;
	@FXML
	Button selectPatient2;
	@FXML
	Button selectPatient3;
	@FXML
	TextField chosenPatientId;

	// SelectTreatment
	@FXML
	TableView<Treatment> treatmentsTable;
	@FXML
	TableColumn<Treatment, String> treatmentId = new TableColumn<>("ID");
	@FXML
	TableColumn<Treatment, String> dateTreatment = new TableColumn<>("StartDate");
	@FXML
	TableColumn<Treatment, String> durationTreatment = new TableColumn<>("Duration");
	@FXML
	TableColumn<Treatment, String> medicationTreatment = new TableColumn<>("Medication");
	@FXML
	TableColumn<Treatment, String> adviceTreatment = new TableColumn<>("Recommendations");
	@FXML
	TableColumn<Treatment, String> diagnosisTreatment = new TableColumn<>("Diagnosis");
	@FXML
	Button selectTreatment;
	@FXML
	TextField chosenTreatmentId;

	// AllShifts
	@FXML
	TableView<Shift> shiftsTable;
	@FXML
	TableColumn<Shift, String> shiftId = new TableColumn<>("ID");
	@FXML
	TableColumn<Shift, String> shiftDate = new TableColumn<>("Date");
	@FXML
	TableColumn<Shift, String> shiftTurn = new TableColumn<>("Shift");
	@FXML
	TableColumn<Shift, String> shiftRoom = new TableColumn<>("Room");

	// SpecificShift
	@FXML
	DatePicker ssSelectedDate;
	@FXML
	TableView<Shift> ssShiftTable;
	@FXML
	TableColumn<Shift, String> ssId = new TableColumn<>("ID");
	@FXML
	TableColumn<Shift, String> ssDate = new TableColumn<>("Date");
	@FXML
	TableColumn<Shift, String> ssTurn = new TableColumn<>("Shift");
	@FXML
	TableColumn<Shift, String> ssRoom = new TableColumn<>("Room");

	// Controller stuff
	public static MedStaffMenuController thisMedStaffMenuController;

	public void setMedStaffController(MedStaffMenuController medStaffMenuController) {
		thisMedStaffMenuController = medStaffMenuController;
	}

	public static MedStaffMenuController passMedStaffMenuController() {
		return thisMedStaffMenuController;
	}

	// Hide all
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

	// Reset all
	private void resetAll() {
		startDateCTreatment.setValue(null);
		startDateCTreatment.getEditor().clear();
		durationCTreatment.clear();
		;
		medicationCTreatment.clear();
		;
		diagnosisCTreatment.clear();
		adviceCTreatment.clear();
		startDateETreatment.setValue(null);
		startDateETreatment.getEditor().clear();
		durationETreatment.clear();
		medicationETreatment.clear();
		;
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

	public void displayConsultMedicalTestsView(ActionEvent aEvent)
			throws NumberFormatException, NotBoundException, SQLException, IOException {
		hideAll();
		resetAll();
		consultMedicalTestsView.setDisable(false);
		consultMedicalTestsView.setVisible(true);
		currentPatient = new Patient(jdbc.selectPatient(Integer.parseInt(chosenPatientId.getText())));
		if (currentPatient == null) {
			ErrorPopup.errorPopup(4);
			return;
		}
//TODO funcion de consultMedicalTest
	}

	public void displayEditTreatmentView(ActionEvent aEvent) {
		hideAll();
		resetAll();
		titleEditTreatment.setText("Edit treatment (ID =" + currentTreatment.getTreatmentId() + ")");
		editTreatmentView.setDisable(false);
		editTreatmentView.setVisible(true);

	}

	public void displaySelectPatientView1(ActionEvent aEvent) throws IOException {
		hideAll();
		resetAll();
		hideButtons();
		selectPatientView.setDisable(false);
		selectPatientView.setVisible(true);
		selectPatient1.setVisible(true);
		try {
			selectPatient();
		} catch (Exception e) {
			ErrorPopup.errorPopup(0);// TODO error que imprima los mitic errores de sql y blah blah o dejarlo en
										// generico
			return;
		}

	}

	public void displaySelectPatientView2(ActionEvent aEvent) throws IOException {
		hideAll();
		resetAll();
		hideButtons();
		selectPatientView.setDisable(false);
		selectPatientView.setVisible(true);
		selectPatient2.setVisible(true);
		try {
			selectPatient();
		} catch (Exception e) {
			ErrorPopup.errorPopup(0);// TODO error que imprima los mitic errores de sql y blah blah o dejarlo en
										// generico
			return;
		}

	}

	public void displaySelectPatientView3(ActionEvent aEvent) throws IOException {
		hideAll();
		resetAll();
		hideButtons();
		selectPatientView.setDisable(false);
		selectPatientView.setVisible(true);
		selectPatient3.setVisible(true);
		try {
			selectPatient();
		} catch (Exception e) {
			ErrorPopup.errorPopup(0);// TODO error que imprima los mitic errores de sql y blah blah o dejarlo en
										// generico
			return;
		}

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

	// database functions
	private static Treatment currentTreatment = new Treatment();
	private static Patient currentPatient = new Patient();

	public void displayWelcomeText(Worker w, SQL sqlman, JPAUserManager userm) {
		hideAll();
		resetAll();
		mainMedStaffView.setVisible(true);
		mainMedStaffView.setDisable(false);
		try {
			medStaff = new Worker(w);
			jdbc = sqlman;
			userman = userm;
			userName.setText(w.getWorkerSurname());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addTreatment(ActionEvent aE) throws Exception {
		currentPatient = new Patient(jdbc.selectPatient(Integer.parseInt(chosenPatientId.getText())));
		if (currentPatient == null) {
			ErrorPopup.errorPopup(4);
			return;
		}
		Treatment newTreatment = new Treatment();
		newTreatment.setPatientId(currentPatient.getMedicalCardId());
		if (diagnosisCTreatment.getText().equalsIgnoreCase("") || durationCTreatment.getText().equalsIgnoreCase("")
				|| medicationCTreatment.getText().equalsIgnoreCase("")
				|| adviceCTreatment.getText().equalsIgnoreCase("") || startDateCTreatment.getEditor().getText() == "") {
			ErrorPopup.errorPopup(2);
			return;
		}
		Date startDate = Date.valueOf(startDateCTreatment.getValue());
		if (startDate.before(Date.valueOf(LocalDate.now()))) {
			ErrorPopup.errorPopup(8);
			return;
		}
		newTreatment.setStartDate(startDate);

		String diagnosis = diagnosisCTreatment.getText();
		newTreatment.setDiagnosis(diagnosis);

		String medication = medicationCTreatment.getText();
		newTreatment.setMedication(medication);

		Integer duration = Integer.parseInt(durationCTreatment.getText());
		newTreatment.setDuration(duration);

		String advice = adviceETreatment.getText();
		newTreatment.setRecommendation(advice);

		jdbc.addTreatment(newTreatment);
		// TODO popup de treatment añadido correctamente
	}

	public void editTreatment(ActionEvent aE) throws Exception {
		currentTreatment = new Treatment(jdbc.selectTreatment(Integer.parseInt(chosenTreatmentId.getText())));
		Date startDate = Date.valueOf(startDateETreatment.getValue());
		String diagnosis = diagnosisETreatment.getText();
		String medication = medicationETreatment.getText();
		Integer duration = Integer.parseInt(durationETreatment.getText());
		String advice = adviceETreatment.getText();
		jdbc.editTreatment(currentTreatment.getTreatmentId(), diagnosis, medication, startDate, duration, advice);
		// TODO popup de treatment added y comprobar qq los getValue y getText devuelven
		// null si no hay nada escrito
	}

	private void allShifts() throws Exception {
		List<Shift> shiftsList = new ArrayList<>();
		shiftsList.addAll(jdbc.searchShiftByWorkerId(medStaff.getWorkerId()));
		if (shiftsList.isEmpty()) {
			ErrorPopup.errorPopup(6);// no shifts for this worker
			displayWelcomeText(medStaff, jdbc, userman);
		} else {
			shiftsTable.getItems().clear();
			shiftsTable.getColumns().clear();
			shiftsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			shiftId.setCellValueFactory(
					data -> new SimpleStringProperty(Integer.toString(data.getValue().getShiftId())));
			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			shiftDate.setCellValueFactory(
					data -> new SimpleStringProperty(dateformat.format(data.getValue().getDate())));
			shiftTurn.setCellValueFactory(new PropertyValueFactory<>("turn"));
			shiftRoom
					.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getRoom())));
			shiftsTable.getColumns().addAll(shiftId, shiftDate, shiftTurn, shiftRoom);
			shiftsTable.getItems().addAll(shiftsList);
		}
	}

	public void selectedShift(ActionEvent aE) throws Exception {
		Date shiftDate = Date.valueOf(ssSelectedDate.getValue());
		if (!shiftDate.before(Date.valueOf(LocalDate.now()))) {
			List<Shift> shiftsList = new ArrayList<>();
			ssShiftTable.getItems().clear();
			ssShiftTable.getColumns().clear();
			shiftsList.addAll(jdbc.searchShiftByDate(medStaff.getWorkerId(), shiftDate));
			if (shiftsList.isEmpty()) {
				ErrorPopup.errorPopup(10);
				return;
			}
			ssShiftTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			ssId.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getShiftId())));
			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			ssDate.setCellValueFactory(data -> new SimpleStringProperty(dateformat.format(data.getValue().getDate())));
			ssTurn.setCellValueFactory(new PropertyValueFactory<>("turn"));
			ssRoom.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getRoom())));
			ssShiftTable.getColumns().addAll(ssId, ssDate, ssTurn, ssRoom);
			ssShiftTable.getItems().addAll(shiftsList);
		} else {
			ErrorPopup.errorPopup(8);
			// TODO POPUP EXCEPTION se pueden seleccionar solo turnos futuros
		}
	}

	private void selectPatient() throws IOException, SQLException, NotBoundException {
		List<Patient> patients = new ArrayList<>();
		patients.addAll(jdbc.searchPatientByDoctor(medStaff.getWorkerId()));
		if (patients.isEmpty()) {
			ErrorPopup.errorPopup(9);
			displayWelcomeText(medStaff, jdbc, userman);
		} else {
			patientsTable.getItems().clear();
			patientsTable.getColumns().clear();
			patientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
			patientSurname.setCellValueFactory(new PropertyValueFactory<>("patientSurname"));
			patientAllergies.setCellValueFactory(new PropertyValueFactory<>("allergieType"));
			patientGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
			patientBlood.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
			patientAddress.setCellValueFactory(new PropertyValueFactory<>("patientAddress"));
			medicalCard.setCellValueFactory(
					data -> new SimpleStringProperty(Integer.toString(data.getValue().getMedicalCardId())));
			patientBirthdate
					.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getbDate().toString()));
			patientCheckIn
					.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCheckInDate().toString()));
			patientHospitalized.setCellValueFactory(
					data -> new SimpleStringProperty(Boolean.toString(data.getValue().getHospitalized())));
			patientsTable.getColumns().addAll(medicalCard, patientName, patientSurname, patientBirthdate, patientGender,
					patientBlood, patientCheckIn, patientAddress, patientAllergies, patientHospitalized);

			patientsTable.getItems().addAll(patients);
		}
		// TODO terminarlo
	}

	private void selectTreatment() throws IOException, NumberFormatException, NotBoundException, SQLException {
		currentPatient = new Patient(jdbc.selectPatient(Integer.parseInt(chosenPatientId.getText())));
		if (currentPatient == null) {
			ErrorPopup.errorPopup(4);
			return;
		}
		List<Treatment> treatments = new ArrayList<>();
		try {
			treatments.addAll(jdbc.searchTreatmentsByMedCard(currentPatient.getMedicalCardId()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (treatments.isEmpty()) {
			ErrorPopup.errorPopup(9);
			displayWelcomeText(medStaff, jdbc, userman);
		} else {
			treatmentsTable.getItems().clear();
			treatmentsTable.getColumns().clear();
			dateTreatment
					.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStartDate().toString()));
			durationTreatment.setCellValueFactory(
					data -> new SimpleStringProperty(Integer.toString(data.getValue().getDuration())));
			medicationTreatment.setCellValueFactory(new PropertyValueFactory<>("medication"));
			adviceTreatment.setCellValueFactory(new PropertyValueFactory<>("advice"));
			diagnosisTreatment.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
			treatmentId.setCellValueFactory(
					data -> new SimpleStringProperty(Integer.toString(data.getValue().getTreatmentId())));

			try {

				treatmentsTable.getColumns().addAll(treatmentId, dateTreatment, durationTreatment, medicationTreatment,
						diagnosisTreatment, adviceTreatment);

				treatmentsTable.getItems().addAll(treatments);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
