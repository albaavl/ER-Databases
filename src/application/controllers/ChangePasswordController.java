package application.controllers;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import db.jpa.JPAUserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojos.users.User;

public class ChangePasswordController {
    
    private static JPAUserManager userManager;
    private ErrorPopup errorPopup;
    private SuccessPopup successPopup;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private TextField newPasswordTextField;
    @FXML
    private Button button;

    @FXML
    private void changePassword() throws IOException, NoSuchAlgorithmException{

        String user, cPassword, nPassword;

        user = usernameTextField.getText();
        cPassword = passwordTextField.getText();
        nPassword = newPasswordTextField.getText();

        if(user == "" || cPassword == "" || nPassword == ""){
            errorPopup.errorPopup(2);
            return;
        }

        if(nPassword.equals(cPassword)){
            errorPopup.errorPopup(17);
            return;
        }

        User u = userManager.checkPassword(user, cPassword);

        if(u == null){
            errorPopup.errorPopup(5);
            return;
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(nPassword.getBytes());
        byte[] hash = md.digest();

        userManager.updateUser(u, hash);

        successPopup.successPopup(12);
        Stage stage = (Stage) button.getScene().getWindow();
		stage.close();

    }

    public void setUserman(JPAUserManager u){
        userManager = u;
    }
    
}
