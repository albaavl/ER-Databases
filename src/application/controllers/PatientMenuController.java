package application.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.jdbc.*;
import db.jpa.*;
import db.pojos.*;



public class PatientMenuController implements Initializable{

	private static SQL jdbc;
	private static JPAUserManager userman;
	private ErrorPopup ErrorPopup = new application.controllers.ErrorPopup();
	private static Patient patient;

	@FXML
	private Label welcomeText;
	
  //SelectTreatment
    @FXML
    TableView<Treatment> treatmentsTable;
    @FXML
    TableColumn<Treatment, String> treatmentId = new TableColumn<>("treatmentId");
    @FXML
    TableColumn<Treatment, String> startDate = new TableColumn<>("startDate");
    @FXML
    TableColumn<Treatment, String> duration = new TableColumn<>("duration");
    @FXML
    TableColumn<Treatment, String> medication = new TableColumn<>("medication");
    @FXML
    TableColumn<Treatment, String> diagnosis = new TableColumn<>("diagnosis");
    @FXML
    TableColumn<Treatment, String> recommendation = new TableColumn<>("recommendation");

	@FXML
	Pane consultTreatmentsView;

	@FXML
	Button LogOut;


	// Controller stuff
	public static PatientMenuController thisPatientMenuController;

	public void setPatientController(PatientMenuController patientMenuController) {
		thisPatientMenuController = patientMenuController;
	}

	public static PatientMenuController passPatientMenuController() {
		return thisPatientMenuController;
	}
	
	// Hide all
	private void hideAll() {
		consultTreatmentsView.setVisible(false);
		consultTreatmentsView.setDisable(true);
	}


	public void displayPatientWelcomeText(Patient p, SQL sqlman, JPAUserManager userm) {
		try {
			patient = new Patient(p);
			jdbc = sqlman;
			userman = userm;
			welcomeText.setText("Mr/Mrs " + p.getPatientName() + ", here are your treatments");
     	} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	private static Patient currentPatient = new Patient();
	
	private void setTreatmentTables() throws IOException, NumberFormatException, NotBoundException, SQLException {
		if (currentPatient == null) {
			ErrorPopup.errorPopup(4);
			return;
		}
		List<Treatment> treatments = new ArrayList<>();
		try {
			treatments.addAll(jdbc.selectAllTreatments(currentPatient.getMedicalCardId()));
		} catch (Exception e) {
			e.printStackTrace();
			ErrorPopup.errorPopup(0);
			return;
		}
		if (treatments.isEmpty()) {
			ErrorPopup.errorPopup(7);
			displayPatientWelcomeText(patient, jdbc, userman);
		} else {
			treatmentsTable.getItems().clear();
			treatmentsTable.getColumns().clear();
			startDate
					.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStartDate().toString()));
			duration.setCellValueFactory(
					data -> new SimpleStringProperty(Integer.toString(data.getValue().getDuration())));
			medication.setCellValueFactory(new PropertyValueFactory<>("medication"));
			recommendation.setCellValueFactory(new PropertyValueFactory<>("recommendation"));
			diagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
			treatmentId.setCellValueFactory(
					data -> new SimpleStringProperty(Integer.toString(data.getValue().getTreatmentId())));

			try {

				treatmentsTable.getColumns().addAll(treatmentId, startDate, duration, medication,
						diagnosis, recommendation);

				treatmentsTable.getItems().addAll(treatments);
			} catch (Exception e) {
				e.printStackTrace();
				ErrorPopup.errorPopup(0);
				return;
			}
		}
	}

	public void displayAllTreatmentsView(ActionEvent aEvent) throws IOException {
		hideAll();
		consultTreatmentsView.setDisable(false);
		consultTreatmentsView.setVisible(true);
		try {
			setTreatmentTables();
		} catch (Exception e) {
			ErrorPopup.errorPopup(0);
			e.printStackTrace();
			return;
		}
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		treatmentId.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getTreatmentId())));
		startDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStartDate().toString()));
		duration.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getDuration())));
    	medication.setCellValueFactory(new PropertyValueFactory<>("medication"));
    	recommendation.setCellValueFactory(new PropertyValueFactory<>("adviceTreatment"));
    	diagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
		
	}

}
