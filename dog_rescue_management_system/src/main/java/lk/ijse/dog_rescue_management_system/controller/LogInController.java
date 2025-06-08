package lk.ijse.dog_rescue_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.dog_rescue_management_system.dto.UserDto;
import lk.ijse.dog_rescue_management_system.model.UserModel;
import lk.ijse.dog_rescue_management_system.util.SessionManager;

import java.io.IOException;
import java.sql.SQLException;

public class LogInController {

    @FXML
    private AnchorPane ancLogin;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private StackPane stackLogin;

    @FXML
    private TextField txtUsername;

    private final UserModel userModel = new UserModel();

    @FXML
    public void initialize() {
        // Setup tab order between fields
        txtUsername.setOnAction(event -> pwdPassword.requestFocus());
        pwdPassword.setOnAction(event -> {
            try {
                btnLogInOnAction(new ActionEvent());
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during login");
                e.printStackTrace();
            }
        });

        // Add event listeners for Enter key in fields
        txtUsername.setOnKeyPressed(this::handleKeyPressed);
        pwdPassword.setOnKeyPressed(this::handleKeyPressed);
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (event.getSource() == txtUsername) {
                pwdPassword.requestFocus();
                event.consume();
            } else if (event.getSource() == pwdPassword) {
                try {
                    btnLogInOnAction(new ActionEvent());
                } catch (IOException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during login");
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void btnLogInOnAction(ActionEvent event) throws IOException {
        String username = txtUsername.getText().trim();
        String password = pwdPassword.getText().trim();

        // Validate input fields
        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Username cannot be empty");
            txtUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Password cannot be empty");
            pwdPassword.requestFocus();
            return;
        }

        try {
            // Authenticate user without specifying role - we'll determine it from the database
            UserDto authenticatedUser = userModel.authenticateUser(username, password);

            if (authenticatedUser != null) {
                // Set current user in session
                SessionManager.getInstance().setCurrentUser(authenticatedUser);

//                Stage currentStage = (Stage) ancLogin.getScene().getWindow();
                loadDashboard(authenticatedUser);

            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed",
                        "Invalid username or password. Please check your credentials.");
                pwdPassword.clear();
                pwdPassword.requestFocus();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error",
                    "Could not connect to the database. " + e.getMessage());
        }
    }

    private void loadDashboard(UserDto user) throws IOException {
        String fxmlPath;
        String role = user.getUserRole().toLowerCase();

        switch (role) {
            case "admin":
                fxmlPath = "/view/Dashboard.fxml";
                break;
            case "vet":
                fxmlPath = "/view/VetDashboard.fxml";
                break;
//            case "owner":
//                fxmlPath = "/view/Dashboard.fxml";
//                break;
            default:
                fxmlPath = "/view/Dashboard.fxml";
                break;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Happy Paws Happy Hearts - Dashboard");
        dashboardStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo2.png")));
        dashboardStage.setScene(new Scene(root));
        dashboardStage.setMaximized(true); // or setResizable(true);
        dashboardStage.show();

        // Close or hide the login window
        Stage currentStage = (Stage) ancLogin.getScene().getWindow();
        currentStage.close(); //

        // welcome alert after loading
        String welcomeMessage = "Welcome, " + user.getUserName() + "!";
        switch (role) {
            case "admin":
                welcomeMessage += " (Admin)";
                break;
            case "vet":
                welcomeMessage += " (Veterinarian)";
                break;
//            case "owner":
//                welcomeMessage += " (Owner)";
//                break;
        }

        Alert welcomeAlert = new Alert(Alert.AlertType.INFORMATION);
        welcomeAlert.setTitle("Login Successful");
        welcomeAlert.setHeaderText(null);
        welcomeAlert.setContentText(welcomeMessage);
        welcomeAlert.show();
    }

    @FXML
    private void lblForgotPasswordOnMouseClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ForgotPassword.fxml"));
        Parent load = loader.load();

        Stage forgotPasswordStage = new Stage();
        forgotPasswordStage.setTitle("Forgot Password");
        Image icon = new Image(getClass().getResourceAsStream("/images/logo2.png"));
        forgotPasswordStage.getIcons().add(icon);
        forgotPasswordStage.setScene(new Scene(load));
        forgotPasswordStage.initModality(Modality.APPLICATION_MODAL);
        forgotPasswordStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}