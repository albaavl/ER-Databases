package application.controllers;
//.
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ErrorPopup {
    
    /**
     * When called it displays a new window with an error msg in function of the int passed to the function
     * <p> {@code 0} General error (unspecified)
     * <p> {@code 1} ur birthdate cant be tomorrow nor on any future date bruh
     * <p> {@code 2} Please fill all the values lmao.
     * <p> {@code 3} Bruh u cant check in on the future.
     * <p> {@code 4} Numeros, no letras ffs aint that hard + #noteinventescosasbro(El id no existe).
     * <p> {@code 5} Wrong Username or password.
     * <p> {@code 6} No shift to display bruh.
     * <p> {@code 7} No treatment to display.
     * <p> {@code 11} No patient selected
     * <p> {@code 12} No worker selected
     * <p> {@code X} idk keep adding stuff here...

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
                stageError.show();
                break;
                
            case 7:
                loaderError = new FXMLLoader(getClass().getResource("errorPopup.fxml")); 
                rootError = loaderError.load(); 
                errorPopupController = loaderError.getController();
                errorPopupController.displayErrorText("Something went wrong, please check everything and try again.");
			    sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);

                icon = new Image("application/images/errorIcon.png");
                stageError.getIcons().add(icon);	        

                stageError.setTitle("Error: No treatments");
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
                stageError.show();

            default:
                break;
        }
    }
}
