package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.rmi.NotBoundException;

import java.security.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import db.pojos.*;
import pojos.users.*;
import db.jdbc.*;
import db.jpa.JPAUserManager;

public class Main2 extends Application {

	// static Connection c ;
	// static SQL jdbc = new SQL();
	// private static JPAUserManager userman = new JPAUserManager();

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("views/logInMenu.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Quiron's ER Database");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
