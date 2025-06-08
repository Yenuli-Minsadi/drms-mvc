package lk.ijse.dog_rescue_management_system.controller;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.dog_rescue_management_system.dto.SentEmailDto;
import lk.ijse.dog_rescue_management_system.util.MailConfigLoader;
//import lk.ijse.dog_rescue_management_system.dto.SentEmailModel;

import java.util.Optional;
import java.util.Properties;

public class AdopterMailController {

    @FXML
    private Button BtnSend;

    @FXML
    private TextArea TxtBody;

    @FXML
    private TextField TxtSub;

    @FXML
    private TextField txtFrom;

    @FXML
    private TextField txtTo;

    public void BtnSendOnAction(ActionEvent actionEvent) {

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to send this email?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isEmpty() || result.get() != ButtonType.OK) {
                return;
            }
            // Show spinner
            //stkLoadingEffect.setVisible(true);
            BtnSend.setDisable(true); // Optional: disable send button while sending

            // Run sending in a background thread to avoid freezing UI
            new Thread(() -> {
                try {
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

                    Message mimeMessage = new MimeMessage(session);
                    mimeMessage.setFrom(new InternetAddress(fromEmail));
                    mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(txtTo.getText()));
                    mimeMessage.setSubject(TxtSub.getText());
                    mimeMessage.setText(TxtBody.getText());

                    Transport.send(mimeMessage);

                    // On success: update UI on JavaFX Application Thread
                    Platform.runLater(() -> {
                        //stkLoadingEffect.setVisible(false);
                        BtnSend.setDisable(false);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Email Sent");
                        alert.setHeaderText(null);
                        alert.setContentText("The email has been sent successfully.");
//                        SentEmailDto emailDTO = new SentEmailDto(
//                                txtTo.getText(),
//                                TxtSub.getText(),
//                                TxtBody.getText(),
//                                null
//
//                        );
//
//                        SentEmailModel.saveEmail(emailDTO);
                        alert.showAndWait();

                        TxtSub.clear();
                        txtTo.clear();
                        TxtBody.clear();
                    });

                } catch (Exception e) {
                    e.printStackTrace();

                    // On failure: update UI on JavaFX Application Thread
                    Platform.runLater(() -> {
                        //stkLoadingEffect.setVisible(false);
                        BtnSend.setDisable(false);
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Failed to send email.");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                        txtTo.clear();
                        TxtSub.clear();
                        TxtBody.clear();
                    });
                }
            }).start();
        }

}
