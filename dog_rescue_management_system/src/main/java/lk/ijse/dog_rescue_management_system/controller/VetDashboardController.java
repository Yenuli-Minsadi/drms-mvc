package lk.ijse.dog_rescue_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VetDashboardController implements Initializable {

    @FXML
    private AnchorPane ancMainDash;

    @FXML
    private AnchorPane ancMainDashboard;

    @FXML
    void btnAppointmentsOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/VetAppointment.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnDogsOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/DogRegister.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnMedicalRecordsOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/MedicalInfo.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        // Create a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        // Show the alert and wait for user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    // Load the Login.fxml
                    Parent loginParent = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
                    Scene loginScene = new Scene(loginParent);

                    // Get the current stage (window)
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    // Set the login scene
                    window.setScene(loginScene);
                    window.centerOnScreen(); // Optional: center the login page
                    window.show();
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Unable to load Login page!").show();
                    e.printStackTrace();
                }
            }
            // If the user presses CANCEL, do nothing (stay on the dashboard)
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
