package com.example.gestparc;
import db.Device;
import db.Devices;
import db.User;
import db.Users;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Optional;

public class UserController {

    private Devices devices;
    private Users users;
    private User selectedUser;

    @FXML
    private Button updateUser;

    @FXML
    private Button desaffectUser;

    @FXML
    private Button updateDeviceAffectation;

    @FXML
    private Text updateUserErrorText;

    @FXML
    private TextField updateUserFirstName;

    @FXML
    private TextField updateUserLastName;
    @FXML
    public Text updateUserDeviceErrorText;

    @FXML
    private ComboBox<String> updateUserRole;
    @FXML
    private TableView<Device> userDevicesTableView;

    @FXML
    private TableColumn<Device, String> deviceNameColumn;

    @FXML
    private TableColumn<Device, String> deviceSerialNumberColumn;

    @FXML
    private TableColumn<Device, String> deviceOperatingSystemColumn;

    @FXML
    private TableColumn<Device, String> deviceModelColumn;

    @FXML
    private TextField updateUserUsername;

    public void initialize() {
        users = new Users();
        devices = new Devices();
        updateUserRole.getItems().addAll("Utilisateur", "Admin");
    }
    public void setSelectedUser(User user) {
        this.selectedUser = user;
        if (user != null) {
            updateUserFirstName.setText(user.getFirstName());
            updateUserLastName.setText(user.getLastName());
            updateUserUsername.setText(user.getUsername());
            updateUserRole.setPromptText(user.getRole().equals("1") ? "Utilisateur" : "Admin");
            configureDeviceTableColumns();
            loadUserDevices(user.getId());
        }
    }
    private void configureDeviceTableColumns() {
        deviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        deviceSerialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deviceModelColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        deviceOperatingSystemColumn.setCellValueFactory(new PropertyValueFactory<>("operatingSystem"));
    }
    private void loadUserDevices(int userId) {
        List<Device> userDevices = devices.getDevicesByUserId(userId);
        ObservableList<Device> observableList = FXCollections.observableArrayList(userDevices);
        userDevicesTableView.setItems(observableList);
    }

    public void onClickDesaffectDevice(){
        Device selectedDevice = userDevicesTableView.getSelectionModel().getSelectedItem();
        if(selectedDevice != null) {
            selectedDevice.setUserUsername("0");
            devices.updateDevice(selectedDevice);
            loadUserDevices(selectedUser.getId());
            updateUserDeviceErrorText.setFill(Color.GREEN);
            updateUserDeviceErrorText.setText("La machine a été désaffectées avec succès");
        }else {
            updateUserDeviceErrorText.setFill(Color.RED);
            updateUserDeviceErrorText.setText("Veuillez sélectionner une machine");
        }
    }

    @FXML
    private void onClickUpdateDeviceAffectation() {
        Device selectedDevice = userDevicesTableView.getSelectionModel().getSelectedItem();
        if (selectedDevice != null) {
            List<String> allUsers = users.getAllUsernames();
            ChoiceDialog<String> dialog = new ChoiceDialog<>(null, allUsers);
            dialog.setTitle("Changer l'utilisateur affecté");
            dialog.setHeaderText("Sélectionner le nouvel utilisateur");
            dialog.setContentText("Utilisateur:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(username -> {
                String userId = new Users().getIdByUsername(username);
                selectedDevice.setUserUsername(userId);
                devices.updateDevice(selectedDevice);
                loadUserDevices(selectedUser.getId());
                updateUserDeviceErrorText.setFill(Color.GREEN);
                updateUserDeviceErrorText.setText("Machine modifiée avec succès");
            });
        } else {
            updateUserDeviceErrorText.setFill(Color.RED);
            updateUserDeviceErrorText.setText("Veuillez selectionner une machine à modifier");
        }
    }
    public void onClickUpdateUser() {
        if (selectedUser != null) {
            String firstName = updateUserFirstName.getText();
            String lastName = updateUserLastName.getText();
            String username = updateUserUsername.getText();
            String role = updateUserRole.getValue().equals("Utilisateur") ? "1" : "2";

            if (!firstName.isEmpty() && !lastName.isEmpty() && !username.isEmpty()) {
                selectedUser.setFirstName(firstName);
                selectedUser.setLastName(lastName);
                selectedUser.setUsername(username);
                selectedUser.setRole(role);

                users.updateUser(selectedUser);
                updateUserErrorText.setFill(Color.GREEN);
                updateUserErrorText.setText("L'utilisateur a été mis à jour avec succès.");
            } else {
                updateUserErrorText.setFill(Color.RED);
                updateUserErrorText.setText("Veuillez remplir tous les champs.");
            }
        }
    }
}

