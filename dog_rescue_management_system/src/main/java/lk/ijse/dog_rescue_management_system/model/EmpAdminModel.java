package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.dto.AdminDto;
import lk.ijse.dog_rescue_management_system.dto.RescuerDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpAdminModel {

    public String getNextAdminId() throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        PreparedStatement pst = connection.prepareStatement(
//                "select admin_id from admins order by admin_id desc limit 1"
//        );
//
//        ResultSet resultSet = pst.executeQuery();
        ResultSet resultSet = CrudUtil.execute("select admin_id from admins order by admin_id desc limit 1");
        String tableString = "A";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1); // skip "A"
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNUmber);

            return nextIdString;
        }
        return tableString + "001";
    }

    public boolean saveAdmin(AdminDto adminDto) throws Exception {
        return CrudUtil.execute(
                "insert into admins set  admin_id =?, admin_name =?, admin_contact =?, admin_email  =?, admin_address =?",
                adminDto.getAdminId(),
                adminDto.getAdminName(),
                adminDto.getAdminContact(),
                adminDto.getAdminEmail(),
                adminDto.getAdminAddress()
        );
    }

    public boolean updateAdmin(AdminDto adminDto) throws Exception {
        return CrudUtil.execute("update admins set admin_name =?, admin_contact =?, admin_email  =?, admin_address =? where admin_id=?",

                adminDto.getAdminName(),
                adminDto.getAdminContact(),
                adminDto.getAdminEmail(),
                adminDto.getAdminAddress(),
                adminDto.getAdminId()
        );

    }

    public boolean deleteAdmin(String adminId) throws Exception {
        return CrudUtil.execute(
                "delete from admins where admin_id=?",
               adminId
        );
    }

    public ArrayList<AdminDto> getAllAdmins() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("select * from admins");

        ArrayList<AdminDto> adminDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            AdminDto adminDto = new AdminDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            adminDtoArrayList.add(adminDto);
        }
        return adminDtoArrayList;
    }
}
