package com.example.cwtarasow.Controllers;

import com.example.cwtarasow.CWApplication;
import com.example.cwtarasow.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ProfileViewController {

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private TextField fioInput;

    @FXML
    private TextField mailInput;

    @FXML
    void onBnClickApply(ActionEvent event) {

    }

    @FXML
    void onBnClickGoBack(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.main, "Курсы");
    }

}
