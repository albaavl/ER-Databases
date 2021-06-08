package application.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ErrorPopup {

	/**
	 * When called it displays a new window with an error msg in function of the int
	 * passed to the function
	 * <p> {@code 0} General error (unspecified)
	 * <p> {@code 1} Not a valid birhtdate (future birthdate)
	 * <p> {@code 2} Please fill all the options
	 * <p> {@code 3} Not a valid check-in date (future date)
	 * <p> {@code 4} Id doesnt exist (no match found/not valid format)
	 * <p> {@code 5} Wrong username or password.
	 * <p> {@code 6} No shifts to display
	 * <p> {@code 7} No treatment to display
	 * <p> {@code 8} Shift date cannot be on the past
	 * <p> {@code 9} No patients to display
	 * <p> {@code 10} No shifts for the selected date
	 * <p> {@code 11} No patient selected
	 * <p> {@code 12} No worker selected
	 * <p> {@code 13} No medical tests for the selected patient
	 * <p> {@code 14} Date cannot be on the past
	 * <p> {@code 15} XML - Shifts - cannot export to file
	 * <p> {@code 16} XML - Shifts - cannot import from file
	 * <p> {@code 17} New password == Old password
	 * <p> {@code 18} XML - cannot convert xml to html
	 * <p> {@code 19} XML - Worker - cannot import from file
	 * <p> {@code 20} XML - Worker - cannot export to file
	 * <p> {@code 21} Date must be today or in the past
	 * <p> {@code 22} Patient and doctor already linked
	 * <p> {@code 23} Conflict MedCardId
	 * <p> {@code X} idk keep adding stuff here...
	 * 
	 * @param errorType - int
	 * @throws IOException
	 */
	public void errorPopup(int errorType) throws IOException {
		FXMLLoader loaderError;
		Parent rootError;
		Scene sceneError;
		Stage stageError;
		Image icon;
		ErrorPopupController errorPopupController;
		switch (errorType) {
		case 0:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("Something went wrong, please check everything and try again.");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Error 0");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 1:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("Please, use a correct date.\nYour birthdate cant be on the future.");
			sceneError = new Scene(rootError);
			stageError = new Stage();

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setScene(sceneError);
			stageError.setTitle("Error: Wrong date");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 2:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("Please, fill all the options.");
			sceneError = new Scene(rootError);
			stageError = new Stage();

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setScene(sceneError);
			stageError.setTitle("Error: Fill all the options");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 3:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("Please, use a correct date.\nCheck-in date cant be on the future.");
			sceneError = new Scene(rootError);
			stageError = new Stage();

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setScene(sceneError);
			stageError.setTitle("Error: Wrong date");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 4:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("No match found.\nPlease try again.");
			sceneError = new Scene(rootError);
			stageError = new Stage();

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);
			stageError.setScene(sceneError);
			stageError.setTitle("Error: Wrong Id");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 5:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("Wrong username or password.\nPlease try again.");
			sceneError = new Scene(rootError);
			stageError = new Stage();

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setScene(sceneError);
			stageError.setTitle("Error: Wrong username or password");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 6:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("There're no shifts added on the database.\nPlease try again.");
			sceneError = new Scene(rootError);
			stageError = new Stage();

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setScene(sceneError);
			stageError.setTitle("Error: No shifts");
			stageError.setResizable(false);
			stageError.show();
			break;

		case 7:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("There are no treatments to display");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Error: No treatments");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 8:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("The date of the shift must be in the future");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Date error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 9:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("No current patients for you");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Patient error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 10:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("There is no shift on the selected date");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Shift error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 11:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("Pick a patient");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 12:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("Pick a worker");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 13:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("No medical tests available \nfor the selected patient");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 14:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("Date must be today or in the future.");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Wrong date");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 15:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("XML file wasn't generated, \ncheck that there are shifts associated to your user and try again");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("XML error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 16:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("XML file cannot be converted into shifts");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("XML error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 17:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("New password and old password are the same");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Password error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 18:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("XML file cannot be converted into HTML"); 
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("XML error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 19:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("XML file cannot be converted into workers");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("XML error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 20:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("Workers cannot be exported into XML file.\n Please check there're workers in the database.");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("XML error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 21:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("Date must be today or in the past.");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Wrong Date");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 22:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("They're already linked.");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Error");
			stageError.setResizable(false);
			stageError.show();
			break;
		case 23:
			loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml"));
			rootError = loaderError.load();
			errorPopupController = loaderError.getController();
			errorPopupController.displayErrorText("Medical Card Id already in use.");
			sceneError = new Scene(rootError);
			stageError = new Stage();
			stageError.setScene(sceneError);

			icon = new Image("application/images/errorIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Error");
			stageError.setResizable(false);
			stageError.show();
			break;

		default:
			break;
		}
	}
}
