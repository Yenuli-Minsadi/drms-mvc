package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.ExpenseDto;
import lk.ijse.dog_rescue_management_system.dto.VetDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenseModel {

    public String getNextExpenseId() throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pst = connection.prepareStatement(
                "select exp_id from expense order by exp_id desc limit 1"
        );

        ResultSet resultSet = pst.executeQuery();

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1); // skip "DNT"
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format("E%03d", nextIdNUmber);

            return nextIdString;
        }
        return "E001";

    }

    public  boolean saveExpense(ExpenseDto expenseDto) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO expense (exp_id, dog_id, receipt_reference, payment_method, exp_description, exp_date, exp_amount, exp_category, expense_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, expenseDto.getExpenseId());
        pst.setString(2, expenseDto.getDogId());
        pst.setString(3, expenseDto.getReceiptReference());
        pst.setString(4, expenseDto.getPaymentMethod());
        pst.setString(5, expenseDto.getExpenseDescription());
        pst.setString(6, String.valueOf(expenseDto.getExpenseDate()));
        pst.setString(7, String.valueOf(expenseDto.getExpenseAmount()));
        pst.setString(8, expenseDto.getExpenseCategory());
        pst.setString(9, expenseDto.getExpenseStatus());

        int i = pst.executeUpdate();
        boolean isSaved = i > 0;
        return isSaved;
    }

    public boolean updateExpense(ExpenseDto expenseDto) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute(
                "update expense set dog_id=?, receipt_reference=?, payment_method=?, exp_description = ?, exp_date = ?, exp_amount = ?, exp_category = ?, expense_status = ? where exp_id=?",
                expenseDto.getDogId(),
                expenseDto.getReceiptReference(),
                expenseDto.getPaymentMethod(),
                expenseDto.getExpenseDescription(),
                expenseDto.getExpenseDate(),
                expenseDto.getExpenseAmount(),
                expenseDto.getExpenseCategory(),
                expenseDto.getExpenseStatus(),
                expenseDto.getExpenseId()
        );
    }

    public boolean deleteExpense(String expenseId) throws Exception {
        return CrudUtil.execute(
                "delete from expense where exp_id=?",
                expenseId
        );
    }

    public ArrayList<ExpenseDto> getAllExpenses() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("select * from expense");

        ArrayList<ExpenseDto> expenseDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            ExpenseDto expenseDto = new ExpenseDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6).toLocalDate(), // Convert java.sql.Date to LocalDate
                    resultSet.getDouble(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
            );
            expenseDtoArrayList.add(expenseDto);
        }
        return expenseDtoArrayList;
    }
}
