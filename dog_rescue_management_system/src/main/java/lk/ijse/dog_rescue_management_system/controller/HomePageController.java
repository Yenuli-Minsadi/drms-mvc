package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import lk.ijse.dog_rescue_management_system.dto.DonorDto;
import lk.ijse.dog_rescue_management_system.dto.ExpenseDto;
import lk.ijse.dog_rescue_management_system.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {


    @FXML
    private Label lblTotScheduledAppt;

    @FXML
    private Label lblAdoptedDogs;

    @FXML
    private Label lblDogCount;

    DogRegisterModel dogRegisterModel = new DogRegisterModel();
    private final AdoptionModel adoptionProcessModel = new AdoptionModel();
    private final AppointmentModel appointmentModel = new AppointmentModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDogCount();
        loadAdoptedDogCount();
        loadScheduledAppointmentCount();

    }

    private void loadDogCount() {
        try {
            int count = dogRegisterModel.getDogCount();
            lblDogCount.setText(String.valueOf(count));
        } catch (SQLException | ClassNotFoundException e) {
            lblDogCount.setText("Error");
            e.printStackTrace();
        }
    }

    private void loadAdoptedDogCount() {
        try {
            int count = adoptionProcessModel.getAdoptedDogCount();
            lblAdoptedDogs.setText(String.valueOf(count));
        } catch (SQLException | ClassNotFoundException e) {
            lblAdoptedDogs.setText("Error");
            e.printStackTrace();
        }
    }

    private void loadScheduledAppointmentCount() {
        try {
            int count = appointmentModel.getScheduledAppointmentCount();
            lblTotScheduledAppt.setText(String.valueOf(count));
        } catch (SQLException | ClassNotFoundException e) {
            lblTotScheduledAppt.setText("Error");
            e.printStackTrace();
        }
    }




}
