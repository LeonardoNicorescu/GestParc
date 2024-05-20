package com.example.gestparc;

import db.Session;
import db.User;
import db.Users;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountController {

    @FXML
    private Text accountRole;

    @FXML
    private TextField updateAccountFirstName;

    @FXML
    private TextField updateAccountLastName;

    @FXML
    private TextField updateAccountUsername;

    @FXML
    private Button updateUser;

    @FXML
    private Button logOutButton;

    @FXML
    private Text updateUserErrorText;

    @FXML
    public void initialize() {
        User loggedInUser = Session.getUser();
        updateAccountFirstName.setText(loggedInUser.getFirstName());
        updateAccountLastName.setText(loggedInUser.getLastName());
        updateAccountUsername.setText(loggedInUser.getUsername());
        accountRole.setText("Administrateur");
        if (loggedInUser != null && !"2".equals(loggedInUser.getRole())) {
            accountRole.setText("Utilisateur");
            updateUser.setDisable(true);
            updateAccountFirstName.setEditable(false);
            updateAccountLastName.setEditable(false);
            updateAccountUsername.setEditable(false);

        }
    }
    @FXML
    private void onClickUpdateAccount() {
        User loggedInUser = Session.getUser();
        if (loggedInUser != null) {
            String firstName = updateAccountFirstName.getText();
            String lastName = updateAccountLastName.getText();
            String username = updateAccountUsername.getText();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !username.isEmpty()) {
                loggedInUser.setFirstName(firstName);
                loggedInUser.setLastName(lastName);
                loggedInUser.setUsername(username);
                Users users = new Users();
                users.updateUser(loggedInUser);
                updateUserErrorText.setFill(Color.GREEN);
                updateUserErrorText.setText("Les informations du compte ont été mises à jour avec succès.");
            } else {
                updateUserErrorText.setFill(Color.RED);
                updateUserErrorText.setText("Veuillez remplir tous les champs.");
            }
        } else {
            updateUserErrorText.setFill(Color.RED);
            updateUserErrorText.setText("Impossible de récupérer l'utilisateur connecté.");
        }
    }
    @FXML
    private void onClickLogOut(){
        GestParc gestParc = new GestParc();
        try {
            gestParc.start((Stage) updateAccountUsername.getScene().getWindow());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Session.clearSession();
    }
}
