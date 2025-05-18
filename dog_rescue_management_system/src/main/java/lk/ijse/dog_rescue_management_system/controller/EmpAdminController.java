package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dog_rescue_management_system.dto.AdminDto;
import lk.ijse.dog_rescue_management_system.dto.RescuerDto;
import lk.ijse.dog_rescue_management_system.dto.VetDto;
import lk.ijse.dog_rescue_management_system.model.EmpAdminModel;
import lk.ijse.dog_rescue_management_system.model.EmpVetsModel;
import lk.ijse.dog_rescue_management_system.view.tdm.AdminTM;
import lk.ijse.dog_rescue_management_system.view.tdm.RescuerTM;
import lk.ijse.dog_rescue_management_system.view.tdm.VetTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmpAdminController implements Initializable {

    public Label lblAdminId;
    public TextField txtAdminName;
    public TextField txtAdminContact;
    public TextField txtAdminEmail;
    public TextField txtAdminAddress;
//    public DatePicker dateCreatedAt;

    public TableView<AdminTM> tblAdmin;
    public TableColumn<AdminTM, String> colAdminId;
    public TableColumn<AdminTM, String> colAdminName;
    public TableColumn<AdminTM, String> colAdminContact;
    public TableColumn<AdminTM, String> colAdminEmail;
    public TableColumn<AdminTM, String> colAdminAddress;

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

    private final EmpAdminModel empAdminModel = new EmpAdminModel();

    @FXML
    void btnAdminSaveOnAction(ActionEvent event) {
        String adminId = lblAdminId.getText();
        String adminName = txtAdminName.getText();
        String adminContact = txtAdminContact.getText();
        String adminEmail = txtAdminEmail.getText();
        String adminAddress = txtAdminAddress.getText();

        AdminDto adminDto = new AdminDto(
                adminId,
                adminName,
                adminContact,
                adminEmail,
                adminAddress
        );

        try {
            boolean isSaved = empAdminModel.saveAdmin(adminDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Admin Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Admin").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Admin: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // table column and tm class properties link
        colAdminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));
        colAdminName.setCellValueFactory(new PropertyValueFactory<>("adminName"));
        colAdminContact.setCellValueFactory(new PropertyValueFactory<>("adminContact"));
        colAdminEmail.setCellValueFactory(new PropertyValueFactory<>("adminEmail"));
        colAdminAddress.setCellValueFactory(new PropertyValueFactory<>("adminAddress"));

        try {
            loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = empAdminModel.getNextAdminId();
        lblAdminId.setText(nextId);
    }


    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<AdminDto> adminDtoArrayList = empAdminModel.getAllAdmins();
        ObservableList<AdminTM> adminTMS = FXCollections.observableArrayList();

        for (AdminDto adminDto : adminDtoArrayList) {
            AdminTM adminTM = new AdminTM(
                    adminDto.getAdminId(),
                    adminDto.getAdminName(),
                    adminDto.getAdminContact(),
                    adminDto.getAdminEmail(),
                    adminDto.getAdminAddress()
            );
            adminTMS.add(adminTM);
        }
        tblAdmin.setItems(adminTMS);
    }

    public void btnAdminSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnAdminUpdateOnAction(ActionEvent actionEvent) {
        String adminId = lblAdminId.getText();
        String adminName = txtAdminName.getText();
        String adminContact = txtAdminContact.getText();
        String adminEmail = txtAdminEmail.getText();
        String adminAddress = txtAdminAddress.getText();

        AdminDto adminDto = new AdminDto(
                adminId,
                adminName,
                adminContact,
                adminEmail,
                adminAddress
        );

        try {
            boolean isUpdated = empAdminModel.updateAdmin(adminDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Admin Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Admin").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update Admin: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    public void btnAdminDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String adminId = lblAdminId.getText();
            try {
                boolean isDeleted = empAdminModel.deleteAdmin(adminId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Admin deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete Admin.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete Admin.").show();
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
            txtAdminName.setText("");
            txtAdminContact.setText("");
            txtAdminEmail.setText("");
            txtAdminAddress.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }
    public void btnAdminResetOnAction(ActionEvent actionEvent) {
            resetPage();
    }

    public void btnAdminGenReportOnAction(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
        AdminTM selectedRequest = tblAdmin.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            lblAdminId.setText(selectedRequest.getAdminId());
            txtAdminName.setText(selectedRequest.getAdminName());
            txtAdminContact.setText(selectedRequest.getAdminContact());
            txtAdminEmail.setText(selectedRequest.getAdminEmail());
            txtAdminAddress.setText(selectedRequest.getAdminAddress());


            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
