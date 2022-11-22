package com.example.cwtarasow.Controllers;

import com.example.cwtarasow.CWApplication;
import com.example.cwtarasow.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class InfoViewController {

    @FXML
    private Button btnEnterKey;

    @FXML
    private Button btnRequestFiles;

    @FXML
    private Button btnRequestKey;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnShowOwners;

    @FXML
    private TextArea descriptionInput;

    @FXML
    private Label enterKeyLabel;

    @FXML
    private TextField fifthStageInput;

    @FXML
    private TextField fileAddressInput;

    @FXML
    private TextField firstStageInput;

    @FXML
    private TextField fourthStageInput;

    @FXML
    private TextField keyInput;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField secondStageInput;

    @FXML
    private TextField thirdStageInput;

    @FXML
    void onBnClickBrowse(ActionEvent event) {

    }

    @FXML
    void onBnClickEnterKey(ActionEvent event) {

    }

    @FXML
    void onBnClickGoBack(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.main, "Курсы");
    }

    @FXML
    void onBnClickRequestFiles(ActionEvent event) {

    }

    @FXML
    void onBnClickRequestKey(ActionEvent event) {

    }

    @FXML
    void onBnClickSave(ActionEvent event) {

    }

    @FXML
    void onBnClickShowOwners(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.otherOwners, "Другие участники курса");
    }

}
