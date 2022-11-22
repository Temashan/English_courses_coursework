package com.example.cwtarasow.Controllers;

import com.example.cwtarasow.CWApplication;
import com.example.cwtarasow.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class UserManagementViewController {

    @FXML
    private Button btnBlock;

    @FXML
    private Button btnUnblock;

    @FXML
    private TableView<?> tableRecords;

    @FXML
    void onBnClickGoBack(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.main, "Курсы");
    }

    @FXML
    void onBnClickBlock(ActionEvent event) {

    }

    @FXML
    void onBnClickUnblock(ActionEvent event) {

    }

}
