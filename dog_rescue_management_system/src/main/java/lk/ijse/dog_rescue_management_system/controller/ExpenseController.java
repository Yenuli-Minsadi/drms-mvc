package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.ExpenseDto;
import lk.ijse.dog_rescue_management_system.dto.RequestCaseDto;
import lk.ijse.dog_rescue_management_system.model.ExpenseModel;
import lk.ijse.dog_rescue_management_system.view.tdm.DonorTM;
import lk.ijse.dog_rescue_management_system.view.tdm.ExpenseTM;
import lk.ijse.dog_rescue_management_system.view.tdm.RequestTM;
import lk.ijse.dog_rescue_management_system.view.tdm.VetTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class ExpenseController implements Initializable {

    public Label lblExpId;
    public TextField txtDogId;
    public TextField txtReceiptReference;
    public TextField txtPaymentMethod;
    public TextField txtExpenseDescription;
    public DatePicker dateExpDate;
    public TextField txtExpAmount;
    public ComboBox<String> comboExpCategory;
    public ComboBox<String> comboExpStatus;

    public TableView<ExpenseTM> tblExpense;
    public TableColumn<ExpenseTM, String> colExpId;
    public TableColumn<ExpenseTM, String> colDogId;
    public TableColumn<ExpenseTM, String> colRecRef;
    public TableColumn<ExpenseTM, String> colPayMeth;
    public TableColumn<ExpenseTM, String> colExpDesc;
    public TableColumn<ExpenseTM, Date> colExpDate;
    public TableColumn<ExpenseTM, Double> colExpAmnt;
    public TableColumn<ExpenseTM, String> colExpCategory;
    public TableColumn<ExpenseTM, Double> colExpStatus;


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

    private final ExpenseModel expenseModel = new ExpenseModel();

    @FXML
    void btnExpSaveOnAction(ActionEvent event) {
        String expenseId = lblExpId.getText();
        String dogId = txtDogId.getText();
        String receiptReference = txtReceiptReference.getText();
        String paymentMethod = txtPaymentMethod.getText();
        String expenseDescription = txtExpenseDescription.getText();
        LocalDate expenseDate = LocalDate.parse(dateExpDate.getValue().toString());
        Double expenseAmount = Double.valueOf(txtExpAmount.getText());
        String expenseCategory = comboExpCategory.getValue().toString();
        String expenseStatus = comboExpStatus.getValue().toString();

        ExpenseDto expenseDto = new ExpenseDto(
                expenseId,
                dogId,
                receiptReference,
                paymentMethod,
                expenseDescription,
                expenseDate,
                expenseAmount,
                expenseCategory,
                expenseStatus
        );

        try {
            boolean isSaved = expenseModel.saveExpense(expenseDto);
            if (isSaved){
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Expense Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Expense").show();
            }
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Failed to save Expense: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // table column and tm class properties link
        colExpId.setCellValueFactory(new PropertyValueFactory<>("expenseId"));
        colDogId.setCellValueFactory(new PropertyValueFactory<>("dogId"));
        colRecRef.setCellValueFactory(new PropertyValueFactory<>("receiptReference"));
        colPayMeth.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colExpDesc.setCellValueFactory(new PropertyValueFactory<>("expenseDescription"));
        colExpDate.setCellValueFactory(new PropertyValueFactory<>("expenseDate"));
        colExpAmnt.setCellValueFactory(new PropertyValueFactory<>("expenseAmount"));
        colExpCategory.setCellValueFactory(new PropertyValueFactory<>("expenseCategory"));
        colExpStatus.setCellValueFactory(new PropertyValueFactory<>("expenseStatus"));

        try {
            loadTableData();
            loadNextId();

            // Add options to donation type combo box
            comboExpCategory.getItems().addAll("Food", "Medical", "Shelter", "Other");
            comboExpStatus.getItems().addAll("Approved", "Pending", "Rejected");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = expenseModel.getNextExpenseId();
        lblExpId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ExpenseDto> expenseDtoArrayList = expenseModel.getAllExpenses();
        ObservableList<ExpenseTM> expenseTMS = FXCollections.observableArrayList();

        for (ExpenseDto expenseDto : expenseDtoArrayList){
            ExpenseTM expenseTM = new ExpenseTM(
                    expenseDto.getExpenseId(),
                    expenseDto.getDogId(),
                    expenseDto.getReceiptReference(),
                    expenseDto.getPaymentMethod(),
                    expenseDto.getExpenseDescription(),
                    expenseDto.getExpenseDate(),
                    expenseDto.getExpenseAmount(),
                    expenseDto.getExpenseCategory(),
                    expenseDto.getExpenseStatus()
            );
            expenseTMS.add(expenseTM);
        }
        tblExpense.setItems(expenseTMS);
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
            txtReceiptReference.setText("");
            txtPaymentMethod.setText("");
            txtExpenseDescription.setText("");
            dateExpDate.setValue(null);
            txtExpAmount.setText("");
            comboExpCategory.setValue(null);
            comboExpStatus.setValue(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void btnExpUpdateOnAction(ActionEvent actionEvent) {

        String expenseId = lblExpId.getText();
        String dogId = txtDogId.getText();
        String receiptReference = txtReceiptReference.getText();
        String paymentMethod = txtPaymentMethod.getText();
        String expenseDescription = txtExpenseDescription.getText();
        LocalDate expenseDate = LocalDate.parse(dateExpDate.getValue().toString());
        Double expenseAmount = Double.valueOf(txtExpAmount.getText());
        String expenseCategory = comboExpCategory.getValue().toString();
        String expenseStatus = comboExpStatus.getValue().toString();

        ExpenseDto expenseDto = new ExpenseDto(
                expenseId,
                dogId,
                receiptReference,
                paymentMethod,
                expenseDescription,
                expenseDate,
                expenseAmount,
                expenseCategory,
                expenseStatus
        );

        try {
            boolean isUpdated = expenseModel.updateExpense(expenseDto);
            if (isUpdated){
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Expense Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Expense").show();
            }
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Failed to update Expense: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }

    public void btnExpDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String expenseId = lblExpId.getText();
            try {
                boolean isDeleted = expenseModel.deleteExpense(expenseId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Expense deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete Expense.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete Expense.").show();
            }
        }
    }

    public void btnExpResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private double calculateTotalExpenseAmount() {
        double total = 0;
        for (ExpenseTM expense : tblExpense.getItems()) {
            total += expense.getExpenseAmount();
        }
        return total;
    }


    public void btnExpGenerateReportOnAction(ActionEvent actionEvent) {
        GenReportController genReportController = new GenReportController();
        genReportController.genExpReport(calculateTotalExpenseAmount());

    }



    public void btnExpSearchOnAction(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
        ExpenseTM selectedRequest = tblExpense.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            lblExpId.setText(selectedRequest.getExpenseId());
            txtDogId.setText(selectedRequest.getDogId());
            txtReceiptReference.setText(selectedRequest.getReceiptReference());
            txtPaymentMethod.setText(selectedRequest.getPaymentMethod());
            txtExpenseDescription.setText(selectedRequest.getExpenseDescription());
            dateExpDate.setValue(selectedRequest.getExpenseDate());
            txtExpAmount.setText(String.valueOf(selectedRequest.getExpenseAmount()));
            comboExpCategory.setValue(selectedRequest.getExpenseCategory());
            comboExpStatus.setValue(selectedRequest.getExpenseStatus());

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
