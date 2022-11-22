package com.example.cwtarasow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ViewLoader {

    public enum Views{
        authorization,
        registration,
        main,
        profile,
        itemInfo,
        addEdit,
        userManagement,
        requestManagement,
        otherOwners
    }

    private class ViewInfo{
        public final String name;
        public final double width;
        public final double height;

        public ViewInfo(String name, double width, double height){
            this.name = name;
            this.width = width;
            this.height = height;
        }
    }

    private Map<Views, ViewInfo> views;
    private final String pathPrefix = "";
    private final Stage stage;
    public <ControllerType> Pair<ControllerType, Scene> getScene(Views view) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathPrefix + views.get(view).name));
            Scene scene = new Scene(fxmlLoader.load(), views.get(view).width, views.get(view).height);
            ControllerType controller = fxmlLoader.getController();
            return new Pair<>(controller, scene);
        } catch (IOException e) {
            //AlertManager.showErrorAlert("View loading error", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public <ControllerType, ItemType> Pair<ControllerType, ItemType> getItem(String itemName) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CWApplication.class.getResource(pathPrefix + itemName));
            ItemType item = fxmlLoader.load();
            ControllerType controller = fxmlLoader.getController();
            return new Pair<>(controller, item);
        } catch (IOException e) {
            //AlertManager.showErrorAlert("View loading error", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setSceneToStage(Views view, String title) {
        Scene scene = getScene(view).getValue();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public ViewLoader(Stage stage) {
        this.stage = stage;
        this.stage.setResizable(false);

        views = new HashMap<Views, ViewInfo>();

        views.put(Views.authorization, new ViewInfo("Views/AuthorizationView.fxml", 561, 472));
        views.put(Views.registration, new ViewInfo("Views/RegistrationView.fxml", 561, 472));
        views.put(Views.main, new ViewInfo("Views/MainView.fxml", 875, 692));
        views.put(Views.profile, new ViewInfo("Views/ProfileView.fxml", 270, 245));
        views.put(Views.itemInfo, new ViewInfo("Views/InfoView.fxml", 618, 451));
        views.put(Views.addEdit, new ViewInfo("Views/AddEditView.fxml", 618, 451));
        views.put(Views.userManagement, new ViewInfo("Views/UserManagementView.fxml", 875, 692));
        views.put(Views.requestManagement, new ViewInfo("Views/RequestManagementView.fxml", 875, 692));
        views.put(Views.otherOwners, new ViewInfo("Views/OtherOwnersView.fxml", 682, 692));
    }
}
