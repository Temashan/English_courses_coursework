module com.example.cwtarasow {
    requires javafx.controls;
    requires javafx.fxml;
    requires ConnectionModule;
    requires Classes;


    opens com.example.cwtarasow to javafx.fxml;
    exports com.example.cwtarasow;
    exports com.example.cwtarasow.Controllers;
    opens com.example.cwtarasow.Controllers to javafx.fxml;
}