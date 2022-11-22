module com.example.servergui {
    requires javafx.controls;
    requires javafx.fxml;
    requires ProcessingLayer;

    opens ServerGui to javafx.fxml;
    exports ServerGui;
}