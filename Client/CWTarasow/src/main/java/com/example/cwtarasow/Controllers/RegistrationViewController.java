package com.example.cwtarasow.Controllers;

import com.example.cwtarasow.CWApplication;
import com.example.cwtarasow.ViewLoader;
import connectionModule.ConnectionModule;
import entities.User;
import entities.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationViewController {

    @FXML
    public void initialize(){
        ObservableList<String> countries = FXCollections.observableArrayList();
        countries.add("Беларусь");
        countries.add("Россия");
        countries.add("Казахстан");

        countryComboBox.setItems(countries);
    }
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

        var login = loginInput.getText();
        var password = passwordInput.getText();
        var repeatPassword = repeatPasswordInput.getText();
        var fullname = fioInput.getText();
        var mail = mailInput.getText();
        var country = countryComboBox.getValue();

        if(login.isEmpty() ||password.isEmpty() ||repeatPassword.isEmpty() ||fullname.isEmpty() || mail.isEmpty() || country == null){
            AlertManager.showWarningAlert("Поля должны быть заполнены", "Заполните все поля!");
            return;
        }
        if(!password.equals(repeatPassword)){
            AlertManager.showWarningAlert("Ошибка", "Пароли должны совпадать!");
            return;
        }
        var credentials = fullname.split(" ");
        if(credentials.length != 3)
        {
            AlertManager.showWarningAlert("Ошибка", "Введите корректные ФИО!");
            return;
        }

        User user =new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setUserType(UserType.USER);
        user.setSurame(credentials[0]);
        user.setName(credentials[1]);
        user.setPatronymic(credentials[2]);
        user.setMail(mail);
        user.setCountry(country);

        try {
            var response = ConnectionModule.registration(user);

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
