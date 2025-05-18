package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dog_rescue_management_system.dto.ShelterDto;
import lk.ijse.dog_rescue_management_system.dto.VetDto;
import lk.ijse.dog_rescue_management_system.model.ShelterModel;
import lk.ijse.dog_rescue_management_system.view.tdm.DonorTM;
import lk.ijse.dog_rescue_management_system.view.tdm.ShelterTM;
import lk.ijse.dog_rescue_management_system.view.tdm.VetTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShelterController implements Initializable {

    public Label lblShelterId;
    public TextField txtLocation;
    public TextField txtCapacity;
    public TextField txtCurrentOccupancy;
    public ComboBox<String> comboStatus;
    public TextArea txtResources;

    public TableView<ShelterTM> tblShelter;
    public TableColumn<ShelterTM, String> colShelterId;
    public TableColumn<ShelterTM, String> colShelterLocation;
    public TableColumn<ShelterTM, Integer> colShelterCapacity;
    public TableColumn<ShelterTM, Integer> colShelterOccupancy;
    public TableColumn<ShelterTM, String> colShelterStatus;
    public TableColumn<ShelterTM, Double> colShelterResources;


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

    private final ShelterModel shelterModel = new ShelterModel();

    @FXML
    void btnShelterSaveOnAction(ActionEvent event) {
        String shelterId = lblShelterId.getText();
        String shelterLocation = txtLocation.getText();
        int shelterCapacity = Integer.parseInt(txtCapacity.getText());
        int currentShelterOccupancy = Integer.parseInt(txtCurrentOccupancy.getText());
        String shelterStatus = comboStatus.getValue().toString();
        String shelterResources = txtResources.getText();


        ShelterDto shelterDto = new ShelterDto(
                shelterId,
                shelterLocation,
                shelterCapacity,
                currentShelterOccupancy,
                shelterStatus,
                shelterResources
        );

        try {
            boolean isSaved = shelterModel.saveShelter(shelterDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Shelter Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Shelter").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Shelter: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // table column and tm class properties link
        colShelterId.setCellValueFactory(new PropertyValueFactory<>("shelterId"));
        colShelterLocation.setCellValueFactory(new PropertyValueFactory<>("shelterLocation"));
        colShelterLocation.setCellValueFactory(new PropertyValueFactory<>("shelterCapacity"));
        colShelterCapacity.setCellValueFactory(new PropertyValueFactory<>("currentShelterOccupancy"));
        colShelterStatus.setCellValueFactory(new PropertyValueFactory<>("shelterStatus"));
        colShelterResources.setCellValueFactory(new PropertyValueFactory<>("shelterResources"));

        try {
            loadTableData();
            loadNextId();

            // Add options to donation type combo box
            comboStatus.getItems().addAll("Active", "Inactive");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = shelterModel.getNextShelterId();
        lblShelterId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ShelterDto> shelterDtoArrayList = shelterModel.getAllShelters();
        ObservableList<ShelterTM> shelterTMS = FXCollections.observableArrayList();

        for (ShelterDto shelterDto : shelterDtoArrayList){
            ShelterTM shelterTM = new ShelterTM(
                    shelterDto.getShelterId(),
                    shelterDto.getShelterLocation(),
                    shelterDto.getShelterCapacity(),
                    shelterDto.getCurrentShelterOccupancy(),
                    shelterDto.getShelterStatus(),
                    shelterDto.getShelterResources()
            );
            shelterTMS.add(shelterTM);
        }
        tblShelter.setItems(shelterTMS);
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
            txtLocation.setText("");
            txtCapacity.setText("");
            txtCurrentOccupancy.setText("");
            comboStatus.setValue(null);
            txtResources.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void btnShelterUpdateOnAction(ActionEvent actionEvent) {
        String shelterId = lblShelterId.getText();
        String shelterLocation = txtLocation.getText();
        int shelterCapacity = Integer.parseInt(txtCapacity.getText());
        int currentShelterOccupancy = Integer.parseInt(txtCurrentOccupancy.getText());
        String shelterStatus = comboStatus.getValue().toString();
        String shelterResources = txtResources.getText();

        ShelterDto shelterDto = new ShelterDto(
                shelterId,
                shelterLocation,
                shelterCapacity,
                currentShelterOccupancy,
                shelterStatus,
                shelterResources
        );

        try {
            boolean isUpdated = shelterModel.updateShelter(shelterDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Shelter Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Shelter").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update Shelter: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    public void btnShelterDeleteOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String shelterId = lblShelterId.getText();
            try {
                boolean isDeleted = shelterModel.deleteShelter(shelterId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Shelter deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete shelter.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete shelter.").show();
            }
        }
    }

    public void btnShleterResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnShelterGenerateReportOnAction(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
        ShelterTM selectedRequest = tblShelter.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            lblShelterId.setText(selectedRequest.getShelterId());
            txtLocation.setText(selectedRequest.getShelterLocation());
            txtCapacity.setText(String.valueOf(selectedRequest.getShelterCapacity()));
            txtCurrentOccupancy.setText(String.valueOf(selectedRequest.getCurrentShelterOccupancy()));
            comboStatus.setValue(selectedRequest.getShelterStatus());
            txtResources.setText(String.valueOf(selectedRequest.getShelterResources()));

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
