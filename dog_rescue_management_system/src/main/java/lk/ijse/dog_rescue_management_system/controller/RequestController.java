package lk.ijse.dog_rescue_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RequestController {

    @FXML
    private AnchorPane ancRequestDash;

    @FXML
    void btnEmergencyCaseOnAction(ActionEvent event) throws IOException {
        ancRequestDash.getChildren().clear();

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/EmergencyCase.fxml"));

        pane.prefWidthProperty().bind(ancRequestDash.widthProperty());
        pane.prefHeightProperty().bind(ancRequestDash.heightProperty());

        // Set anchors to all sides to fill the entire parent
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);

        ancRequestDash.getChildren().add(pane);
    }

    @FXML
    void btnGenCaseOnAction(ActionEvent event) throws IOException {
        ancRequestDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/RequestCase.fxml"));

        pane.prefWidthProperty().bind(ancRequestDash.widthProperty());
        pane.prefHeightProperty().bind(ancRequestDash.heightProperty());

        ancRequestDash.getChildren().add(pane);
    }

}
