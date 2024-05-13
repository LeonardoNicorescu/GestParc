package com.example.gestparc;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GestParc extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GestParc.class.getResource("login.fxml"));
        Scene login = new Scene(fxmlLoader.load(), 750, 500);
        stage.setTitle("Connectez vous à GestParc");
        stage.setScene(login);
        stage.show();
    }

    public void openGestParcScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GestParc.class.getResource("homepage.fxml"));
        Scene gestParcScene = new Scene(fxmlLoader.load(), 900, 700);
        stage.setScene(gestParcScene);
        stage.setResizable(false);
        stage.setTitle("GestParc");
        HomepageController controller = fxmlLoader.getController();
        controller.initialize();
    }
}