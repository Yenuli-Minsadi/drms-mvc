package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.DogDto;
import lk.ijse.dog_rescue_management_system.dto.RequestCaseDto;
import lk.ijse.dog_rescue_management_system.dto.ShelterDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DogRegisterModel {

    public String getNextDogId() throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        PreparedStatement pst = connection.prepareStatement(
//                "select dog_id from dog order by dog_id desc limit 1"
//        );
//
//        ResultSet resultSet = pst.executeQuery();
//
//
//        if (resultSet.next()) {
//            String lastId = resultSet.getString(1);
//            String lastIdNumberString = lastId.substring(1);
//            int lastIdNumber = Integer.parseInt(lastIdNumberString);
//            int nextIdNUmber = lastIdNumber + 1;
//
//            String nextIdString = String.format("D%03d", nextIdNUmber);
//
//            return nextIdString;
//        }
//
//        return "D001";

        ResultSet resultSet = CrudUtil.execute("select dog_id from dog order by dog_id desc limit 1");
        String tableString = "D";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);//skip D
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNUmber);
            return nextIdString;
        }
        return tableString + "001";

    }

    public  boolean saveDog(DogDto dogDto) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        String sql = "INSERT INTO dog (dog_id, rescue_request_id, dog_name, dog_breed, dog_birth_date, dog_color, dog_size, dog_gender, dog_status) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        PreparedStatement pst = connection.prepareStatement(sql);
//
//        pst.setString(1, dogDto.getDogId());
//        pst.setString(2, dogDto.getRescueRequestId());
//        pst.setString(3, dogDto.getDogName());
//        pst.setString(4, dogDto.getDogBreed());
//        pst.setString(5, String.valueOf(dogDto.getBirthDate()));
//        pst.setString(6, dogDto.getDogColor());
//        pst.setString(7, dogDto.getDogSize());
//        pst.setString(8, dogDto.getDogGender());
//        pst.setString(9, dogDto.getDogStatus());
//
//        int i = pst.executeUpdate();
//        boolean isSaved = i > 0;
//        return isSaved;

        return CrudUtil.execute(
                "insert into dog (dog_id, rescue_request_id, dog_name, dog_breed, dog_color, dog_size, dog_gender, dog_status, estimated_age) values (?,?,?,?,?,?,?,?,?)",
                dogDto.getDogId(),
                dogDto.getRescueRequestId(),
                dogDto.getDogName(),
                dogDto.getDogBreed(),
                dogDto.getDogColor(),
                dogDto.getDogSize(),
                dogDto.getDogGender(),
                dogDto.getDogStatus(),
                dogDto.getDogEstAge()
        );
    }

    public boolean deleteDog(String requestId) throws Exception {
        return CrudUtil.execute(
                "delete from dog where dog_id=?",
                requestId
        );
    }

    public boolean updateDog(DogDto dogDto) throws Exception {
        return CrudUtil.execute(
                "update dog set rescue_request_id =?, dog_name =?, dog_breed =?, dog_color =?, dog_size =?, dog_gender =?, dog_status =?, estimated_age =? where dog_id =?",
                dogDto.getRescueRequestId(),
                dogDto.getDogName(),
                dogDto.getDogBreed(),
                dogDto.getDogColor(),
                dogDto.getDogSize(),
                dogDto.getDogGender(),
                dogDto.getDogStatus(),
                dogDto.getDogEstAge(),
                dogDto.getDogId()
        );
    }

    public ArrayList<DogDto> getAllDogs() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM dog");
        ResultSet resultSet = pst.executeQuery();

        ArrayList<DogDto> dogRegisterDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            try {
                // Get column values by name instead of index to avoid column order issues
                String dogId = resultSet.getString("dog_id");
                String rescueRequestId = resultSet.getString("rescue_request_id");
                String dogName = resultSet.getString("dog_name");
                String dogBreed = resultSet.getString("dog_breed");
                String dogColor = resultSet.getString("dog_color");
                String dogSize = resultSet.getString("dog_size");
                String dogGender = resultSet.getString("dog_gender");
                String dogStatus = resultSet.getString("dog_status");
                String dogEstAge = resultSet.getString("estimated_age");

                DogDto dogDto = new DogDto(
                        dogId,
                        rescueRequestId,
                        dogName,
                        dogBreed,
                        dogColor,
                        dogSize,
                        dogGender,
                        dogStatus,
                        dogEstAge
                );
                dogRegisterDtoArrayList.add(dogDto);
            } catch (SQLException e) {
                System.out.println("Error processing row: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return dogRegisterDtoArrayList;
    }

}