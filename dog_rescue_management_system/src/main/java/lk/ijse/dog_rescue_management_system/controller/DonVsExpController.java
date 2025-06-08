package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import lk.ijse.dog_rescue_management_system.dto.DonorDto;
import lk.ijse.dog_rescue_management_system.dto.ExpenseDto;
import lk.ijse.dog_rescue_management_system.model.DonorModel;
import lk.ijse.dog_rescue_management_system.model.ExpenseModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DonVsExpController implements Initializable {
    @FXML
    private PieChart donationExpensePieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadDonationExpensePieChart();
    }

    private void loadDonationExpensePieChart() {
        try {
            // Get total donations
            List<DonorDto> donors = new DonorModel().getAllDonors();
            double totalDonations = donors.stream()
                    .mapToDouble(DonorDto::getDonorAmount)
                    .sum();

            // Get total expenses
            List<ExpenseDto> expenses = new ExpenseModel().getAllExpenses();
            double totalExpenses = expenses.stream()
                    .mapToDouble(ExpenseDto::getExpenseAmount)
                    .sum();

            // Prepare PieChart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Total Donations", totalDonations),
                    new PieChart.Data("Total Expenses", totalExpenses)
            );

            donationExpensePieChart.setData(pieChartData);
            donationExpensePieChart.setTitle("Donations vs Expenses");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // You can show an Alert here
        }
    }
}
