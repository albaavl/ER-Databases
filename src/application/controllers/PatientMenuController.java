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
import java.sql.SQLException;
import java.util.List;

import db.jdbc.*;
import db.jpa.*;
import db.pojos.*;

// import db.jdbc.*;

public class PatientMenuController {

	private static SQL jdbc;
	private static JPAUserManager userman;
	private ErrorPopup ErrorPopup = new application.controllers.ErrorPopup();
	private static Patient patient;

	@FXML
	private Label welcomeText;

	@FXML
	Pane consultTreatmentsView;

	@FXML
	Button LogOut;

	// SelectTreatment
	@FXML
	TableView<Treatment> treatmentsTable;
	@FXML
	TableColumn<Treatment, String> treatmentId;
	@FXML
	TableColumn<Treatment, String> dateTreatment;
	@FXML
	TableColumn<Treatment, String> durationTreatment;
	@FXML
	TableColumn<Treatment, String> medicationTreatment;
	@FXML
	TableColumn<Treatment, String> adviceTreatment;
	@FXML
	TableColumn<Treatment, String> diagnosisTreatment;

	// Controller stuff
	public static PatientMenuController thisPatientMenuController;

	public void setPatientController(PatientMenuController patientMenuController) {
		thisPatientMenuController = patientMenuController;
	}

	public static PatientMenuController passPatientMenuController() {
		return thisPatientMenuController;
	}

	public void displayPatientWelcomeTextView(Patient p, SQL sqlman, JPAUserManager userm) {
		welcomeText.setText("Please, Mr/Mrs " + p.getPatientName() + " here are your treatments");
		try {
			patient = new Patient(p);
			jdbc = sqlman;
			userman = userm;
			// userName.setText(p.getPatientName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TODO gisel no se crea la tabla pqq nunca llamas a las funciones en el display
	// que es a donde accede javafx y hay que cambiar los ajustes del label pqq no
	// se ve el nombre
	private void selectTreatment() {
		treatmentId.setCellValueFactory(
				data -> new SimpleStringProperty(Integer.toString(data.getValue().getTreatmentId())));
		dateTreatment.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStartDate().toString()));
		durationTreatment
				.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getDuration())));
		medicationTreatment.setCellValueFactory(new PropertyValueFactory<>("medicationTreatment"));
		adviceTreatment.setCellValueFactory(new PropertyValueFactory<>("adviceTreatment"));
		diagnosisTreatment.setCellValueFactory(new PropertyValueFactory<>("diagnosisTreatment"));
	}

	private void setTreatmentTables() throws Exception {

		List<Treatment> treatmentList = jdbc.selectAllTreatments();
		if (treatmentList.isEmpty()) {
			ErrorPopup.errorPopup(0);
		} else {
			treatmentsTable.getItems().clear();
			treatmentsTable.getColumns().clear();
			treatmentsTable.getColumns().addAll(treatmentId, dateTreatment, durationTreatment, medicationTreatment,
					adviceTreatment, diagnosisTreatment);
			treatmentsTable.getItems().addAll(treatmentList);
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

}
