package application.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SuccessPopup {

    /**
     * When called displays a new window with a success msg in function of the int passed to the function
     * <p> {@code 0} General success (unspecified)
     * <p> {@code 1} Success edit patient 
     * <p> {@code 2} Success edit worker    
     * <p> {@code 3} Success link worker+patient    
     * <p> {@code 4} Success edit shift     
     * <p> {@code 5} Success create shift
     * <p> {@code 6} Success delete patient
     * <p> {@code 7} Success delete worker
     * <p> {@code X} idk keep adding stuff here...
     *
     * @param successType - int
     * @throws IOException
     */
    public void successPopup(int successType) throws IOException {
        FXMLLoader loaderError;
        Parent rootError;
        Scene sceneError;
        Stage stageError;
        Image icon;
        SuccessPopupController successPopupController;
        switch (successType) {
            case 0:
                loaderError = new FXMLLoader(getClass().getResource("successPopup.fxml")); 
                rootError = loaderError.load(); 
                successPopupController = loaderError.getController();
                successPopupController.displaySuccessText("Success :).");
                sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);

			icon = new Image("application/images/successIcon.png");
			stageError.getIcons().add(icon);

			stageError.setTitle("Success");
			stageError.show();

                break;
            case 1:
                loaderError = new FXMLLoader(getClass().getResource("successPopup.fxml")); 
                rootError = loaderError.load(); 
                successPopupController = loaderError.getController();
                successPopupController.displaySuccessText("Edited Patient Correctly.");
                sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);

                icon = new Image("application/images/successIcon.png");
                stageError.getIcons().add(icon);	        

                stageError.setTitle("Success");
                stageError.show();

                break;
            case 2:
                loaderError = new FXMLLoader(getClass().getResource("successPopup.fxml")); 
                rootError = loaderError.load(); 
                successPopupController = loaderError.getController();
                successPopupController.displaySuccessText("Edited Worker Correctly.");
                sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);

                icon = new Image("application/images/successIcon.png");
                stageError.getIcons().add(icon);	        

                stageError.setTitle("Success");
                stageError.show();

                break;
            case 3:
                loaderError = new FXMLLoader(getClass().getResource("successPopup.fxml")); 
                rootError = loaderError.load(); 
                successPopupController = loaderError.getController();
                successPopupController.displaySuccessText("Linked Patient and Worker Correctly.");
                sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);

                icon = new Image("application/images/successIcon.png");
                stageError.getIcons().add(icon);	        

                stageError.setTitle("Success");
                stageError.show();

                break;
            case 4:
                loaderError = new FXMLLoader(getClass().getResource("successPopup.fxml")); 
                rootError = loaderError.load(); 
                successPopupController = loaderError.getController();
                successPopupController.displaySuccessText("Edited Shift Correctly.");
                sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);

                icon = new Image("application/images/successIcon.png");
                stageError.getIcons().add(icon);	        

                stageError.setTitle("Success");
                stageError.show();

                break;
            case 5:
                loaderError = new FXMLLoader(getClass().getResource("successPopup.fxml")); 
                rootError = loaderError.load(); 
                successPopupController = loaderError.getController();
                successPopupController.displaySuccessText("Created Shift Correctly.");
                sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);

                icon = new Image("application/images/successIcon.png");
                stageError.getIcons().add(icon);	        

                stageError.setTitle("Success");
                stageError.show();

                break;
            case 6:
                loaderError = new FXMLLoader(getClass().getResource("successPopup.fxml")); 
                rootError = loaderError.load(); 
                successPopupController = loaderError.getController();
                successPopupController.displaySuccessText("Deleted Patient Correctly.");
                sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);

                icon = new Image("application/images/successIcon.png");
                stageError.getIcons().add(icon);	        

                stageError.setTitle("Success");
                stageError.show();

                break;
            case 7:
                loaderError = new FXMLLoader(getClass().getResource("successPopup.fxml")); 
                rootError = loaderError.load(); 
                successPopupController = loaderError.getController();
                successPopupController.displaySuccessText("Delete Worker Correctly.");
                sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);

                icon = new Image("application/images/successIcon.png");
                stageError.getIcons().add(icon);	        

                stageError.setTitle("Success");
                stageError.show();

                break;
            case 8:
                loaderError = new FXMLLoader(getClass().getResource("successPopup.fxml")); 
                rootError = loaderError.load(); 
                successPopupController = loaderError.getController();
                successPopupController.displaySuccessText("Success :).");
                sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);

                icon = new Image("application/images/successIcon.png");
                stageError.getIcons().add(icon);	        

                stageError.setTitle("Success");
                stageError.show();

                break;
            default:
                loaderError = new FXMLLoader(getClass().getResource("successPopup.fxml")); 
                rootError = loaderError.load(); 
                successPopupController = loaderError.getController();
                successPopupController.displaySuccessText("Success :).");
                sceneError = new Scene(rootError);
                stageError = new Stage();
                stageError.setScene(sceneError);

                icon = new Image("application/images/successIcon.png");
                stageError.getIcons().add(icon);	        

                stageError.setTitle("Success");
                stageError.show();

                break;
        }
    }
}
