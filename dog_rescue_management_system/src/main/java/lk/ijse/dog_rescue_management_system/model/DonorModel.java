package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.DonorDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DonorModel {
    public String getNextDonorId() throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        PreparedStatement pst = connection.prepareStatement(
//                "select donor_id from donor order by donor_id desc limit 1"
//        );
//
//        ResultSet resultSet = pst.executeQuery();
        ResultSet resultSet = CrudUtil.execute("select donor_id from donor order by donor_id desc limit 1");
        String tableString = "DN";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(2); // skip "DN"
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNUmber);

            return nextIdString;
        }
        return tableString + "001";
    }

    public  boolean saveDonor(DonorDto donorDto) throws Exception {
        return CrudUtil.execute(
                "insert into donor set  donor_id =?, donor_name =?, donor_contact =?, donor_email  =?, donor_address =?, donation_amount=?",
                donorDto.getDonorId(),
                donorDto.getDonorName(),
                donorDto.getDonorContact(),
                donorDto.getDonorEmail(),
                donorDto.getDonorAddress(),
                donorDto.getDonorAmount()
        );

    }

    public  boolean updateDonor(DonorDto donorDto) throws Exception {
        return CrudUtil.execute(
                "update donor set donor_name =?, donor_contact =?, donor_email  =?, donor_address =?, donation_amount=? where donor_id =?",
                donorDto.getDonorName(),
                donorDto.getDonorContact(),
                donorDto.getDonorEmail(),
                donorDto.getDonorAddress(),
                donorDto.getDonorAmount(),
                donorDto.getDonorId()
        );

    }

    public boolean deleteDonor(String donorId) throws Exception {
        return CrudUtil.execute(
                "delete from donor where donor_id=?",
                donorId
        );
    }


    public ArrayList<DonorDto> getAllDonors() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("select * from donor");

        ArrayList<DonorDto> donorDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            DonorDto donorDto = new DonorDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
            );
            donorDtoArrayList.add(donorDto);
        }
        return donorDtoArrayList;
    }
}

