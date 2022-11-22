package com.example.cwtarasow.Controllers;

import com.example.cwtarasow.CWApplication;
import com.example.cwtarasow.ViewLoader;
import connectionModule.ConnectionModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationViewController {

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private TextField fioInput;

    @FXML
    private TextField loginInput;

    @FXML
    private TextField mailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private PasswordField repeatPasswordInput;

    @FXML
    void onBnClickEnter(ActionEvent event) {

        try {
            var response = ConnectionModule.registration(loginInput.getText(), passwordInput.getText(), fioInput.getText(), "");

            switch (response) {
                case SUCCESSFULLY -> {
                    CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.main, "Курсы");
                }
                default -> {
                    AlertManager.showErrorAlert("Ошибка регистрации", "");
                }
            }
        } catch (Exception e) {
            AlertManager.showErrorAlert("Ошибка соединения", "");
        }


    }

    @FXML
    void onBnClickGoBack(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.authorization, "Авторизация");
    }
}
