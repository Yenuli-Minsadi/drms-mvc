package lk.ijse.dog_rescue_management_system.controller;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.dog_rescue_management_system.util.MailConfigLoader;
import lk.ijse.dog_rescue_management_system.util.Reference;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

public class ForgotPasswordController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSendCode;

    @FXML
    private TextField txtEmail;

    private int generatedOtp;

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSendCodeOnAction(ActionEvent event) {
        String email = txtEmail.getText().trim();

        if (email.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Email cannot be empty!").show();
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Send verification code to this email?");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        btnSendCode.setDisable(true);

        new Thread(() -> {
            try {
                boolean emailExists = checkEmailInDatabase(email); // You should implement this method

                if (!emailExists) {
                    Platform.runLater(() -> {
                        btnSendCode.setDisable(false);
                        new Alert(Alert.AlertType.ERROR, "Email does not exist in the database!").show();
                    });
                    return;
                }

                generatedOtp = generateOtp();
                Reference.email = email;
                Reference.emailSentOtp = generatedOtp;

                sendEmail(email, "Your OTP Code", "Your OTP is: " + generatedOtp);

                Platform.runLater(() -> {
                    btnSendCode.setDisable(false);
                    System.out.println("OTP: " + generatedOtp);
                    System.out.println("Email: " + email);

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyAccount.fxml"));
                        Parent root = loader.load();

                        Stage stage = new Stage();
                        stage.setTitle("Enter Verification Code");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.centerOnScreen();
                        stage.show();

                        ((Stage) txtEmail.getScene().getWindow()).close();
                    } catch (IOException e) {
                        showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to open verification window.");
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    btnSendCode.setDisable(false);
                    new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
                });
            }
        }).start();
    }

    private boolean checkEmailInDatabase(String email) {
        // TODO: Connect to your DB and check if email exists
        // Return true or false based on actual DB result
        return true; // For testing/demo purposes
    }

    private int generateOtp() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    private void sendEmail(String toEmail, String subject, String messageBody) throws MessagingException {
        String fromEmail = MailConfigLoader.getEmail();
        String password = MailConfigLoader.getPassword();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(messageBody);

        Transport.send(message);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
