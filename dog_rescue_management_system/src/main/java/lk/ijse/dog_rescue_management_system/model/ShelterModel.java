package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.ShelterDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShelterModel {

    public String getNextShelterId() throws Exception {
        ResultSet resultSet = CrudUtil.execute("select shelter_id from shelter order by shelter_id desc limit 1");
        String tableString = "S";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1); // skip "S"
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNUmber);

            return nextIdString;
        }
        return tableString + "001";
    }

    public  boolean saveShelter(ShelterDto shelterDto) throws Exception {
        return CrudUtil.execute(
                "insert into shelter set  shelter_id=?, shelter_location=?, shelter_capacity=?, current_shelter_occupancy=?, status=?, resources=?",
                shelterDto.getShelterId(),
                shelterDto.getShelterLocation(),
                shelterDto.getShelterCapacity(),
                shelterDto.getCurrentShelterOccupancy(),
                shelterDto.getShelterStatus(),
                shelterDto.getShelterResources()
        );
    }

    public  boolean updateShelter(ShelterDto shelterDto) throws Exception {
        return CrudUtil.execute(
                "update shelter set shelter_location=?, shelter_capacity=?, current_shelter_occupancy=?, status=?, resources=? where shelter_id=?",
                shelterDto.getShelterLocation(),
                shelterDto.getShelterCapacity(),
                shelterDto.getCurrentShelterOccupancy(),
                shelterDto.getShelterStatus(),
                shelterDto.getShelterResources(),
                shelterDto.getShelterId()
        );
    }

    public boolean deleteShelter(String shelterId) throws Exception {
        return CrudUtil.execute(
                "delete from shelter where shelter_id=?",
                shelterId
        );
    }

    public ArrayList<ShelterDto> getAllShelters() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("select * from shelter");

        ArrayList<ShelterDto> shelterDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            ShelterDto shelterDto = new ShelterDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            shelterDtoArrayList.add(shelterDto);
        }
        return shelterDtoArrayList;
    }

}

