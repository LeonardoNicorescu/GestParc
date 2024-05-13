package com.example.gestparc;

import db.Device;
import db.Devices;
import db.Users;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class DeviceController {

    @FXML
    private Button updateDevice;

    @FXML
    private Text updateDeviceErrorText;

    @FXML
    private TextField updateDeviceModel;

    @FXML
    private TextField updateDeviceName;

    @FXML
    private TextField updateDeviceOperatingSystem;

    @FXML
    private TextField updateDeviceSerialNumber;

    @FXML
    private ComboBox<String> updateDeviceUser;

    @FXML
    private TextField addDeviceName;

    @FXML
    private TextField addDeviceSerialNumber;

    @FXML
    private TextField addDeviceOperatingSystem;

    @FXML
    private TextField addDeviceModel;

    @FXML
    private ComboBox<String> addDeviceUser;

    @FXML
    private Button addDevice;

    @FXML
    private Text addDeviceErrorText;

    private Devices devices;
    private Users users;

    private Device selectedDevice;

    public DeviceController() {
        devices = new Devices();
        users = new Users();
    }

    @FXML
    public void initialize() {
        if(addDeviceUser != null) {
            List<String> userList = users.getAllUsernames();
            addDeviceUser.setItems(FXCollections.observableArrayList(userList));
        }
        if(updateDeviceUser != null) {
            List<String> userList = users.getAllUsernames();
            updateDeviceUser.setItems(FXCollections.observableArrayList(userList));
        }
    }

    @FXML
    private void onClickAddDevice() {
        String name = addDeviceName.getText();
        String serialNumber = addDeviceSerialNumber.getText();
        String operatingSystem = addDeviceOperatingSystem.getText();
        String model = addDeviceModel.getText();
        String username = addDeviceUser.getValue();

        if (name.isEmpty() || serialNumber.isEmpty() || operatingSystem.isEmpty() || model.isEmpty() || username == null) {
            addDeviceErrorText.setFill(Color.RED);
            addDeviceErrorText.setText("Veuillez remplir tous les champs");
        } else {
            String msg = devices.add(name, serialNumber, operatingSystem, model, username);
            addDeviceName.clear();
            addDeviceSerialNumber.clear();
            addDeviceOperatingSystem.clear();
            addDeviceModel.clear();
            addDeviceUser.getSelectionModel().clearSelection();
            addDeviceErrorText.setFill(Color.GREEN);
            addDeviceErrorText.setText(msg);
        }
    }
    @FXML
    private void onClickUpdateDevice() {
        String name = updateDeviceName.getText();
        String serialNumber = updateDeviceSerialNumber.getText();
        String operatingSystem = updateDeviceOperatingSystem.getText();
        String model = updateDeviceModel.getText();
        String username = updateDeviceUser.getValue();
        String userId = new Users().getIdByUsername(username);

        if (name.isEmpty() || serialNumber.isEmpty() || operatingSystem.isEmpty() || model.isEmpty() || userId == null) {
            updateDeviceErrorText.setFill(Color.RED);
            updateDeviceErrorText.setText("Veuillez remplir tous les champs");
        } else {
            selectedDevice.setName(name);
            selectedDevice.setSerialNumber(serialNumber);
            selectedDevice.setOperatingSystem(operatingSystem);
            selectedDevice.setModel(model);
            selectedDevice.setUserUsername(userId);
            devices.updateDevice(selectedDevice);
            updateDeviceErrorText.setFill(Color.GREEN);
            updateDeviceErrorText.setText("Machine mise à jour avec succès");
        }

    }

    public void setSelectedDevice(Device device) {
        this.selectedDevice = device;
        if (device != null) {
            updateDeviceName.setText(device.getName());
            updateDeviceSerialNumber.setText(device.getSerialNumber());
            updateDeviceOperatingSystem.setText(device.getOperatingSystem());
            updateDeviceModel.setText(device.getModel());
            updateDeviceUser.setValue(device.getUserUsername());
        }
    }
}


