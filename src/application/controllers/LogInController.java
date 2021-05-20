package application.controllers;

import java.io.IOException;
import java.rmi.NotBoundException;

//JavaFX imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//Stuff
import java.security.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

//DB imports
import db.pojos.*;
import pojos.users.*;
import db.jdbc.*;
import db.jpa.JPAUserManager;


public class LogInController {
    
	static Connection c ;
	static SQL jdbc = new SQL();
	private static JPAUserManager userman = new JPAUserManager();

    @FXML
    TextField usernameTextfieldLogIn;

    @FXML
    TextField passwordTextFieldLogIn;

    /**
     * Activates when user clicks on "OK" in the main log in window.
     * 
     * @param aEvent
     * @throws IOException
     */
    @FXML
    public void logInMenuOK(ActionEvent aEvent) throws IOException {

        connectDB();
        
        try {
            
            String username = usernameTextfieldLogIn.getText();
            String password = passwordTextFieldLogIn.getText();
    
            User user = userman.checkPassword(username, password);
            if(user == null) {
    
                // System.out.println("Wrong username or password"); TODO - popup: wrong username or password.
                
            } else if(user.getRole().getRole().equalsIgnoreCase("patient")){
    
                switchToPatientMenu(aEvent, user.getUserId());
    
            } else if(user.getRole().getRole().equalsIgnoreCase("medicalStaff")){
    
                switchToWorkerMenu(aEvent, user.getUserId());
    
            }else if(user.getRole().getRole().equalsIgnoreCase("adStaff")){
    
                switchToAdStaffMenu(aEvent, user.getUserId());
    
            }    

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //Main menus for patients/worker/administrationStaff

    private Parent root;
    private Stage stage;
    private Scene scene;

    /**
     * Used to return to the first log in screen.
     * @param aEvent
     * @throws IOException
     */
    private void switchToLogIn(ActionEvent aEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("logInMenu.fxml")); //TODO - need to create the patient menu fxml w scenebuilder
        root = loader.load();
        stage = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchToPatientMenu(ActionEvent aEvent, Integer userId) throws IOException, SQLException, NotBoundException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientMenu.fxml")); 
        root = loader.load(); 

        PatientMenuController patientMenuController =  loader.getController();
        patientMenuController.displayUserName(jdbc.selectPatient(c, userId).getPatientName());
        patientMenuController.displayUserName(jdbc.selectPatient(c, userId).getPatientSurname());

        stage = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchToAdStaffMenu(ActionEvent aEvent, Integer userId) throws IOException, SQLException, NotBoundException{
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adStaffMenu.fxml")); 
        root = loader.load(); 

        AdStaffMenuController adStaffMenuController = loader.getController();
        adStaffMenuController.displayWelcomeText(jdbc.selectWorker(c, userId).getWorkerName());
        // root = FXMLLoader.load(getClass().getResource("adStaffMenu.fxml")); //TODO - need to create the administration Staff menu fxml w scenebuilder
        stage = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void switchToWorkerMenu(ActionEvent aEvent, Integer userId) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientMenu.fxml")); 
        root = loader.load(); 

        stage = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Database connection, disconnection and setup.

    public void connectDB() {
        try{
			c = jdbc.connect();
			
			try{
				jdbc.create(c);			
				userman.connect();
				firstlogin();
			}catch(SQLException ex) {
				if(!ex.getMessage().contains("already exists")) {
					ex.printStackTrace();
				}
			}
			userman.connect();
		}catch(Exception ex) {
			ex.printStackTrace();
		}

    }
    public void disconnectDB(){

        try {
            jdbc.disconnect(c);
            userman.disconnect();    
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void firstlogin(){
		try{
		String username = "admin";
		String password = "admin";
		Role role = userman.getRole(3);
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		User user = new User(username, hash, role);
		userman.newUser(user);
		Worker worker = new Worker("admin","admin","none","adStaff");
		jdbc.addWorker(c, worker);
		Worker created = new Worker(jdbc.selectWorker(c, 1));
		jdbc.createLinkUserWorker(c, user.getUserId(), created.getWorkerId());
		System.out.println("Admin created");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
