package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.dog_rescue_management_system.dto.AdopterDto;
import lk.ijse.dog_rescue_management_system.dto.DonorDto;
import lk.ijse.dog_rescue_management_system.model.AdopterModel;
import lk.ijse.dog_rescue_management_system.view.tdm.AdopterTM;
import lk.ijse.dog_rescue_management_system.view.tdm.DonorTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdopterController implements Initializable {

    public Label lblAdopterId;
    public TextField txtAdopterName;
    public TextField txtAdopterContact;
    public TextField txtAdopterAddress;
    public TextField txtAdopterEmail;
    public ComboBox<String> comboAdopterHasPets;
    public DatePicker dateAdopterRegisterDate;
    public ComboBox<String> comboAdopterIncomeStatus;

    public TableView<AdopterTM> tblAdopter;
    public TableColumn<AdopterTM, String> colAdopId;
    public TableColumn<AdopterTM, String> colAdopName;
    public TableColumn<AdopterTM, String> colAdopContact;
    public TableColumn<AdopterTM, String> colAdopAddress;
    public TableColumn<AdopterTM, String> colAdopEmail;
    public TableColumn<AdopterTM, Boolean> colAdopPets;
    public TableColumn<AdopterTM, String> colApplydate;
    public TableColumn<AdopterTM, String> colAdopInc;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSendEmail;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    private final AdopterModel adopterModel = new AdopterModel();

    @FXML
    void btnAdopterSaveOnAction(ActionEvent event) {
        String adopterId = lblAdopterId.getText();
        String adopterName = txtAdopterName.getText();
        String adopterContact = txtAdopterContact.getText();
        String adopterAddress = txtAdopterAddress.getText();
        String adopterEmail = txtAdopterEmail.getText();
        String adopterHasOtherPets = comboAdopterHasPets.getValue().toString();
        LocalDate adopterApplyDate = LocalDate.parse(dateAdopterRegisterDate.getValue().toString());
        String adopterIncomeStatus = comboAdopterIncomeStatus.getValue().toString();

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
            if (isSaved){
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Adopter Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Adopter").show();
            }
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Failed to save Adopter: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // table column and tm class properties link
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
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = adopterModel.getNextAdopterId();
        lblAdopterId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<AdopterDto> adopterDtoArrayList =adopterModel.getAllAdopters();
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

            // save button (id) -> enable
            btnSave.setDisable(false);

            // update, delete button (id) -> disable
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

//            lblRequestId.setText("");
            txtAdopterName.setText("");
            txtAdopterContact.setText("");
            txtAdopterAddress.setText("");
            txtAdopterEmail.setText("");
            comboAdopterHasPets.setValue(null);
            dateAdopterRegisterDate.setValue(null);
            comboAdopterIncomeStatus.setValue(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void btnAdopterSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnAdopterUpdateOnAction(ActionEvent actionEvent) {

        String adopterId = lblAdopterId.getText();
        String adopterName = txtAdopterName.getText();
        String adopterContact = txtAdopterContact.getText();
        String adopterAddress = txtAdopterAddress.getText();
        String adopterEmail = txtAdopterEmail.getText();
        String adopterHasOtherPets = comboAdopterHasPets.getValue().toString();
        LocalDate adopterApplyDate = LocalDate.parse(dateAdopterRegisterDate.getValue().toString());
        String adopterIncomeStatus = comboAdopterIncomeStatus.getValue().toString();

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
            if (isUpdated){
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Adopter Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Adopter").show();
            }
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Failed to update Adopter: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void btnAdopterDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String adopterId = lblAdopterId.getText();
            try {
                boolean isDeleted = adopterModel.deleteAdopter(adopterId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Adopter deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete Adopter.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete Adopter.").show();
            }
        }
    }

    public void btnAdopterResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }


    public void onClickTable(MouseEvent mouseEvent) {
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

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void btnAdopterSendEmailOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AdopterMail.fxml"));
        AnchorPane mailPane = fxmlLoader.load();

        Stage stage = new Stage();
        stage.setTitle("Send Email to Adopter");
        stage.setScene(new Scene(mailPane));
        stage.initModality(Modality.APPLICATION_MODAL); // Optional: block other windows until this is closed
        stage.show();
    }

}
