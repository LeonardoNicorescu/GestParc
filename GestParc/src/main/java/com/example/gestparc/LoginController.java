package com.example.gestparc;

import db.Login;
import db.Session;
import db.User;
import db.Users;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField firstnameTextField;

    @FXML
    private Button goToLogin;

    @FXML
    private Button goToRegister;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private Pane loginBgPane;

    @FXML
    private Button loginButton;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private PasswordField newpasswordTextField;

    @FXML
    private TextField newusernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Pane registerBgPane;

    @FXML
    private Button registerButton;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private Text registererrorText;

    @FXML
    private Text loginerrorText;

    @FXML
    private TextField usernameTextField;

    @FXML
    private void onClickGoToLogin() {
        registerPane.setVisible(false);
        registerBgPane.setVisible(true);
        loginPane.setVisible(true);
        loginBgPane.setVisible(false);
    }

    @FXML
    private void onClickGoToRegister() {
        registerPane.setVisible(true);
        registerBgPane.setVisible(false);
        loginPane.setVisible(false);
        loginBgPane.setVisible(true);
    }
    @FXML
    private void onClickLogin() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            loginerrorText.setText("Veuillez remplir tous les champs");
        } else {
            Login user = new Login();
            int count = user.login(username, password);
            if(count == 1) {
                Users users = new Users();
                User loggedInUser = users.getUserByUsername(username);
                if (loggedInUser != null) {
                    Session.setUser(loggedInUser); // Stocker l'utilisateur connecté dans la session
                    GestParc gestParc = new GestParc();
                    try {
                        gestParc.openGestParcScene((Stage) usernameTextField.getScene().getWindow());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Gérer le cas où l'utilisateur n'est pas trouvé
                    loginerrorText.setText("Identifiants erronés");
                }
            } else if (count == 0 ) {
                loginerrorText.setText("Identifiants erronés");
            }
        }
    }

    @FXML
    private void onClickRegister() {
        String firstName = firstnameTextField.getText();
        String lastName = lastnameTextField.getText();
        String username = newusernameTextField.getText();
        String password = newpasswordTextField.getText();
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            registererrorText.setText("Veuillez remplir tous les champs");
        } else {
            Login user = new Login();
            if (user.userExists(username)) {
                registererrorText.setText("Nom d'utilisateur non valide");
            } else if (user.register(firstName, lastName, username, password)) {
                Users users = new Users();
                User registeredUser = users.getUserByUsername(username);
                if (registeredUser != null) {
                    Session.setUser(registeredUser); // Stocker l'utilisateur connecté dans la session
                    try {
                        GestParc gestParc = new GestParc();
                        gestParc.openGestParcScene((Stage) usernameTextField.getScene().getWindow());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    registererrorText.setText("Erreur lors de l'inscription");
                }
            } else {
                registererrorText.setText("Erreur lors de l'inscription");
            }
        }
    }
}
