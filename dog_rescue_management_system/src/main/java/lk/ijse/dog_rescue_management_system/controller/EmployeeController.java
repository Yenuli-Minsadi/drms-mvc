package lk.ijse.dog_rescue_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EmployeeController {

    @FXML
    private AnchorPane ancEmployeeDash;

    @FXML
    void btnAdminOnAction(ActionEvent event) throws IOException {
        ancEmployeeDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/EmpAdmin.fxml"));

        pane.prefWidthProperty().bind(ancEmployeeDash.widthProperty());
        pane.prefHeightProperty().bind(ancEmployeeDash.heightProperty());

        ancEmployeeDash.getChildren().add(pane);

    }

    @FXML
    void btnRescuerOnAction(ActionEvent event) throws IOException {
        ancEmployeeDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/EmpRescuer.fxml"));

        pane.prefWidthProperty().bind(ancEmployeeDash.widthProperty());
        pane.prefHeightProperty().bind(ancEmployeeDash.heightProperty());

        ancEmployeeDash.getChildren().add(pane);
    }

    @FXML
    void btnVetOnAction(ActionEvent event) throws IOException {
        ancEmployeeDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/EmpVets.fxml"));

        pane.prefWidthProperty().bind(ancEmployeeDash.widthProperty());
        pane.prefHeightProperty().bind(ancEmployeeDash.heightProperty());

        ancEmployeeDash.getChildren().add(pane);
    }

}
