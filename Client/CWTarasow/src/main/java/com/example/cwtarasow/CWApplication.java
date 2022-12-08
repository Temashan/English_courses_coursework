package com.example.cwtarasow;

import connectionModule.ConnectionModule;
import entities.UserType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CWApplication extends Application {

    public static ViewLoader viewLoader;

    public static UserType userType;

    @Override
    public void start(Stage stage) throws IOException {
        viewLoader = new ViewLoader(stage);
        ConnectionModule.connectToServer();
        viewLoader.setSceneToStage(ViewLoader.Views.authorization, "Авторизация");
    }

    public static void main(String[] args) {
        launch();
    }
}