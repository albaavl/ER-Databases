package application.controllers;
//.
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ErrorPopup {
    
    /**
     * When called it displays a new window with an error msg in function of the int passed to the function
     * <p> {@code 0} General error (unspecified)
     * <p> {@code 1} ur birthdate cant be tomorrow nor on any future date bruh
     * <p> {@code 2} Please fill all the values lmao.
     * <p> {@code X} idk keep adding stuff here...

     * @param errorType - int
     * @throws IOException
     */
    public void errorPopup(int errorType) throws IOException {
        FXMLLoader loaderError;
        Parent rootError;
        Scene sceneError;
        Stage stageError;
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
                stageError.setScene(sceneError);
                stageError.setTitle("Error: Fill all the options");
                stageError.show();
                break;
            default:
                break;
        }
    }
}
