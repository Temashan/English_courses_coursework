package com.example.cwtarasow.Controllers;

import com.example.cwtarasow.CWApplication;
import com.example.cwtarasow.ViewLoader;
import connectionModule.ConnectionModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class MainViewController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnUserManagement;

    @FXML
    private TextField editFilterCostFrom;

    @FXML
    private TextField editFilterCostTo;

    @FXML
    private TextField editFilterRatingFrom;

    @FXML
    private TextField editFilterRatingTo;

    @FXML
    private TextField editSearchName;
    @FXML
    private Label labelMail;

    @FXML
    private Label labelUsername;

    @FXML
    private TableView<?> tableRecords;

    @FXML
    void onBnClickAdd(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.addEdit, "Добавление");
    }

    @FXML
    void onBnClickDelete(ActionEvent event) {

    }

    @FXML
    void onBnClickEdit(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.addEdit, "Редактирование");
    }

    @FXML
    void onBnClickExit(ActionEvent event) {
        try {
            ConnectionModule.exit();
            CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.authorization, "Авторизация");
        } catch (IOException e) {
            AlertManager.showErrorAlert("Ошибка соединения", "");
        }

    }

    @FXML
    void onBnClickProfile(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.profile, "Редактирование профиля");
    }

    @FXML
    void onBnClickUserManagement(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.userManagement, "Управление пользователями");
    }

    @FXML
    void onBnClickRequestManagement(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.requestManagement, "Управление заявками");
    }

    @FXML
    void onKeyTypedFilterCostFrom(KeyEvent event) {

    }

    @FXML
    void onKeyTypedFilterCostTo(KeyEvent event) {

    }

    @FXML
    void onKeyTypedFilterRatingFrom(KeyEvent event) {

    }

    @FXML
    void onKeyTypedFilterRatingTo(KeyEvent event) {

    }

    @FXML
    void onKeyTypedSearchName(KeyEvent event) {

    }

}
