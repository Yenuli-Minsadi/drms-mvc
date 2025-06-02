module lk.ijse.dog_rescue_management_system {

    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires javafx.controls;
    requires javafx.graphics;


    opens lk.ijse.dog_rescue_management_system.view.tdm to javafx.base;
    opens lk.ijse.dog_rescue_management_system.controller to javafx.fxml;
    exports lk.ijse.dog_rescue_management_system;
}