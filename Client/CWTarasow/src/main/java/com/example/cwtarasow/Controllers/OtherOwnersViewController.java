package com.example.cwtarasow.Controllers;

import com.example.cwtarasow.CWApplication;
import com.example.cwtarasow.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class OtherOwnersViewController {

    @FXML
    private TableView<?> tableRecords;

    @FXML
    void onBnClickGoBack(ActionEvent event) {
        CWApplication.viewLoader.setSceneToStage(ViewLoader.Views.itemInfo, "Курсы");
    }

}
