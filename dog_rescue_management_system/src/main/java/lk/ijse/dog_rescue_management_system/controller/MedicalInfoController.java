package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dog_rescue_management_system.dto.MedicalRecordDto;
import lk.ijse.dog_rescue_management_system.dto.RequestCaseDto;
import lk.ijse.dog_rescue_management_system.dto.ShelterDto;
import lk.ijse.dog_rescue_management_system.model.MedicalInfoModel;
import lk.ijse.dog_rescue_management_system.view.tdm.MedicalInfoTM;
import lk.ijse.dog_rescue_management_system.view.tdm.RequestTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MedicalInfoController implements Initializable {

    public Label lblMedRecId;
    public TextField txtDogId;
    public TextField txtVetId;
    public DatePicker dateMedRecDate;
    public TextField txtDiagnosis;
    public TextField txtTreatments;
    public TextField txtMedication;
    public TextArea txtNotes;
    public ComboBox<String> comboLabReport;
    public TextField txtLabReportRef;

    public TableView<MedicalInfoTM> tblMedicalRec;
    public TableColumn<MedicalInfoTM, String> colMedRecId;
    public TableColumn<MedicalInfoTM, String> colDogId;
    public TableColumn<MedicalInfoTM, String> colVetId;
    public TableColumn<MedicalInfoTM, String> colMedRecDate;
    public TableColumn<MedicalInfoTM, String> colDiagnosis;
    public TableColumn<MedicalInfoTM, String> colTreatments;
    public TableColumn<MedicalInfoTM, String> colMedications;
    public TableColumn<MedicalInfoTM, String> colVetNotes;
    public TableColumn<MedicalInfoTM, String> colLabRep;
    public TableColumn<MedicalInfoTM, String> colLabRepRef;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGenReport;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    private final MedicalInfoModel medicalInfoModel = new MedicalInfoModel();



    @FXML
    void btnMedRecSaveOnAction(ActionEvent event) {
        String medicalRecordId = lblMedRecId.getText();
        String dogId = txtDogId.getText();
        String vetId = txtVetId.getText();
        LocalDate medicalRecordDate = LocalDate.parse(dateMedRecDate.getValue().toString());
        String diagnosis = txtDiagnosis.getText();
        String treatment = txtTreatments.getText();
        String medication = txtMedication.getText();
        String vetNote = txtNotes.getText();
        String hasLabReport = comboLabReport.getValue().toString();
        String labReportReference = txtLabReportRef.getText();


        MedicalRecordDto medicalRecordDto = new MedicalRecordDto(
                medicalRecordId,
                dogId,
                vetId,
                medicalRecordDate,
                diagnosis,
                treatment,
                medication,
                vetNote,
                hasLabReport,
                labReportReference
        );

        try {
            boolean isSaved = medicalInfoModel.saveMedRec(medicalRecordDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Record Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Record").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Record: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // table column and tm class properties link
        colMedRecId.setCellValueFactory(new PropertyValueFactory<>("medicalRecordId"));
        colDogId.setCellValueFactory(new PropertyValueFactory<>("dogId"));
        colVetId.setCellValueFactory(new PropertyValueFactory<>("vetId"));
        colMedRecDate.setCellValueFactory(new PropertyValueFactory<>("medicalRecordDate"));
        colDiagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        colTreatments.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        colMedications.setCellValueFactory(new PropertyValueFactory<>("medication"));
        colVetNotes.setCellValueFactory(new PropertyValueFactory<>("vetNote"));
        colLabRep.setCellValueFactory(new PropertyValueFactory<>("hasLabReport"));
        colLabRepRef.setCellValueFactory(new PropertyValueFactory<>("labReportReference"));

        try {
            loadTableData();
            loadNextId();

            // Add options to donation type combo box
            comboLabReport.getItems().addAll("Yes", "No");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = medicalInfoModel.getNextMedRecId();
        lblMedRecId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<MedicalRecordDto> medicalRecordDtoArrayList = medicalInfoModel.getAllMedInfo();
        ObservableList<MedicalInfoTM> medInfoTMS = FXCollections.observableArrayList();

        for (MedicalRecordDto medicalRecordDto : medicalRecordDtoArrayList){
            MedicalInfoTM medicalInfoTM = new MedicalInfoTM(
                    medicalRecordDto.getMedicalRecordId(),
                    medicalRecordDto.getDogId(),
                    medicalRecordDto.getVetId(),
                    medicalRecordDto.getMedicalRecordDate(),
                    medicalRecordDto.getDiagnosis(),
                    medicalRecordDto.getTreatment(),
                    medicalRecordDto.getMedication(),
                    medicalRecordDto.getVetNote(),
                    medicalRecordDto.getHasLabReport(),
                    medicalRecordDto.getLabReportReference()
            );
            medInfoTMS.add(medicalInfoTM);
        }
        tblMedicalRec.setItems(medInfoTMS);
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

            txtDogId.setText("");
            txtVetId.setText("");
            dateMedRecDate.setValue(null);
            txtDiagnosis.setText("");
            txtTreatments.setText("");
            txtMedication.setText("");
            txtNotes.setText("");
            comboLabReport.setValue(null);
            txtLabReportRef.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void btnMedRecSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnMedRecUpdateOnAction(ActionEvent actionEvent) {

        String medicalRecordId = lblMedRecId.getText();
        String dogId = txtDogId.getText();
        String vetId = txtVetId.getText();
        LocalDate medicalRecordDate = LocalDate.parse(dateMedRecDate.getValue().toString());
        String diagnosis = txtDiagnosis.getText();
        String treatment = txtTreatments.getText();
        String medication = txtMedication.getText();
        String vetNote = txtNotes.getText();
        String hasLabReport = comboLabReport.getValue();
        String labReportReference = txtLabReportRef.getText();


        MedicalRecordDto medicalRecordDto = new MedicalRecordDto(
                medicalRecordId,
                dogId,
                vetId,
                medicalRecordDate,
                diagnosis,
                treatment,
                medication,
                vetNote,
                hasLabReport,
                labReportReference
        );

        try {
            boolean isUpdated = medicalInfoModel.updateMedRec(medicalRecordDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Record Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Record").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Record: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    public void btnMedRecDeleteOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String medicalRecordId = lblMedRecId.getText();
            try {
                boolean isDeleted = medicalInfoModel.deleteMedRec(medicalRecordId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Record deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete Record.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete Record.").show();
            }
        }
    }

    public void btnMedRecResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnMedRecGenerateReportOnAction(ActionEvent actionEvent) {
        
    }

    public void onClickTable(MouseEvent mouseEvent) {
        MedicalInfoTM selectedMedRec = tblMedicalRec.getSelectionModel().getSelectedItem();

        if (selectedMedRec != null) {
            lblMedRecId.setText(selectedMedRec.getMedicalRecordId());
            txtDogId.setText(selectedMedRec.getDogId());
            txtVetId.setText(selectedMedRec.getVetId());
            dateMedRecDate.setValue(selectedMedRec.getMedicalRecordDate());
            txtDiagnosis.setText(selectedMedRec.getDiagnosis());
            txtTreatments.setText(selectedMedRec.getTreatment());
            txtMedication.setText(selectedMedRec.getMedication());
            txtNotes.setText(selectedMedRec.getVetNote());
            comboLabReport.setValue(selectedMedRec.getHasLabReport());
            txtLabReportRef.setText(selectedMedRec.getLabReportReference());

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
