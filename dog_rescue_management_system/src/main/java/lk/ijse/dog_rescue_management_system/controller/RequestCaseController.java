package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dog_rescue_management_system.dto.RequestCaseDto;
import lk.ijse.dog_rescue_management_system.model.RequestCaseModel;
import lk.ijse.dog_rescue_management_system.view.tdm.DogRegisterTM;
import lk.ijse.dog_rescue_management_system.view.tdm.RequestTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class RequestCaseController implements Initializable {
    public Label lblRequestId;
    public TextField txtRescuerId;
    public TextField txtLocation;
    public TextField txtReason;
    public ComboBox<String> comboCaseType;
    public ComboBox<String> comboUrgencylvl;
    public DatePicker dateRequestDate;
    public ComboBox<String> comboRequestStatus;
    public TextArea txtReqNotes;

    public TableView<RequestTM> tblCase;
    public TableColumn<RequestTM, String> colReqId;
    public TableColumn<RequestTM, String> colRescuerId;
    public TableColumn<RequestTM, String> colReqDate;
    public TableColumn<RequestTM, String> colReqStatus;
    public TableColumn<RequestTM, String> colLocation;
    public TableColumn<RequestTM, String> colReason;
    public TableColumn<RequestTM, String> colUrgencyLevel;
    public TableColumn<RequestTM, String> colReqNotes;
    public TableColumn<RequestTM, String> colCaseType;

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

    private final RequestCaseModel requestCaseModel = new RequestCaseModel();

    @FXML
    void btnCaseSaveOnAction(ActionEvent event) {
        String requestId = lblRequestId.getText();
        String rescuerId = txtRescuerId.getText();
        String location = txtLocation.getText();
        String reason = txtReason.getText();
        String caseType = comboCaseType.getValue();
        String urgencyLevel = comboUrgencylvl.getValue();
        LocalDate requestDate = dateRequestDate.getValue();
        String requestStatus = comboRequestStatus.getValue();
        String requestNotes = txtReqNotes.getText();

        RequestCaseDto requestCaseDto = new RequestCaseDto(
                requestId,
                rescuerId,
                location,
                reason,
                caseType,
                urgencyLevel,
                requestDate,
                requestStatus,
                requestNotes
        );

        try {
            boolean isSaved = requestCaseModel.saveGenCase(requestCaseDto);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "General Case saved successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save General Case.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to save General Case.").show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // table column and tm class properties link
        colReqId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        colRescuerId.setCellValueFactory(new PropertyValueFactory<>("rescuerId"));
        colReqDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        colReqStatus.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        colUrgencyLevel.setCellValueFactory(new PropertyValueFactory<>("urgencyLevel"));
        colReqNotes.setCellValueFactory(new PropertyValueFactory<>("requestNotes"));
        colCaseType.setCellValueFactory(new PropertyValueFactory<>("caseType"));

        try{
            loadTableData();
            loadNextId();
            comboCaseType.getItems().addAll("Emergency", "General");
            comboUrgencylvl.getItems().addAll("High", "Low", "Medium", "Critical");
            comboRequestStatus.getItems().addAll("New", "Pending", "In Progress", "Completed");
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = requestCaseModel.getNextRequestId();
        lblRequestId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<RequestCaseDto> requestCaseDtoArrayList = requestCaseModel.getAllRequest();
        ObservableList<RequestTM> requestTMS = FXCollections.observableArrayList();

        for (RequestCaseDto requestCaseDto : requestCaseDtoArrayList){
            RequestTM requestTM = new RequestTM(
                    requestCaseDto.getRequestId(),
                    requestCaseDto.getRescuerId(),
                    requestCaseDto.getRequestDate(),
                    requestCaseDto.getRequestStatus(),
                    requestCaseDto.getLocation(),
                    requestCaseDto.getReason(),
                    requestCaseDto.getUrgencyLevel(),
                    requestCaseDto.getRequestNote(),
                    requestCaseDto.getCaseType()
            );
            requestTMS.add(requestTM);
        }
        tblCase.setItems(requestTMS);
    }

    public void btnCaseUpdateOnAction(ActionEvent actionEvent) {

        String requestId = lblRequestId.getText();
        String rescuerId = txtRescuerId.getText();
        String location = txtLocation.getText();
        String reason = txtReason.getText();
        String caseType = comboCaseType.getValue();
        String urgencyLevel = comboUrgencylvl.getValue();
        LocalDate requestDate = dateRequestDate.getValue();
        String requestStatus = comboRequestStatus.getValue();
        String requestNotes = txtReqNotes.getText();

        RequestCaseDto requestCaseDto = new RequestCaseDto(
                requestId,
                rescuerId,
                location,
                reason,
                caseType,
                urgencyLevel,
                requestDate,
                requestStatus,
                requestNotes
        );

        try {
            boolean isUpdated = requestCaseModel.updateGenCase(requestCaseDto);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "General Case Updated successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update General Case.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to Update General Case.").show();
        }
    }

    public void btnCaseDeleteOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String requestId = lblRequestId.getText();
            try {
                boolean isDeleted = requestCaseModel.deleteCase(requestId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete customer.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer.").show();
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
            txtRescuerId.setText("");
            txtLocation.setText("");
            txtReason.setText("");
            comboCaseType.setValue(null);
            comboUrgencylvl.setValue(null);
            dateRequestDate.setValue(null);
            comboRequestStatus.setValue("");
            txtReqNotes.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void btnCaseResetOnAction(ActionEvent actionEvent) {
        // page set to initial look
        resetPage();
    }

    public void btnCaseGenerateReportOnAction(ActionEvent actionEvent) {
    }

    public void btnCaseSearchOnAction(ActionEvent actionEvent) {

    }

    public void onClickTable(MouseEvent mouseEvent) {
        RequestTM selectedRequest = tblCase.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            lblRequestId.setText(selectedRequest.getRequestId());
            txtRescuerId.setText(selectedRequest.getRescuerId());
            txtLocation.setText(selectedRequest.getLocation());
            txtReason.setText(selectedRequest.getReason());
            comboCaseType.setValue(selectedRequest.getCaseType());
            comboUrgencylvl.setValue(selectedRequest.getUrgencyLevel());
            dateRequestDate.setValue(selectedRequest.getRequestDate());
            comboRequestStatus.setValue(selectedRequest.getRequestStatus());
            txtReqNotes.setText(selectedRequest.getRequestNotes());

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
