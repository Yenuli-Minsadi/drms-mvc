package lk.ijse.dog_rescue_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.dog_rescue_management_system.util.Reference;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EnterCodeController implements Initializable {

    @FXML
    private TextField txtCode;

    private int referenceOtp;

    @FXML
    void btnVerifyOnAction(ActionEvent event) throws IOException {
        try {
            int enteredOtp = Integer.parseInt(txtCode.getText().trim());

            System.out.println("Entered OTP: " + enteredOtp);
            System.out.println("Expected OTP: " + referenceOtp);

            if (enteredOtp == referenceOtp) {
                // Close current window
                Stage currentStage = (Stage) txtCode.getScene().getWindow();
                currentStage.close();

                // Load the next screen (e.g., Reset Password or Login)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ResetPwd.fxml"));
                Parent root = loader.load();

                Stage newStage = new Stage();
                newStage.setTitle("Login");
                newStage.setScene(new Scene(root));
                newStage.initModality(Modality.NONE);
                newStage.show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid OTP!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric code.").show();
        }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Stage stage = (Stage) txtCode.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.referenceOtp = Reference.emailSentOtp;
        System.out.println("Reference OTP loaded: " + referenceOtp);
    }
}
