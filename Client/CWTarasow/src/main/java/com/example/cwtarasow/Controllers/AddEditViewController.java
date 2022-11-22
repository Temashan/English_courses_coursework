package com.example.cwtarasow.Controllers;

import com.example.cwtarasow.CWApplication;
import com.example.cwtarasow.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddEditViewController {

    @FXML
    private TextArea descriptionInput;

    @FXML
    private TextField fifthStageInput;

    @FXML
    private TextField fileAddressInput;

    @FXML
    private TextField firstStageInput;

    @FXML
    private TextField fourthStageInput;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField secondStageInput;

    @FXML
    private TextField thirdStageInput;

    @FXML
    void onBnClickGoBack(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.main, "Курсы");
    }

    @FXML
    void onBnClickBrowse(ActionEvent event) {

    }

    @FXML
    void onBnClickSave(ActionEvent event) {

    }

}
