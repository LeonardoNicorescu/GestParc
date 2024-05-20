package com.example.gestparc;

import db.User;
import db.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;

public class CreateUserController {


    @FXML
    private BorderPane mainPane;

    @FXML
    private Button createUser;

    @FXML
    private Text createUserErrorText;

    @FXML
    private TextField createUserFirstName;

    @FXML
    private TextField createUserLastName;

    @FXML
    private ComboBox<String> createUserRole;

    @FXML
    private TextField createUserUsername;

    @FXML
    private PasswordField createUserPassword;

    @FXML
    private Text updateUserErrorText;

    public void initialize(){
        createUserRole.getItems().addAll("Utilisateur", "Admin");

    }

    @FXML
    private void onClickCreateUser() {
        int id = 1;
        String firstName = createUserFirstName.getText();
        String lastName = createUserLastName.getText();
        String username = createUserUsername.getText();
        String password = createUserPassword.getText();
        String role = createUserRole.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || role == null) {
            createUserErrorText.setText("Veuillez remplir tous les champs");
        } else {
            Users users = new Users();
            if (users.userExists(username)) {
                createUserErrorText.setText("Nom d'utilisateur déjà pris");
            } else {
                User newUser = new User(id, firstName, lastName, username, password, role.equals("Utilisateur") ? "1" : "2");
                if (users.register(newUser)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("users.fxml"));
                    try {
                        Parent root = loader.load();
                        mainPane.setCenter(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    createUserErrorText.setText("Erreur lors de la création de l'utilisateur");
                }
            }
        }
    }


}
