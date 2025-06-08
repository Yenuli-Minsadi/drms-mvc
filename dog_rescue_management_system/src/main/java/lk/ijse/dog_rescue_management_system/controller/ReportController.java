package lk.ijse.dog_rescue_management_system.controller;


import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class ReportController {

    @FXML
    private AnchorPane homePane;
    // The pane in your FXML to add the pie chart into

    @FXML
    private TextArea txtDesc;

//    public void initialize() {
//        addPieChart();
//    }
//
//    private void addPieChart() {
//        ObservableList<PieChart.Data> pieChartData =
//                FXCollections.observableArrayList(
//                        new PieChart.Data("Adopted", 45),
//                        new PieChart.Data("In Shelter", 30),
//                        new PieChart.Data("Pending", 25)
//                );
//
//        PieChart pieChart = new PieChart(pieChartData);
//        pieChart.setTitle("Dog Rescue Status");
//        pieChart.setLayoutX(100); // Customize position
//        pieChart.setLayoutY(150);
//        pieChart.setPrefSize(400, 300); // Size of the chart
//
//        homePane.getChildren().add(pieChart);
//    }
}

