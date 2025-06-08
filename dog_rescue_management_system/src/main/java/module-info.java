module lk.ijse.dog_rescue_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires jakarta.mail;
    requires jakarta.activation;
    requires net.sf.jasperreports.core;
//    requires java.desktop;
//    requires itext;

    //opens lk.ijse.dog_rescue_management_system.controller to jasperreports;

    opens lk.ijse.dog_rescue_management_system.view.tdm to javafx.base;
    opens lk.ijse.dog_rescue_management_system.controller to javafx.fxml;
    exports lk.ijse.dog_rescue_management_system;
}