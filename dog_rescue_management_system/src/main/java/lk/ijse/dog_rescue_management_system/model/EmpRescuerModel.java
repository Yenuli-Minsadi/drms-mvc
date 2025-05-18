package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.RescuerDto;
import lk.ijse.dog_rescue_management_system.dto.VetDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpRescuerModel {

    public String getNextRescuerId() throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        PreparedStatement pst = connection.prepareStatement(
//                "select rescuer_id from rescuer order by rescuer_id desc limit 1"
//        );
//
//        ResultSet resultSet = pst.executeQuery();

        ResultSet resultSet = CrudUtil.execute("select rescuer_id from rescuer order by rescuer_id desc limit 1");
        String tableString = "R";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1); // skip "R"
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNUmber);

            return nextIdString;
        }
        return tableString + "001";
    }

    public boolean saveRescuer(RescuerDto rescuerDto) throws Exception {
        return CrudUtil.execute(
                "insert into rescuer set  rescuer_id =?, rescuer_name =?, rescuer_specialty =?, rescuer_status  =?, rescuer_email =?, rescuer_address  = ?, rescuer_contact = ?, salary = ?",
                rescuerDto.getRescuerId(),
                rescuerDto.getRescuerName(),
                rescuerDto.getRescuerSpecialty(),
                rescuerDto.getRescuerStatus(),
                rescuerDto.getRescuerEmail(),
                rescuerDto.getRescuerAddress(),
                rescuerDto.getRescuerContact(),
                rescuerDto.getRescuerSalary()
        );
    }

    public boolean deleteRescuer(String rescuerId) throws Exception {
        return CrudUtil.execute(
                "delete from rescuer where rescuer_id=?",
                rescuerId
        );
    }

    public boolean updateRescuer(RescuerDto rescuerDto) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute(
                "update rescuer set rescuer_name =?, rescuer_specialty =?, rescuer_status  =?, rescuer_email =?, rescuer_address  = ?, rescuer_contact = ?, salary = ? where rescuer_id =?",
                rescuerDto.getRescuerName(),
                rescuerDto.getRescuerSpecialty(),
                rescuerDto.getRescuerStatus(),
                rescuerDto.getRescuerEmail(),
                rescuerDto.getRescuerAddress(),
                rescuerDto.getRescuerContact(),
                rescuerDto.getRescuerSalary(),
                rescuerDto.getRescuerId()
        );
    }

    public ArrayList<RescuerDto> getAllRescuers() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("select * from rescuer");

        ArrayList<RescuerDto> rescuerDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            RescuerDto rescuerDto = new RescuerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getDouble(8)
            );
            rescuerDtoArrayList.add(rescuerDto);
        }
        return rescuerDtoArrayList;
    }
}

