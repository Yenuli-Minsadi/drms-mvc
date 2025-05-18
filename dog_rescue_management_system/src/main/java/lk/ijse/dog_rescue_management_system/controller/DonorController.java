package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dog_rescue_management_system.dto.DonorDto;
import lk.ijse.dog_rescue_management_system.dto.RescuerDto;
import lk.ijse.dog_rescue_management_system.model.AppointmentModel;
import lk.ijse.dog_rescue_management_system.model.DonorModel;
import lk.ijse.dog_rescue_management_system.view.tdm.AdminTM;
import lk.ijse.dog_rescue_management_system.view.tdm.DonorTM;
import lk.ijse.dog_rescue_management_system.view.tdm.RescuerTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DonorController implements Initializable {

    public Label lblDonorId;
    public TextField txtDonorName;
    public TextField txtDonorContact;
    public TextField txtDonorEmail;
    public TextField txtDonorAddress;
    public TextField txtDonAmount;

    public TableView<DonorTM> tblDonor;
    public TableColumn<DonorTM, String> colDonorId;
    public TableColumn<DonorTM, String> colDonorName;
    public TableColumn<DonorTM, String> colDonorContact;
    public TableColumn<DonorTM, String> colDonorEmail;
    public TableColumn<DonorTM, String> colDonorAddress;
    public TableColumn<DonorTM, Double> colDonAmount;


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


    private final DonorModel donorModel = new DonorModel();

    @FXML
    void btnDonorSaveOnAction(ActionEvent event) {
        String donorId = lblDonorId.getText();
        String donorName = txtDonorName.getText();
        String donorContact = txtDonorContact.getText();
        String donorEmail = txtDonorEmail.getText();
        String donorAddress = txtDonorAddress.getText();
        Double donorAmount = Double.valueOf(txtDonAmount.getText());


        DonorDto donorDto = new DonorDto(
                donorId,
                donorName,
                donorContact,
                donorEmail,
                donorAddress,
                donorAmount
        );

        try {
            boolean isSaved = donorModel.saveDonor(donorDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Donor Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Donor").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Donor: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // table column and tm class properties link
        colDonorId.setCellValueFactory(new PropertyValueFactory<>("donorId"));
        colDonorName.setCellValueFactory(new PropertyValueFactory<>("donorName"));
        colDonorContact.setCellValueFactory(new PropertyValueFactory<>("donorContact"));
        colDonorEmail.setCellValueFactory(new PropertyValueFactory<>("donorEmail"));
        colDonorAddress.setCellValueFactory(new PropertyValueFactory<>("donorAddress"));
        colDonAmount.setCellValueFactory(new PropertyValueFactory<>("donorAmount"));

        try {
            loadTableData();
            loadNextId();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = donorModel.getNextDonorId();
        lblDonorId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<DonorDto> donorDtoArrayList = donorModel.getAllDonors();
        ObservableList<DonorTM> donorTMS = FXCollections.observableArrayList();

        for (DonorDto donorDto : donorDtoArrayList) {
            DonorTM donorTM = new DonorTM(
                    donorDto.getDonorId(),
                    donorDto.getDonorName(),
                    donorDto.getDonorContact(),
                    donorDto.getDonorEmail(),
                    donorDto.getDonorAddress(),
                    donorDto.getDonorAmount()
            );
            donorTMS.add(donorTM);
        }
        tblDonor.setItems(donorTMS);
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
            txtDonorName.setText("");
            txtDonorContact.setText("");
            txtDonorEmail.setText("");
            txtDonorAddress.setText("");
            txtDonAmount.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void btnDonorSearchOnAction(ActionEvent actionEvent) {
    }


    public void btnDonorUpdateOnAction(ActionEvent actionEvent) {

        String donorId = lblDonorId.getText();
        String donorName = txtDonorName.getText();
        String donorContact = txtDonorContact.getText();
        String donorEmail = txtDonorEmail.getText();
        String donorAddress = txtDonorAddress.getText();
        Double donorAmount = Double.valueOf(txtDonAmount.getText());


        DonorDto donorDto = new DonorDto(
                donorId,
                donorName,
                donorContact,
                donorEmail,
                donorAddress,
                donorAmount
        );

        try {
            boolean isUpdated = donorModel.updateDonor(donorDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Donor Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Donor").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update Donor: " + e.getMessage()).show();
            e.printStackTrace();

        }

    }

    public void btnDonorDeleteOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String donorId = lblDonorId.getText();
            try {
                boolean isDeleted = donorModel.deleteDonor(donorId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Donor deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete Donor.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete Donor.").show();
            }
        }
    }

    public void btnDonorResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnDonorGenerateReportOnAction(ActionEvent actionEvent) {
    }


    public void onClickTable(MouseEvent mouseEvent) {
        DonorTM selectedRequest = tblDonor.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            lblDonorId.setText(selectedRequest.getDonorId());
            txtDonorName.setText(selectedRequest.getDonorName());
            txtDonorContact.setText(selectedRequest.getDonorContact());
            txtDonorEmail.setText(selectedRequest.getDonorEmail());
            txtDonorAddress.setText(selectedRequest.getDonorAddress());
            txtDonAmount.setText(String.valueOf(selectedRequest.getDonorAmount()));

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
