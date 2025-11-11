module com.example.taller5 {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.taller5.app;
    opens com.example.taller5.app to javafx.fxml;

    exports com.example.taller5.controllers;
    opens com.example.taller5.controllers to javafx.fxml;

    exports com.example.taller5.Models;
    opens com.example.taller5.Models to javafx.base;
}