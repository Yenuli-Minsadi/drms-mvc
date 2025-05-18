package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.RequestCaseDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RequestCaseModel {

    public String getNextRequestId() throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        PreparedStatement pst = connection.prepareStatement(
//                "select request_id from rescue_request order by request_id desc limit 1"
//        );
//
//        ResultSet resultSet = pst.executeQuery();

        ResultSet resultSet = CrudUtil.execute("select request_id from rescue_request order by request_id desc limit 1");
        String tableString = "RQ";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(2);//skip RQ
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNUmber);
            return nextIdString;
        }
        return tableString + "001";
    }

    public boolean saveGenCase(RequestCaseDto requestCaseDto) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        String sql = "INSERT INTO rescue_request (request_id, rescuer_id, location, reason, " +
//                "case_type, urgency_level, request_date, request_status, request_notes) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        PreparedStatement pst = connection.prepareStatement(sql);
//
//        // set values for ? marks
//        pst.setString(1, requestCaseDto.getRequestId());
//        pst.setString(2, requestCaseDto.getRescuerId());
//        pst.setString(3, requestCaseDto.getLocation());
//        pst.setString(4, requestCaseDto.getReason());
//        pst.setString(5, requestCaseDto.getCaseType());
//        pst.setString(6, requestCaseDto.getUrgencyLevel());
//        pst.setString(7, String.valueOf(requestCaseDto.getRequestDate()));
//        pst.setString(8, requestCaseDto.getRequestStatus());
//        pst.setString(9, requestCaseDto.getRequestNote());
//
//        int i = pst.executeUpdate();
//        boolean isSaved = i > 0;
//        return isSaved;

        return CrudUtil.execute(
                "insert into rescue_request (request_id, rescuer_id, location, reason, case_type, urgency_level, request_date, request_status, request_notes) values (?,?,?,?,?,?,?,?,?)",
                requestCaseDto.getRequestId(),
                requestCaseDto.getRescuerId(),
                requestCaseDto.getLocation(),
                requestCaseDto.getReason(),
                requestCaseDto.getCaseType(),
                requestCaseDto.getUrgencyLevel(),
                requestCaseDto.getRequestDate(),
                requestCaseDto.getRequestStatus(),
                requestCaseDto.getRequestNote()
                );
    }


    public boolean deleteCase(String requestId) throws Exception {
        return CrudUtil.execute(
                "delete from rescue_request where request_id=?",
                requestId
        );
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        String sql = "DELETE FROM rescue_request WHERE request_id = ?";
//
//        PreparedStatement pst = connection.prepareStatement(sql);
//        pst.setString(1, requestId);
//
//        int rowsAffected = pst.executeUpdate();
//        return rowsAffected > 0;
    }

    public boolean updateGenCase(RequestCaseDto requestCaseDto) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute(
                "update rescue_request set rescuer_id =?, request_date =?, request_status =?, location =?, reason = ?, urgency_level = ?, request_notes = ?, case_type = ? where request_id =?",
                requestCaseDto.getRescuerId(),
                requestCaseDto.getRequestDate(),
                requestCaseDto.getRequestStatus(),
                requestCaseDto.getLocation(),
                requestCaseDto.getReason(),
                requestCaseDto.getUrgencyLevel(),
                requestCaseDto.getRequestNote(),
                requestCaseDto.getCaseType(),
                requestCaseDto.getRequestId()
        );
    }

    public ArrayList<RequestCaseDto> getAllRequest() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM rescue_request");
        ResultSet resultSet = pst.executeQuery();

        ArrayList<RequestCaseDto> requestCaseDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            try {
                // Get column values by name instead of index to avoid column order issues
                String requestId = resultSet.getString("request_id");
                String rescuerId = resultSet.getString("rescuer_id");
                String location = resultSet.getString("location");
                String reason = resultSet.getString("reason");
                String caseType = resultSet.getString("case_type");
                String urgencyLevel = resultSet.getString("urgency_level");

                // Safely handle date conversion
                LocalDate requestDate;
                try {
                    // First attempt to get as a proper date
                    java.sql.Date sqlDate = resultSet.getDate("request_date");
                    if (sqlDate != null) {
                        requestDate = sqlDate.toLocalDate();
                    } else {
                        // If date is null, use current date
                        requestDate = LocalDate.now();
                    }
                } catch (SQLException e) {
                    // If there's an error
                    // Get as string and try to parse it
                    String dateStr = resultSet.getString("request_date");
                    try {
                        requestDate = LocalDate.parse(dateStr);
                    } catch (Exception ex) {
                        // If parsing fails, use current date as fallback
                        requestDate = LocalDate.now();
                        System.out.println("Warning: Could not parse date value for request ID " +
                                requestId + ": " + dateStr + ". Using current date instead.");
                    }
                }

                String requestStatus = resultSet.getString("request_status");
                String requestNotes = resultSet.getString("request_notes");

                RequestCaseDto requestCaseDto = new RequestCaseDto(
                        requestId,
                        rescuerId,
                        location,
                        reason,
                        caseType,
                        urgencyLevel,
                        requestDate,
                        requestStatus,
                        requestNotes
                );
                requestCaseDtoArrayList.add(requestCaseDto);
            } catch (SQLException e) {
                System.out.println("Error processing row: " + e.getMessage());
                e.printStackTrace();
                // Continue to next row instead of failing the entire operation
            }
        }

        return requestCaseDtoArrayList;
    }
}