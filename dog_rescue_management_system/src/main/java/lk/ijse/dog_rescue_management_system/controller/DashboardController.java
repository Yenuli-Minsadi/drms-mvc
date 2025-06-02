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

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane ancMainDash;

    @FXML
    private AnchorPane ancMainDashboard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        navigateTo("/view/HomePage.fxml");
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        navigateTo("/view/HomePage.fxml");
    }

    @FXML
    void btnRequestOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Request.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnEmployeesOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Employee.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnDonorsOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Donor.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnSheltersOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Shelter.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }



    @FXML
    void btnDogsOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Dog.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnExpensesOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Expense.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnAdoptersOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Adopter.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnAdoptionOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Adoption.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnDonationOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnReportsOnAction(ActionEvent event) throws IOException {
        ancMainDash.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));

        pane.prefWidthProperty().bind(ancMainDash.widthProperty());
        pane.prefHeightProperty().bind(ancMainDash.heightProperty());

        ancMainDash.getChildren().add(pane);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");


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
        });
    }

    private void navigateTo(String path) {
        try {
            System.out.println("Hi");
            ancMainDash.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource(path));

            pane.prefWidthProperty().bind(ancMainDash.widthProperty());
            pane.prefHeightProperty().bind(ancMainDash.heightProperty());
            ancMainDash.getChildren().add(pane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found!...").show();
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
