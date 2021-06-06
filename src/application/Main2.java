package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main2 extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("controllers/logInMenu.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

			Image icon = new Image("application/images/healthcare.png");
			primaryStage.getIcons().add(icon);	

			primaryStage.setResizable(false);
			primaryStage.setTitle("Quiron's ER Database");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void name() {
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
