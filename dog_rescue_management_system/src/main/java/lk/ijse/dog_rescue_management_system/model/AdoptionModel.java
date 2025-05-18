package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.AdoptionProcessDto;
import lk.ijse.dog_rescue_management_system.dto.DonorDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdoptionModel {

    public String getNextAdoptionId() throws Exception {
        ResultSet resultSet = CrudUtil.execute("select adopt_process_id from adoption_process order by adopt_process_id desc limit 1");
        String tableString = "AP";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(2); // skip "AP"
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNUmber);

            return nextIdString;
        }
        return tableString + "001";

    }

    public  boolean saveAdoption(AdoptionProcessDto adoptionProcessDto) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO adoption_process (adopt_process_id, dog_id, adopter_id, application_date, approval_date, adoption_date , adoption_status , adoption_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, adoptionProcessDto.getAdoptProcessId());
        pst.setString(2, adoptionProcessDto.getDogId());
        pst.setString(3, adoptionProcessDto.getAdopterId());
        pst.setString(4, String.valueOf(adoptionProcessDto.getAdopterApplyDate()));
        pst.setString(5, String.valueOf(adoptionProcessDto.getAdoptApprovalDate()));
        pst.setString(6, String.valueOf(adoptionProcessDto.getAdoptionDate()));
        pst.setString(7, adoptionProcessDto.getAdoptionStatus());
        pst.setString(8, adoptionProcessDto.getAdoptionType());

        int i = pst.executeUpdate();
        boolean isSaved = i > 0;
        return isSaved;
    }

    public  boolean updateAdoption(AdoptionProcessDto adoptionProcessDto) throws Exception {
        return CrudUtil.execute(
                "update adoption_process set dog_id =?, adopter_id =?, application_date  =?, approval_date =?, adoption_date=?, adoption_status=?, adoption_type=? where adopt_process_id =?",
                adoptionProcessDto.getDogId(),
                adoptionProcessDto.getAdopterId(),
                adoptionProcessDto.getAdopterApplyDate(),
                adoptionProcessDto.getAdoptApprovalDate(),
                adoptionProcessDto.getAdoptionDate(),
                adoptionProcessDto.getAdoptionStatus(),
                adoptionProcessDto.getAdoptionType(),
                adoptionProcessDto.getAdoptProcessId()
        );

    }

    public boolean deleteAdoption(String adoptionId) throws Exception {
        return CrudUtil.execute(
                "delete from adoption_process where adopt_process_id=?",
                adoptionId
        );
    }

    public ArrayList<AdoptionProcessDto> getAllAdoptions() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("select * from adoption_process");

        ArrayList<AdoptionProcessDto> adoptionProcessDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            AdoptionProcessDto adoptionProcessDto = new AdoptionProcessDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getDate(6).toLocalDate(),
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
            adoptionProcessDtoArrayList.add(adoptionProcessDto);
        }
        return adoptionProcessDtoArrayList;
    }
}
