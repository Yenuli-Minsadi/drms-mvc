package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dog_rescue_management_system.dto.RescuerDto;
import lk.ijse.dog_rescue_management_system.dto.VetDto;
import lk.ijse.dog_rescue_management_system.model.EmpRescuerModel;
import lk.ijse.dog_rescue_management_system.model.EmpVetsModel;
import lk.ijse.dog_rescue_management_system.view.tdm.RescuerTM;
import lk.ijse.dog_rescue_management_system.view.tdm.VetTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmpRescuerController implements Initializable {

    public Label lblRescuerId;
    public TextField txtRescuerName;
    public ComboBox<String> comboRescuerSpecialty;
    public ComboBox<String> comboRescuerStatus;
    public TextField txtEmail;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtSalary;

    public TableView<RescuerTM> tblRescuer;
    public TableColumn<RescuerTM, String> colResId;
    public TableColumn<RescuerTM, String> colResName;
    public TableColumn<RescuerTM, String> colResSpecialty;
    public TableColumn<RescuerTM, String> colResStatus;
    public TableColumn<RescuerTM, String> colResEmail;
    public TableColumn<RescuerTM, String> colResAddress;
    public TableColumn<RescuerTM, String> colResContact;
    public TableColumn<RescuerTM, Double> colResSalary;

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

    private final EmpRescuerModel empRescuerModel = new EmpRescuerModel();

    @FXML
    void btnRescuerSaveOnAction(ActionEvent event) {
        String rescuerId = lblRescuerId.getText();
        String rescuerName = txtRescuerName.getText();
        String rescuerSpecialty = comboRescuerSpecialty.getValue();
        String rescuerStatus = comboRescuerStatus.getValue();
        String rescuerEmail = txtEmail.getText();
        String rescuerAddress = txtAddress.getText();
        String rescuerContact = txtContact.getText();
        Double rescuerSalary = Double.valueOf(txtSalary.getText());


        RescuerDto rescuerDto = new RescuerDto(
                rescuerId,
                rescuerName,
                rescuerSpecialty,
                rescuerStatus,
                rescuerEmail,
                rescuerAddress,
                rescuerContact,
                rescuerSalary
        );

        try {
            boolean isSaved = empRescuerModel.saveRescuer(rescuerDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Rescuer Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Rescuer").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Rescuer: " + e.getMessage()).show();
            e.printStackTrace();

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
            txtRescuerName.setText("");
            comboRescuerSpecialty.setValue(null);
            comboRescuerStatus.setValue(null);
            txtEmail.setText("");
            txtAddress.setText("");
            txtContact.setText("");
            txtSalary.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // table column and tm class properties link
        colResId.setCellValueFactory(new PropertyValueFactory<>("rescuerId"));
        colResName.setCellValueFactory(new PropertyValueFactory<>("rescuerName"));
        colResSpecialty.setCellValueFactory(new PropertyValueFactory<>("rescuerSpecialty"));
        colResStatus.setCellValueFactory(new PropertyValueFactory<>("rescuerStatus"));
        colResEmail.setCellValueFactory(new PropertyValueFactory<>("rescuerEmail"));
        colResAddress.setCellValueFactory(new PropertyValueFactory<>("rescuerAddress"));
        colResContact.setCellValueFactory(new PropertyValueFactory<>("rescuerContact"));
        colResSalary.setCellValueFactory(new PropertyValueFactory<>("rescuerSalary"));

        try {
            loadTableData();
            loadNextId();

            comboRescuerSpecialty.getItems().addAll(
                    "Animal Adoption",
                    "Animal Behavioral Therapy",
                    "Animal First Aid",
                    "Animal Handling",
                    "Animal Rehabilitation",
                    "Animal Rescue Logistics",
                    "Animal Rescue Operations",
                    "Animal Rights Advocacy",
                    "Animal Welfare Awareness",
                    "Dog Adoption",
                    "Dog Adoption Events",
                    "Dog Behavior Training",
                    "Dog Behavioral Therapy",
                    "Dog Care",
                    "Dog Feeding Programs",
                    "Dog Foster Care",
                    "Dog Foster Coordination",
                    "Dog Rehabilitation",
                    "Dog Shelter Management",
                    "Dog Training",
                    "Dog Transport Services",
                    "Dog Vaccination Programs",
                    "Emergency Dog Medical Aid",
                    "Emergency Dog Rescue",
                    "Emergency Rescue",
                    "Lost Dog Recovery",
                    "Medical Aid",
                    "Pet Adoption Services",
                    "Pet Therapy Programs",
                    "Rescue Coordination",
                    "Shelter Assistance",
                    "Stray Dog Management",
                    "Stray Dog Rescue",
                    "Street Dog Rescue",
                    "Veterinary Assistance",
                    "Veterinary Support");
            comboRescuerStatus.getItems().addAll("Active", "Inactive");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = empRescuerModel.getNextRescuerId();
        lblRescuerId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<RescuerDto> rescuerDtoArrayList = empRescuerModel.getAllRescuers();
        ObservableList<RescuerTM> rescuerTMS = FXCollections.observableArrayList();

        for (RescuerDto rescuerDto : rescuerDtoArrayList) {
            RescuerTM rescuerTM = new RescuerTM(
                    rescuerDto.getRescuerId(),
                    rescuerDto.getRescuerName(),
                    rescuerDto.getRescuerSpecialty(),
                    rescuerDto.getRescuerStatus(),
                    rescuerDto.getRescuerEmail(),
                    rescuerDto.getRescuerAddress(),
                    rescuerDto.getRescuerContact(),
                    rescuerDto.getRescuerSalary()
            );
            rescuerTMS.add(rescuerTM);
        }
        tblRescuer.setItems(rescuerTMS);
    }

    public void btnRescuerSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnRescuerUpdateOnAction(ActionEvent actionEvent) {
        String rescuerId = lblRescuerId.getText();
        String rescuerName = txtRescuerName.getText();
        String rescuerSpecialty = comboRescuerSpecialty.getValue();
        String rescuerStatus = comboRescuerStatus.getValue();
        String rescuerEmail = txtEmail.getText();
        String rescuerAddress = txtAddress.getText();
        String rescuerContact = txtContact.getText();
        Double rescuerSalary = Double.valueOf(txtSalary.getText());


        RescuerDto rescuerDto = new RescuerDto(
                rescuerId,
                rescuerName,
                rescuerSpecialty,
                rescuerStatus,
                rescuerEmail,
                rescuerAddress,
                rescuerContact,
                rescuerSalary
        );

        try {
            boolean isUpdated = empRescuerModel.updateRescuer(rescuerDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Rescuer Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update Rescuer").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to Update Rescuer: " + e.getMessage()).show();
            e.printStackTrace();

        }

    }

    public void btnRescuerDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String rescuerId = lblRescuerId.getText();
            try {
                boolean isDeleted = empRescuerModel.deleteRescuer(rescuerId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Rescuer deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete Rescuer.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete Rescuer.").show();
            }
        }
    }

    public void btnRescuerResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnRescuerGenReportOnAction(ActionEvent actionEvent) {

    }

    public void onClickTable(MouseEvent mouseEvent) {
        RescuerTM selectedRequest = tblRescuer.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            lblRescuerId.setText(selectedRequest.getRescuerId());
            txtRescuerName.setText(selectedRequest.getRescuerName());
            comboRescuerSpecialty.setValue(selectedRequest.getRescuerSpecialty());
            comboRescuerStatus.setValue(selectedRequest.getRescuerStatus());
            txtEmail.setText(selectedRequest.getRescuerEmail());
            txtAddress.setText(selectedRequest.getRescuerAddress());
            txtContact.setText(selectedRequest.getRescuerContact());
            txtSalary.setText(String.valueOf(selectedRequest.getRescuerSalary()));

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}