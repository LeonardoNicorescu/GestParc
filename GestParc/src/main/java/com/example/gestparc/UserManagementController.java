package com.example.gestparc;

import db.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManagementController {

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, Integer> devicesCountColumn;

    @FXML
    private BorderPane mainPane;

    @FXML
    private AnchorPane userManagementPane;

    @FXML
    private Button updateUserManagementForm;

    @FXML
    private Button createUserForm;

    private Users users;
    private Devices devices;

    public void initialize() {
        users = new Users();
        devices = new Devices();
        User loggedInUser = Session.getUser();
        if (loggedInUser != null && !"2".equals(loggedInUser.getRole())) {
            updateUserManagementForm.setDisable(true);
            createUserForm.setDisable(true);

        }
        if(userTableView != null) {
            configureTableColumns();
            loadUsers();
        }
    }

    public void onClickUpdateUserManagementForm(){
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user.fxml"));
            try{
                Parent root = loader.load();
                UserController userController = loader.getController();
                userController.setSelectedUser(selectedUser);
                mainPane.setCenter(root);

            } catch (IOException ex) {
                Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
         else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun utilisateur sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur.");
            alert.showAndWait();
        }
    }

    private void configureTableColumns() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(cellData -> {
            String role = cellData.getValue().getRole();
            return role.equals("1") ? new javafx.beans.property.SimpleStringProperty("Utilisateur") :
                    new javafx.beans.property.SimpleStringProperty("Admin");
        });
        devicesCountColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            int deviceCount = devices.getDeviceCountForUser(user.getId());
            return new ReadOnlyObjectWrapper<>(deviceCount);
        });
    }

    private void loadUsers() {
        List<User> userList = users.getAllUsers();
        ObservableList<User> observableList = FXCollections.observableArrayList(userList);
        userTableView.setItems(observableList);
    }

    @FXML
    private void onClickCreateUserForm(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createUser.fxml"));
        try{
            Parent root = loader.load();
            mainPane.setCenter(root);
        } catch (IOException ex) {
            Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
