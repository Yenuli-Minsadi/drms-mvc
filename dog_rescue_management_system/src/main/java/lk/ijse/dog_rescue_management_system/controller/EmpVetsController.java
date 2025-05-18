package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dog_rescue_management_system.dto.DogDto;
import lk.ijse.dog_rescue_management_system.dto.RequestCaseDto;
import lk.ijse.dog_rescue_management_system.dto.VetDto;
import lk.ijse.dog_rescue_management_system.model.EmpVetsModel;
import lk.ijse.dog_rescue_management_system.view.tdm.RequestTM;
import lk.ijse.dog_rescue_management_system.view.tdm.VetTM;


import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmpVetsController implements Initializable {
    public Label lblVetId;
    public TextField txtVetName;
    public TextField txtClinicName;
    public ComboBox<String> comboSpecialization;
    public TextField txtVetLicenseNumber;
    public TextField txtContact;
    public TextField txtEmail;
    public TextField txtAddress;
    public ComboBox<String> comboAvailability;
    public TextField txtSalary;

    public TableView<VetTM> tblVet;
    public TableColumn<VetTM, String> colVetId;
    public TableColumn<VetTM, String> colVetName;
    public TableColumn<VetTM, String> colVetClinicName;
    public TableColumn<VetTM, String> colVetSpecialization;
    public TableColumn<VetTM, String> colVetLicenseNum;
    public TableColumn<VetTM, String> colVetContact;
    public TableColumn<VetTM, String> colVetEmail;
    public TableColumn<VetTM, String> colVetAddress;
    public TableColumn<VetTM, String> colVetAvailability;
    public TableColumn<VetTM, Double> colVetSalary;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGenRep;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    private final EmpVetsModel empVetsModel = new EmpVetsModel();

    @FXML
    void btnVetSaveOnAction(ActionEvent event) {
        String vetId = lblVetId.getText();
        String vetName = txtVetName.getText();
        String vetClinicName = txtClinicName.getText();
        String vetSpecialization = comboSpecialization.getValue().toString();
        String vetLicenseNum = txtVetLicenseNumber.getText();
        String vetContact = txtContact.getText();
        String vetEmail = txtEmail.getText();
        String vetAddress = txtAddress.getText();
        String vetAvailability = comboAvailability.getValue().toString();
        Double vetSalary = Double.valueOf(txtSalary.getText());

        VetDto vetDto = new VetDto (
                vetId,
                vetName,
                vetClinicName,
                vetSpecialization,
                vetLicenseNum,
                vetContact,
                vetEmail,
                vetAddress,
                vetAvailability,
                vetSalary
        );

        try {
            boolean isSaved = empVetsModel.saveVet(vetDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Vet Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Vet").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Vet: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // table column and tm class properties link
        colVetId.setCellValueFactory(new PropertyValueFactory<>("vetId"));
        colVetName.setCellValueFactory(new PropertyValueFactory<>("vetName"));
        colVetClinicName.setCellValueFactory(new PropertyValueFactory<>("vetClinicName"));
        colVetSpecialization.setCellValueFactory(new PropertyValueFactory<>("vetSpecialization"));
        colVetLicenseNum.setCellValueFactory(new PropertyValueFactory<>("vetLicenseNum"));
        colVetContact.setCellValueFactory(new PropertyValueFactory<>("vetContact"));
        colVetEmail.setCellValueFactory(new PropertyValueFactory<>("vetEmail"));
        colVetAddress.setCellValueFactory(new PropertyValueFactory<>("vetAddress"));
        colVetAvailability.setCellValueFactory(new PropertyValueFactory<>("vetAvailability"));
        colVetSalary.setCellValueFactory(new PropertyValueFactory<>("vetSalary"));

        try {
            loadTableData();
            loadNextId();

            comboSpecialization.getItems().addAll("Cardiology", "Dentistry", "Dermatology", "General Veterinary", "Neurology", "Ophthalmology", "Orthopedics", "Radiology", "Surgery");
            comboAvailability.getItems().addAll("Available", "Unavailable");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = empVetsModel.getNextVetId();
        lblVetId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<VetDto> vetDtoArrayList = empVetsModel.getAllVets();
        ObservableList<VetTM> vetTMS = FXCollections.observableArrayList();

        for (VetDto vetDto : vetDtoArrayList){
            VetTM vetTM = new VetTM(
                    vetDto.getVetId(),
                    vetDto.getVetName(),
                    vetDto.getVetClinicName(),
                    vetDto.getVetSpecialization(),
                    vetDto.getVetLicenseNum(),
                    vetDto.getVetContact(),
                    vetDto.getVetEmail(),
                    vetDto.getVetAddress(),
                    vetDto.getVetAvailability(),
                    vetDto.getVetSalary()
            );
            vetTMS.add(vetTM);
        }
        tblVet.setItems(vetTMS);
    }

    public void btnVetSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnVetUpdateOnAction(ActionEvent actionEvent) {
        String vetId = lblVetId.getText();
        String vetName = txtVetName.getText();
        String vetClinicName = txtClinicName.getText();
        String vetSpecialization = comboSpecialization.getValue().toString();
        String vetLicenseNum = txtVetLicenseNumber.getText();
        String vetContact = txtContact.getText();
        String vetEmail = txtEmail.getText();
        String vetAddress = txtAddress.getText();
        String vetAvailability = comboAvailability.getValue().toString();
        Double vetSalary = Double.valueOf(txtSalary.getText());

        VetDto vetDto = new VetDto (
                vetId,
                vetName,
                vetClinicName,
                vetSpecialization,
                vetLicenseNum,
                vetContact,
                vetEmail,
                vetAddress,
                vetAvailability,
                vetSalary
        );

        try {
            boolean isUpdated = empVetsModel.updateVet(vetDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Vet Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Vet").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Vet: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    public void btnVetDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String vetId = lblVetId.getText();
            try {
                boolean isDeleted = empVetsModel.deleteVet(vetId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Vet deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete Vet.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete Vet.").show();
            }
        }
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
            txtVetName.setText("");
            txtClinicName.setText("");
            comboSpecialization.setValue(null);
            txtVetLicenseNumber.setText("");
            txtContact.setText("");
            txtEmail.setText("");
            txtAddress.setText("");
            comboAvailability.setValue(null);
            txtSalary.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void btnVetResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnVetGenReport(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
        VetTM selectedRequest = tblVet.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            lblVetId.setText(selectedRequest.getVetId());
            txtVetName.setText(selectedRequest.getVetName());
            txtClinicName.setText(selectedRequest.getVetClinicName());
            comboSpecialization.setValue(selectedRequest.getVetSpecialization());
            txtVetLicenseNumber.setText(selectedRequest.getVetLicenseNum());
            txtContact.setText(selectedRequest.getVetContact());
            txtEmail.setText(selectedRequest.getVetEmail());
            txtAddress.setText(selectedRequest.getVetAddress());
            comboAvailability.setValue(selectedRequest.getVetAvailability());
            txtSalary.setText(String.valueOf(selectedRequest.getVetSalary()));

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
