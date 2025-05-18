package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dog_rescue_management_system.dto.AdopterDto;
import lk.ijse.dog_rescue_management_system.dto.AdoptionProcessDto;
import lk.ijse.dog_rescue_management_system.dto.ShelterDto;
import lk.ijse.dog_rescue_management_system.model.AdoptionModel;
import lk.ijse.dog_rescue_management_system.view.tdm.AdopterTM;
import lk.ijse.dog_rescue_management_system.view.tdm.AdoptionTM;
import lk.ijse.dog_rescue_management_system.view.tdm.DonorTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdoptionController implements Initializable {

    public Label lblAdoptionId;
    public TextField txtDogId;
    public TextField txtAdopterId;
    public DatePicker dateApplicationDate;
    public DatePicker dateApprovalDate;
    public DatePicker dateAdoptionDate;
    public ComboBox<String> comboAdoptionStatus;
    public ComboBox<String> comboAdoptionType;

    public TableView<AdoptionTM> tblAdoption;
    public TableColumn<AdoptionTM, String> colAdptId;
    public TableColumn<AdoptionTM, String> colDogId;
    public TableColumn<AdoptionTM, String> colAdopterId;
    public TableColumn<AdoptionTM, String> colApplyDate;
    public TableColumn<AdoptionTM, String> colApproveDate;
    public TableColumn<AdoptionTM, String> colAdoptDate;
    public TableColumn<AdoptionTM, String> colAdoptStatus;
    public TableColumn<AdoptionTM, String> colAdoptType;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGenRep;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    private final AdoptionModel adoptionModel = new AdoptionModel();

    @FXML
    void btnAdoptionSaveOnAction(ActionEvent event) {
        String adoptProcessId = lblAdoptionId.getText();
        String dogId = txtDogId.getText();
        String adopterId = txtAdopterId.getText();
        LocalDate adopterApplyDate = LocalDate.parse(dateApplicationDate.getValue().toString());
        LocalDate adoptApprovalDate = LocalDate.parse(dateApprovalDate.getValue().toString());
        LocalDate adoptionDate = LocalDate.parse(dateAdoptionDate.getValue().toString());
        String adoptionStatus = comboAdoptionStatus.getValue().toString();
        String adoptionType = comboAdoptionType.getValue().toString();


        AdoptionProcessDto adoptionProcessDto = new AdoptionProcessDto(
                adoptProcessId,
                dogId,
                adopterId,
                adopterApplyDate,
                adoptApprovalDate,
                adoptionDate,
                adoptionStatus,
                adoptionType
        );

        try {
            boolean isSaved = adoptionModel.saveAdoption(adoptionProcessDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Adoption Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Adoption").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Adoption: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // table column and tm class properties link
        colAdptId.setCellValueFactory(new PropertyValueFactory<>("adoptProcessId"));
        colDogId.setCellValueFactory(new PropertyValueFactory<>("dogId"));
        colAdopterId.setCellValueFactory(new PropertyValueFactory<>("adopterId"));
        colApplyDate.setCellValueFactory(new PropertyValueFactory<>("adopterApplyDate"));
        colApproveDate.setCellValueFactory(new PropertyValueFactory<>("adoptApprovalDate"));
        colAdoptDate.setCellValueFactory(new PropertyValueFactory<>("adoptionDate"));
        colAdoptStatus.setCellValueFactory(new PropertyValueFactory<>("adoptionStatus"));
        colAdoptType.setCellValueFactory(new PropertyValueFactory<>("adoptionType"));

        try {
            loadTableData();
            loadNextId();

            comboAdoptionStatus.getItems().addAll("Completed", "Pending", "Approved");
            comboAdoptionType.getItems().addAll("Long Term", "Short Term");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = adoptionModel.getNextAdoptionId();
        lblAdoptionId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<AdoptionProcessDto> adoptionProcessDtoArrayList =adoptionModel.getAllAdoptions();
        ObservableList<AdoptionTM> adoptionTMS = FXCollections.observableArrayList();

        for (AdoptionProcessDto adoptionProcessDto : adoptionProcessDtoArrayList) {
            AdoptionTM adoptionTM = new AdoptionTM(
                    adoptionProcessDto.getAdoptProcessId(),
                    adoptionProcessDto.getDogId(),
                    adoptionProcessDto.getAdopterId(),
                    adoptionProcessDto.getAdopterApplyDate(),
                    adoptionProcessDto.getAdoptApprovalDate(),
                    adoptionProcessDto.getAdoptionDate(),
                    adoptionProcessDto.getAdoptionStatus(),
                    adoptionProcessDto.getAdoptionType()
            );
            adoptionTMS.add(adoptionTM);
        }
        tblAdoption.setItems(adoptionTMS);
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
            txtAdopterId.setText("");
            dateApplicationDate.setValue(null);
            dateApprovalDate.setValue(null);
            dateAdoptionDate.setValue(null);
            comboAdoptionStatus.setValue(null);
            comboAdoptionType.setValue(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void btnAdoptionUpdateOnAction(ActionEvent actionEvent) {
        String adoptProcessId = lblAdoptionId.getText();
        String dogId = txtDogId.getText();
        String adopterId = txtAdopterId.getText();
        LocalDate adopterApplyDate = LocalDate.parse(dateApplicationDate.getValue().toString());
        LocalDate adoptApprovalDate = LocalDate.parse(dateApprovalDate.getValue().toString());
        LocalDate adoptionDate = LocalDate.parse(dateAdoptionDate.getValue().toString());
        String adoptionStatus = comboAdoptionStatus.getValue().toString();
        String adoptionType = comboAdoptionType.getValue().toString();


        AdoptionProcessDto adoptionProcessDto = new AdoptionProcessDto(
                adoptProcessId,
                dogId,
                adopterId,
                adopterApplyDate,
                adoptApprovalDate,
                adoptionDate,
                adoptionStatus,
                adoptionType
        );

        try {
            boolean isUpdated = adoptionModel.updateAdoption(adoptionProcessDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Adoption Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Adoption").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update Adoption: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    public void btnAdoptionSearch(ActionEvent actionEvent) {
    }

    public void btnAdoptionDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String adoptionId = lblAdoptionId.getText();
            try {
                boolean isDeleted = adoptionModel.deleteAdoption(adoptionId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Adoption deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete Adoption.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete Adoption.").show();
            }
        }
    }

    public void btnAdoptionResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnAdoptionGenerateReportOnAction(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
        AdoptionTM selectedRequest = tblAdoption.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            lblAdoptionId.setText(selectedRequest.getAdoptProcessId());
            txtDogId.setText(selectedRequest.getDogId());
            txtAdopterId.setText(selectedRequest.getAdopterId());
            dateApplicationDate.setValue(selectedRequest.getAdopterApplyDate());
            dateApprovalDate.setValue(selectedRequest.getAdoptApprovalDate());
            dateAdoptionDate.setValue(selectedRequest.getAdoptionDate());
            comboAdoptionStatus.setValue(selectedRequest.getAdoptionStatus());
            comboAdoptionType.setValue(selectedRequest.getAdoptionType());

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
