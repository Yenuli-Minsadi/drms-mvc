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
import lk.ijse.dog_rescue_management_system.model.AdopterModel;
import lk.ijse.dog_rescue_management_system.view.tdm.AdopterTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdopterController implements Initializable {

    @FXML
    public Label lblAdopterId;
    @FXML
    public TextField txtAdopterName;
    @FXML
    public TextField txtAdopterContact;
    @FXML
    public TextField txtAdopterAddress;
    @FXML
    public TextField txtAdopterEmail;
    @FXML
    public ComboBox<String> comboAdopterHasPets;
    @FXML
    public DatePicker dateAdopterRegisterDate;
    @FXML
    public ComboBox<String> comboAdopterIncomeStatus;

    @FXML
    public TableView<AdopterTM> tblAdopter;
    @FXML
    public TableColumn<AdopterTM, String> colAdopId;
    @FXML
    public TableColumn<AdopterTM, String> colAdopName;
    @FXML
    public TableColumn<AdopterTM, String> colAdopContact;
    @FXML
    public TableColumn<AdopterTM, String> colAdopAddress;
    @FXML
    public TableColumn<AdopterTM, String> colAdopEmail;
    @FXML
    public TableColumn<AdopterTM, String> colAdopPets; // Changed to String
    @FXML
    public TableColumn<AdopterTM, LocalDate> colApplydate;
    @FXML
    public TableColumn<AdopterTM, String> colAdopInc;

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

    private final AdopterModel adopterModel = new AdopterModel();

    @FXML
    void btnAdopterSaveOnAction(ActionEvent event) {
        // Validate inputs
        if (!isInputValid()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all required fields").show();
            return;
        }

        String adopterId = lblAdopterId.getText();
        String adopterName = txtAdopterName.getText();
        String adopterContact = txtAdopterContact.getText();
        String adopterAddress = txtAdopterAddress.getText();
        String adopterEmail = txtAdopterEmail.getText();
        String adopterHasOtherPets = comboAdopterHasPets.getValue();
        LocalDate adopterApplyDate = dateAdopterRegisterDate.getValue();
        String adopterIncomeStatus = comboAdopterIncomeStatus.getValue();

        AdopterDto adopterDto = new AdopterDto(
                adopterId,
                adopterName,
                adopterContact,
                adopterAddress,
                adopterEmail,
                adopterHasOtherPets,
                adopterApplyDate,
                adopterIncomeStatus
        );

        try {
            boolean isSaved = adopterModel.saveAdopter(adopterDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Adopter Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Adopter").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Adopter: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnAdopterUpdateOnAction(ActionEvent actionEvent) {
        // Validate inputs
        if (!isInputValid()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all required fields").show();
            return;
        }

        String adopterId = lblAdopterId.getText();
        String adopterName = txtAdopterName.getText();
        String adopterContact = txtAdopterContact.getText();
        String adopterAddress = txtAdopterAddress.getText();
        String adopterEmail = txtAdopterEmail.getText();
        String adopterHasOtherPets = comboAdopterHasPets.getValue();
        LocalDate adopterApplyDate = dateAdopterRegisterDate.getValue();
        String adopterIncomeStatus = comboAdopterIncomeStatus.getValue();

        AdopterDto adopterDto = new AdopterDto(
                adopterId,
                adopterName,
                adopterContact,
                adopterAddress,
                adopterEmail,
                adopterHasOtherPets,
                adopterApplyDate,
                adopterIncomeStatus
        );

        try {
            boolean isUpdated = adopterModel.updateAdopter(adopterDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Adopter Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Adopter").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update Adopter: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnAdopterDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String adopterId = lblAdopterId.getText();
            try {
                boolean isDeleted = adopterModel.deleteAdopter(adopterId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Adopter deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Adopter.").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete Adopter: " + e.getMessage()).show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnAdopterResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    @FXML
    void btnAdopterSearchOnAction(ActionEvent actionEvent) {
        // Implement search functionality if needed
    }

    @FXML
    void btnAdopterGenerateReportOnAction(ActionEvent actionEvent) {
        // Implement report generation if needed
    }

    @FXML
    void onClickTable(MouseEvent mouseEvent) {
        AdopterTM selectedRequest = tblAdopter.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            lblAdopterId.setText(selectedRequest.getAdopterId());
            txtAdopterName.setText(selectedRequest.getAdopterName());
            txtAdopterContact.setText(selectedRequest.getAdopterContact());
            txtAdopterAddress.setText(selectedRequest.getAdopterAddress());
            txtAdopterEmail.setText(selectedRequest.getAdopterEmail());
            comboAdopterHasPets.setValue(selectedRequest.getAdopterHasOtherPets());
            dateAdopterRegisterDate.setValue(selectedRequest.getAdopterApplyDate());
            comboAdopterIncomeStatus.setValue(selectedRequest.getAdopterIncomeStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colAdopId.setCellValueFactory(new PropertyValueFactory<>("adopterId"));
        colAdopName.setCellValueFactory(new PropertyValueFactory<>("adopterName"));
        colAdopContact.setCellValueFactory(new PropertyValueFactory<>("adopterContact"));
        colAdopAddress.setCellValueFactory(new PropertyValueFactory<>("adopterAddress"));
        colAdopEmail.setCellValueFactory(new PropertyValueFactory<>("adopterEmail"));
        colAdopPets.setCellValueFactory(new PropertyValueFactory<>("adopterHasOtherPets"));
        colApplydate.setCellValueFactory(new PropertyValueFactory<>("adopterApplyDate"));
        colAdopInc.setCellValueFactory(new PropertyValueFactory<>("adopterIncomeStatus"));

        try {
            loadTableData();
            loadNextId();
            comboAdopterHasPets.getItems().addAll("Yes", "No");
            comboAdopterIncomeStatus.getItems().addAll("High", "Medium", "Low");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Initialization failed: " + e.getMessage()).show();
        }
    }

    private boolean isInputValid() {
        return !lblAdopterId.getText().isEmpty() &&
                !txtAdopterName.getText().isEmpty() &&
                !txtAdopterContact.getText().isEmpty() &&
                !txtAdopterAddress.getText().isEmpty() &&
                !txtAdopterEmail.getText().isEmpty() &&
                comboAdopterHasPets.getValue() != null &&
                dateAdopterRegisterDate.getValue() != null &&
                comboAdopterIncomeStatus.getValue() != null;
    }

    private void loadNextId() throws Exception {
        String nextId = adopterModel.getNextAdopterId();
        lblAdopterId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<AdopterDto> adopterDtoArrayList = adopterModel.getAllAdopters();
        ObservableList<AdopterTM> adopterTMS = FXCollections.observableArrayList();

        for (AdopterDto adopterDto : adopterDtoArrayList) {
            AdopterTM adopterTM = new AdopterTM(
                    adopterDto.getAdopterId(),
                    adopterDto.getAdopterName(),
                    adopterDto.getAdopterContact(),
                    adopterDto.getAdopterAddress(),
                    adopterDto.getAdopterEmail(),
                    adopterDto.getAdopterHasOtherPets(),
                    adopterDto.getAdopterApplyDate(),
                    adopterDto.getAdopterIncomeStatus()
            );
            adopterTMS.add(adopterTM);
        }
        tblAdopter.setItems(adopterTMS);
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtAdopterName.setText("");
            txtAdopterContact.setText("");
            txtAdopterAddress.setText("");
            txtAdopterEmail.setText("");
            comboAdopterHasPets.setValue(null);
            dateAdopterRegisterDate.setValue(null);
            comboAdopterIncomeStatus.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Reset failed: " + e.getMessage()).show();
        }
    }
}