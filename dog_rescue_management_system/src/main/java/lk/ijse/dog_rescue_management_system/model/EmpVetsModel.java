package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.RequestCaseDto;
import lk.ijse.dog_rescue_management_system.dto.VetDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpVetsModel {

    public String getNextVetId() throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        PreparedStatement pst = connection.prepareStatement(
//                "select vet_id from vet order by vet_id desc limit 1"
//        );
//
//        ResultSet resultSet = pst.executeQuery();

        ResultSet resultSet = CrudUtil.execute("select vet_id from vet order by vet_id desc limit 1");
        String tableString = "V";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1); // skip "V"
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNUmber);

            return nextIdString;
        }
        return tableString + "001";
    }

    public boolean saveVet(VetDto vetDto) throws Exception {
        return CrudUtil.execute(
                "update vet set vet_id=?, vet_name=?, clinic_name=?, specialization =?, vet_license_num=?, vet_contact = ?, vet_email = ?, vet_address = ?, availability = ?, salary = ?",
                vetDto.getVetId(),
                vetDto.getVetName(),
                vetDto.getVetClinicName(),
                vetDto.getVetSpecialization(),
                vetDto.getVetLicenseNum(),
                vetDto.getVetContact(),
                vetDto.getVetEmail(),
                vetDto.getVetAddress(),
                vetDto.getVetAvailability(),
                vetDto.getVetSalary()
        );
    }

    public boolean deleteVet(String vetId) throws Exception {
        return CrudUtil.execute(
                "delete from vet where vet_id=?",
                vetId
        );
    }

    public boolean updateVet(VetDto vetDto) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute(
                "update vet set vet_name=?, clinic_name=?, specialization =?, vet_license_num=?, vet_contact = ?, vet_email = ?, vet_address = ?, availability = ?, salary = ? where vet_id =?",
                vetDto.getVetName(),
                vetDto.getVetClinicName(),
                vetDto.getVetSpecialization(),
                vetDto.getVetLicenseNum(),
                vetDto.getVetContact(),
                vetDto.getVetEmail(),
                vetDto.getVetAddress(),
                vetDto.getVetAvailability(),
                vetDto.getVetSalary(),
                vetDto.getVetId()
        );
    }

    public ArrayList<VetDto> getAllVets() throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pst = connection.prepareStatement("SELECT * FROM vet");
//        ResultSet resultSet = pst.executeQuery();
        ResultSet resultSet = CrudUtil.execute("select * from vet");

        ArrayList<VetDto> vetDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            VetDto vetDto = new VetDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getDouble(10)
            );
            vetDtoArrayList.add(vetDto);
        }
        return vetDtoArrayList;
        }
}
