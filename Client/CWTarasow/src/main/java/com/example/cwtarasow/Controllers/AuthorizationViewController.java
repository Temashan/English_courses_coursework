package com.example.cwtarasow.Controllers;

import com.example.cwtarasow.CWApplication;
import com.example.cwtarasow.ViewLoader;
import connectionModule.ConnectionModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import entities.UserType;

public class AuthorizationViewController {


    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;


    @FXML
    void onBnClickAuthorization(ActionEvent event) {

        var login = loginInput.getText();
        var password = passwordInput.getText();

        try {

            CWApplication.userType = ConnectionModule.singUp(login, password);
           if(CWApplication.userType == UserType.UNDEFINED) {
               AlertManager.showErrorAlert("Пользователь не найден!", "");
               return;
           }

            CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.main, "Курсы");

        } catch (Exception e) {
            AlertManager.showErrorAlert("Ошибка соединения", "");
        }
    }

    @FXML
    void onBnClickRegistration(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.registration, "Регистрация");
    }

}
