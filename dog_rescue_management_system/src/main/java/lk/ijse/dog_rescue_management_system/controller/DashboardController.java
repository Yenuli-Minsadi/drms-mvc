package lk.ijse.dog_rescue_management_system.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    private AnchorPane ancMainDash;

    @FXML
    private AnchorPane ancMainDashboard;

    @FXML
    private Label lblClock;

    @FXML
    private Label lblDay;


    @FXML
    private Button btnDonExp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startClock();
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

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), e -> {
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblClock.setText(currentTime.format(formatter));

            String currentDay = java.time.LocalDate.now().getDayOfWeek().toString();
            // Capitalize first letter only (optional formatting)
            String formattedDay = currentDay.substring(0, 1).toUpperCase() + currentDay.substring(1).toLowerCase();
            lblDay.setText(formattedDay);
        }));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
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

    public void btnDonExpOnAction(ActionEvent actionEvent) {
        try {
            // Load the FXML for the donation vs expense pie chart
            Parent root = FXMLLoader.load(getClass().getResource("/view/DonVsExpChart.fxml"));

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Donation vs Expense Report");
            stage.setScene(new Scene(root));
            stage.setResizable(false); // Optional: prevent resizing
            stage.centerOnScreen();    // Optional: center on screen
            stage.show();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open Donation vs Expense window.").show();
            e.printStackTrace();
        }
    }
}
