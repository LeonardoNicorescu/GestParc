package com.example.gestparc;

import db.*;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomepageController {


    @FXML
    private Button affectDeviceButton;

    @FXML
    private AnchorPane home;

    @FXML
    private Button homeButton;

    @FXML
    private TextField researchDevice;

    @FXML
    private TableView<Device> deviceTableView;

    @FXML
    private TableColumn<Device, Integer> idColumn;

    @FXML
    private TableColumn<Device, String> nameColumn;

    @FXML
    private TableColumn<Device, String> serialNumberColumn;

    @FXML
    private TableColumn<Device, String> operatingSystemColumn;

    @FXML
    private TableColumn<Device, String> modelColumn;

    @FXML
    private TableColumn<Device, String> userUsernameColumn;

    @FXML
    public BorderPane mainPane;

    @FXML
    private Button accountbutton;

    @FXML
    private AnchorPane navbar;

    @FXML
    private Button usersButton;

    @FXML
    private Button deleteDeviceButton;

    @FXML
    private Button addDeviceForm;

    @FXML
    private Button updateDeviceButton;

    private Devices devices;

    public HomepageController() {
        devices = new Devices();
    }

    private FilteredList<Device> filteredData;


    @FXML
    public void initialize() {
        configureTableColumns();
        User loggedInUser = Session.getUser();
        if (loggedInUser != null && !"2".equals(loggedInUser.getRole())) {
            deleteDeviceButton.setDisable(true);
            addDeviceForm.setDisable(true);
            affectDeviceButton.setDisable(true);
            updateDeviceButton.setDisable(true);
        }
        List<Device> devicesList = devices.getAllDevices();
        filteredData = new FilteredList<>(FXCollections.observableArrayList(devicesList), p -> true);
        deviceTableView.setItems(filteredData);
        researchDevice.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(device -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (device.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (device.getSerialNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (device.getOperatingSystem().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (device.getModel().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (device.getUserUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void configureTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        operatingSystemColumn.setCellValueFactory(new PropertyValueFactory<>("operatingSystem"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        userUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("userUsername"));
    }
    public void loadPage(String page){
        Parent root = null;

        try{
            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.setCenter(root);
    }

    @FXML

    private void onClickHome() {
        mainPane.setCenter(home);
        initialize();
    }
    @FXML

    private void onClickUsers() {
        loadPage("users");
    }


    @FXML
    private void onClickUpdateDeviceForm() {
        Device selectedDevice = deviceTableView.getSelectionModel().getSelectedItem();
        if (selectedDevice != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("device.fxml"));
            try {
                Parent root = loader.load();
                DeviceController deviceController = loader.getController();
                deviceController.setSelectedDevice(selectedDevice);
                mainPane.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun appareil sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un appareil à modifier.");
            alert.showAndWait();
        }
    }





    @FXML
    private void onClickAddDeviceForm(){
        loadPage("addDevice");
    }

    @FXML
    private void onClickDeleteDevice() {
        Device selectedDevice = deviceTableView.getSelectionModel().getSelectedItem();
        if (selectedDevice != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette machine ?");

            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    devices.deleteDevice(selectedDevice);
                    filteredData.getSource().remove(selectedDevice);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun appareil sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un appareil à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    private void onClickAffectDevice() {
        Device selectedDevice = deviceTableView.getSelectionModel().getSelectedItem();
        if (selectedDevice != null) {
            List<String> usernames = new Users().getAllUsernames();
            ChoiceDialog<String> dialog = new ChoiceDialog<>(null, usernames);
            dialog.setTitle("Affecter un utilisateur à la machine");
            dialog.setHeaderText(null);
            dialog.setContentText("Sélectionnez un utilisateur:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(username -> {
                String userId = new Users().getIdByUsername(username);
                selectedDevice.setUserUsername(userId);
                devices.updateDevice(selectedDevice);
                initialize();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun appareil sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un appareil.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onClickAccountForm(){
        loadPage("account");
    }
}
