package lk.ijse.dog_rescue_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.dog_rescue_management_system.dto.DogDto;
import lk.ijse.dog_rescue_management_system.dto.RequestCaseDto;
import lk.ijse.dog_rescue_management_system.dto.ShelterDto;
import lk.ijse.dog_rescue_management_system.model.DogRegisterModel;
import lk.ijse.dog_rescue_management_system.view.tdm.DogRegisterTM;
import lk.ijse.dog_rescue_management_system.view.tdm.RequestTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DogRegisterController implements Initializable {

    public Label lblDogId;
    public TextField txtReqId;
    public TextField txtDogName;
    public TextField txtDogBreed;
    public DatePicker dateDogBirthday;
    public TextField txtDogColor;
    public ComboBox<String> comboDogSize;
    public ComboBox<String> comboDogGender;
    public ComboBox<String> comboDogStatus;

    public TableView<DogRegisterTM> tblDogReg;
    public TableColumn<DogRegisterTM, String> colDogId;
    public TableColumn<DogRegisterTM, String> colRescueRequestId;
    public TableColumn<DogRegisterTM, String> colDogName;
    public TableColumn<DogRegisterTM, String> colDogBreed;
    public TableColumn<DogRegisterTM, String> colDogBirthdate;
    public TableColumn<DogRegisterTM, String> colDogColor;
    public TableColumn<DogRegisterTM, String> colDogSize;
    public TableColumn<DogRegisterTM, String> colDogGender;
    public TableColumn<DogRegisterTM, String> colDogStatus;


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

    private final DogRegisterModel dogRegisterModel = new DogRegisterModel();

    @FXML
    void btnDogSaveOnAction(ActionEvent event) {
        String dogId = lblDogId.getText();
        String rescueRequestId = txtReqId.getText();
        String dogName = txtDogName.getText();
        String dogBreed = txtDogBreed.getText();
        LocalDate birthDate = LocalDate.parse(dateDogBirthday.getValue().toString());
        String dogColor = txtDogColor.getText();
        String dogSize = comboDogSize.getValue().toString();
        String dogGender = comboDogGender.getValue().toString();
        String dogStatus = comboDogStatus.getValue().toString();

        DogDto dogDto = new DogDto (
                dogId,
                rescueRequestId,
                dogName,
                dogBreed,
                birthDate,
                dogColor,
                dogSize,
                dogGender,
                dogStatus
        );

        try {
            boolean isSaved = dogRegisterModel.saveDog(dogDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Dog Saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Dog").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Dog: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colDogId.setCellValueFactory(new PropertyValueFactory<>("dogId"));
        colRescueRequestId.setCellValueFactory(new PropertyValueFactory<>("rescueRequestId"));
        colDogName.setCellValueFactory(new PropertyValueFactory<>("dogName"));
        colDogBreed.setCellValueFactory(new PropertyValueFactory<>("dogBreed"));
        colDogBirthdate.setCellValueFactory(new PropertyValueFactory<>("dogBirthdate"));
        colDogColor.setCellValueFactory(new PropertyValueFactory<>("dogColor"));
        colDogSize.setCellValueFactory(new PropertyValueFactory<>("dogSize"));
        colDogGender.setCellValueFactory(new PropertyValueFactory<>("dogGender"));
        colDogStatus.setCellValueFactory(new PropertyValueFactory<>("dogStatus"));

        try {
            loadTableData();
            loadNextId();

            comboDogSize.getItems().addAll("Large", "Medium", "Small");
            comboDogGender.getItems().addAll("Male", "Female");
            comboDogStatus.getItems().addAll("Rescued", "Under Treatment", "Adopted", "Available for Adoption");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = dogRegisterModel.getNextDogId();
        lblDogId.setText(nextId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<DogDto> dogRegisterDtoArrayList = dogRegisterModel.getAllDogs();
        ObservableList<DogRegisterTM> dogRegisterTMS = FXCollections.observableArrayList();

        for (DogDto dogDto : dogRegisterDtoArrayList){
            DogRegisterTM dogRegisterTM = new DogRegisterTM(
                    dogDto.getDogId(),
                    dogDto.getRescueRequestId(),
                    dogDto.getDogName(),
                    dogDto.getDogBreed(),
                    dogDto.getBirthDate(),
                    dogDto.getDogColor(),
                    dogDto.getDogSize(),
                    dogDto.getDogGender(),
                    dogDto.getDogStatus()
            );
            dogRegisterTMS.add(dogRegisterTM);
        }
        tblDogReg.setItems(dogRegisterTMS);
    }

    public void btnDogUpdateOnAction(ActionEvent actionEvent) {
        String dogId = lblDogId.getText();
        String rescueRequestId = txtReqId.getText();
        String dogName = txtDogName.getText();
        String dogBreed = txtDogBreed.getText();
        LocalDate birthDate = LocalDate.parse(dateDogBirthday.getValue().toString());
        String dogColor = txtDogColor.getText();
        String dogSize = comboDogSize.getValue().toString();
        String dogGender = comboDogGender.getValue().toString();
        String dogStatus = comboDogStatus.getValue().toString();

        DogDto dogDto = new DogDto (
                dogId,
                rescueRequestId,
                dogName,
                dogBreed,
                birthDate,
                dogColor,
                dogSize,
                dogGender,
                dogStatus
        );

        try {
            boolean isUpdated = dogRegisterModel.updateDog(dogDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Dog Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Dog").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update Dog: " + e.getMessage()).show();
            e.printStackTrace();

        }
    }

    public void btnDogDeleteOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            // user with agree to delete data
            String dogId = lblDogId.getText();
            try {
                boolean isDeleted = dogRegisterModel.deleteDog(dogId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Dog deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete dog.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete dog.").show();
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

            txtReqId.setText("");
            txtDogName.setText("");
            txtDogBreed.setText("");
            dateDogBirthday.setValue(null);
            txtDogColor.setText(null);
            comboDogSize.setValue(null);
            comboDogGender.setValue(null);
            comboDogStatus.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void btnDogResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnDogGenerateReportOnAction(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
        DogRegisterTM selectedDog = tblDogReg.getSelectionModel().getSelectedItem();

        if (selectedDog != null) {
            lblDogId.setText(selectedDog.getDogId());
            txtReqId.setText(selectedDog.getRescueRequestId());
            txtDogName.setText(selectedDog.getDogName());
            txtDogBreed.setText(selectedDog.getDogBreed());
            dateDogBirthday.setValue(selectedDog.getBirthDate());
            txtDogColor.setText(selectedDog.getDogColor());
            comboDogSize.setValue(selectedDog.getDogSize());
            comboDogGender.setValue(selectedDog.getDogGender());
            comboDogStatus.setValue(selectedDog.getDogStatus());

            // save button disable
            btnSave.setDisable(true);

            // update, delete button enable
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}