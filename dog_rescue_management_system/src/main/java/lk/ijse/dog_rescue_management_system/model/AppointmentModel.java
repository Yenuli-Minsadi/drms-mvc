package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.AppointmentDto;
import lk.ijse.dog_rescue_management_system.dto.DonorDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class AppointmentModel {

    public String getNextAppointmentId() throws Exception {
        ResultSet resultSet = CrudUtil.execute("select appointment_id from appointments order by appointment_id desc limit 1");
        String tableString = "APT";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(3); // skip "APT"
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNumber);

            return nextIdString;
        }
        return tableString + "001";
    }

    public boolean saveAppointment(AppointmentDto appointmentDto) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO appointments (appointment_id, dog_id, vet_id, appointment_date, reason, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, appointmentDto.getAppointmentId());
        pst.setString(2, appointmentDto.getDogId());
        pst.setString(3, appointmentDto.getVetId());

        // Ensure appointment date is not null
        if (appointmentDto.getAppointmentDate() != null) {
            pst.setDate(4, Date.valueOf(appointmentDto.getAppointmentDate()));
        } else {
            throw new IllegalArgumentException("Appointment date cannot be null");
        }

        pst.setString(5, appointmentDto.getAppointmentReason());
        pst.setString(6, appointmentDto.getAppointmentStatus());

        int i = pst.executeUpdate();
        return i > 0;

//        return CrudUtil.execute(
//                "insert into appointments set  appointment_id =?, dog_id =?, vet_id =?, appointment_date =?, reason =?, status=?",
//                appointmentDto.getAppointmentId(),
//                appointmentDto.getDogId(),
//                appointmentDto.getVetId(),
//                appointmentDto.getAppointmentDate(),
//                appointmentDto.getAppointmentReason(),
//                appointmentDto.getAppointmentStatus()
//        );
    }

    public boolean deleteAppointment(String appointmentId) throws Exception {
        return CrudUtil.execute(
                "delete from appointments where appointment_id=?",
                appointmentId
        );
    }

    public  boolean updateAppointment(AppointmentDto appointmentDto) throws Exception {
        return CrudUtil.execute(
                "update appointments set dog_id =?, vet_id=?, appointment_date =?, reason=?, status=? where appointment_id =?",
                appointmentDto.getDogId(),
                appointmentDto.getVetId(),
                appointmentDto.getAppointmentDate(),
                appointmentDto.getAppointmentReason(),
                appointmentDto.getAppointmentStatus(),
                appointmentDto.getAppointmentId()
        );
    }

    public ArrayList<AppointmentDto> getAllAppointments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM appointments");

        ArrayList<AppointmentDto> appointmentDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            AppointmentDto appointmentDto = new AppointmentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            appointmentDtoArrayList.add(appointmentDto);
        }
        return appointmentDtoArrayList;
    }

    public int getScheduledAppointmentCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT COUNT(*) FROM appointments WHERE status = 'Scheduled'"
        );

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        return 0;
    }
}
