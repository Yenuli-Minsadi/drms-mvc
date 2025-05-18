package lk.ijse.dog_rescue_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DogController {

    @FXML
    private AnchorPane ancDogDash;

    @FXML
    void btnDogMedicalOnAction(ActionEvent event) throws IOException {
        ancDogDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/MedicalInfo.fxml"));

        pane.prefWidthProperty().bind(ancDogDash.widthProperty());
        pane.prefHeightProperty().bind(ancDogDash.heightProperty());

        ancDogDash.getChildren().add(pane);
    }

    @FXML
    void btnDogProfileOnAction(ActionEvent event) throws IOException {
        ancDogDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/DogProfile.fxml"));

        pane.prefWidthProperty().bind(ancDogDash.widthProperty());
        pane.prefHeightProperty().bind(ancDogDash.heightProperty());

        ancDogDash.getChildren().add(pane);
    }

    @FXML
    void btnDogRegisterOnAction(ActionEvent event) throws IOException {
        ancDogDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/DogRegister.fxml"));

        pane.prefWidthProperty().bind(ancDogDash.widthProperty());
        pane.prefHeightProperty().bind(ancDogDash.heightProperty());

        ancDogDash.getChildren().add(pane);
    }

}
