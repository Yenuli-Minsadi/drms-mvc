package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dog_rescue_management_system.dto.AppointmentDto;
import lk.ijse.dog_rescue_management_system.dto.DonorDto;
import lk.ijse.dog_rescue_management_system.model.AppointmentModel;
import lk.ijse.dog_rescue_management_system.view.tdm.AppointmentTM;
import lk.ijse.dog_rescue_management_system.view.tdm.DonorTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    public Label lblApptId;
    public TextField txtDogId;
    public TextField txtVetId;
    public DatePicker dateApptDate;
    public TextField txtReason;
    public ComboBox<String> comboStatus;

    public TableView<AppointmentTM> tblAppt;
    public TableColumn<AppointmentTM, String> colApptId;
    public TableColumn<AppointmentTM, String> colDogId;
    public TableColumn<AppointmentTM, String> colVetId;
    public TableColumn<AppointmentTM, String> colApptDate;
    public TableColumn<AppointmentTM, String> colReason;
    public TableColumn<AppointmentTM, String> colStatus;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    private final AppointmentModel appointmentModel = new AppointmentModel();

    @FXML
    void btnApptSaveOnAction(ActionEvent event) {
        String appointmentId = lblApptId.getText();
        String dogId = txtDogId.getText();
        String vetId = txtVetId.getText();
        LocalDate appointmentDate = LocalDate.parse(dateApptDate.getValue().toString());
        String appointmentReason = txtReason.getText();
        String appointmentStatus = comboStatus.getValue().toString();


//        if (donationId.isEmpty() || donorId.isEmpty() || adminId.isEmpty() || shelterId.isEmpty()
//                || comboDonationType.getValue() == null || txtDonationAmount.getText().isEmpty()
//                || dateDonationDate.getValue() == null) {
//            new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
//            return;
//        }


        //dto

        //create dto object wrap data into single unit
        AppointmentDto appointmentDto = new AppointmentDto(
                appointmentId,
                dogId,
                vetId,
                appointmentDate,
                appointmentReason,
                appointmentStatus
        );

        try {
            boolean isSaved = appointmentModel.saveAppointment(appointmentDto);
            if (isSaved){
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Appointment Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Appointment").show();
            }
        } catch (Exception e){
        new Alert(Alert.AlertType.ERROR, "Failed to save Appointment: " + e.getMessage()).show();
        e.printStackTrace();
    }

}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // table column and tm class properties link
        colApptId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        colDogId.setCellValueFactory(new PropertyValueFactory<>("dogId"));
        colVetId.setCellValueFactory(new PropertyValueFactory<>("vetId"));
        colApptDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("appointmentReason"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("appointmentStatus"));

        try {
            loadTableData();
            loadNextId();

            comboStatus.getItems().addAll("Scheduled", "In Progress");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = appointmentModel.getNextAppointmentId();
        lblApptId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<AppointmentDto> appointmentDtoArrayList = appointmentModel.getAllAppointments();
        ObservableList<AppointmentTM> appointmentTMS = FXCollections.observableArrayList();

        for (AppointmentDto appointmentDto : appointmentDtoArrayList) {
            AppointmentTM appointmentTM = new AppointmentTM(
                    appointmentDto.getAppointmentId(),
                    appointmentDto.getDogId(),
                    appointmentDto.getVetId(),
                    appointmentDto.getAppointmentDate(),
                    appointmentDto.getAppointmentReason(),
                    appointmentDto.getAppointmentStatus()
            );
            appointmentTMS.add(appointmentTM);
        }
        tblAppt.setItems(appointmentTMS);
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            // save button (id) -> enable
            btnSave.setDisable(false);

            // update, delete button (id) -> disable
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

//            lblRequestId.setText("");
            txtDogId.setText("");
            txtVetId.setText("");
            dateApptDate.setValue(null);
            txtReason.setText("");
            comboStatus.setValue(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void btnApptSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnApptUpdateOnAction(ActionEvent actionEvent) {

        String appointmentId = lblApptId.getText();
        String dogId = txtDogId.getText();
        String vetId = txtVetId.getText();
        LocalDate appointmentDate = dateApptDate.getValue();
        String appointmentReason = txtReason.getText();
        String appointmentStatus = comboStatus.getValue();

        AppointmentDto appointmentDto = new AppointmentDto(
                appointmentId,
                dogId,
                vetId,
                appointmentDate,
                appointmentReason,
                appointmentStatus
        );

        try {
            boolean isUpdated = appointmentModel.updateAppointment(appointmentDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Appointment Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Appointment").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update Appointment: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    public void btnApptDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String appointmentId = lblApptId.getText();
            try {
                boolean isDeleted = appointmentModel.deleteAppointment(appointmentId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Appointment deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete Appointment.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete Appointment.").show();
            }
        }
    }

    public void btnApptResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }


    public void onClickTable(MouseEvent mouseEvent) {
        AppointmentTM selectedRequest = tblAppt.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            lblApptId.setText(selectedRequest.getAppointmentId());
            txtDogId.setText(selectedRequest.getDogId());
            txtVetId.setText(selectedRequest.getVetId());
            dateApptDate.setValue(selectedRequest.getAppointmentDate());
            txtReason.setText(selectedRequest.getAppointmentReason());
            comboStatus.setValue(selectedRequest.getAppointmentStatus());

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
