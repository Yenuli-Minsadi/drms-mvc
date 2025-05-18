package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.MedicalRecordDto;
import lk.ijse.dog_rescue_management_system.dto.RequestCaseDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MedicalInfoModel {

    public String getNextMedRecId() throws Exception {
        ResultSet resultSet = CrudUtil.execute("select medical_rec_id from medical_record order by medical_rec_id desc limit 1");
        String tableString = "MR";
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(2); // skip "MR"
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNUmber);

            return nextIdString;
        }
        // I
        return tableString + "MR%03d";
    }

    public boolean saveMedRec(MedicalRecordDto medicalRecordDto) throws Exception {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        String sql = "INSERT INTO medical_record (medical_rec_id , dog_id, vet_id , medical_rec_date , diagnosis, treatments , medications , vet_notes, has_lab_report, lab_report_reference) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        PreparedStatement pst = connection.prepareStatement(sql);
//
//        pst.setString(1, medicalRecordDto.getMedicalRecordId());
//        pst.setString(2, medicalRecordDto.getDogId());
//        pst.setString(3, medicalRecordDto.getVetId());
//        pst.setDate(4, java.sql.Date.valueOf(medicalRecordDto.getMedicalRecordDate()));
//        pst.setString(5, medicalRecordDto.getDiagnosis());
//        pst.setString(6, medicalRecordDto.getTreatment());
//        pst.setString(7, medicalRecordDto.getMedication());
//        pst.setString(8, medicalRecordDto.getVetNote());
//        pst.setBoolean(9, medicalRecordDto.getHasLabReport());
//        pst.setString(10, medicalRecordDto.getLabReportReference());
//
//
//
//        int i = pst.executeUpdate();
//        boolean isSaved = i > 0;
//        return isSaved;

        return CrudUtil.execute(
                "insert into medical_record (medical_rec_id, dog_id, vet_id , medical_rec_date , diagnosis, treatments , medications , vet_notes, has_lab_report, lab_report_reference) values (?,?,?,?,?,?,?,?,?,?)",
                medicalRecordDto.getMedicalRecordId(),
                medicalRecordDto.getDogId(),
                medicalRecordDto.getVetId(),
                medicalRecordDto.getMedicalRecordDate(),
                medicalRecordDto.getDiagnosis(),
                medicalRecordDto.getTreatment(),
                medicalRecordDto.getMedication(),
                medicalRecordDto.getVetNote(),
                medicalRecordDto.getHasLabReport(),
                medicalRecordDto.getLabReportReference()
        );
    }

    public boolean deleteMedRec(String medicalRecordId) throws Exception {
        return CrudUtil.execute(
                "delete from medical_record where medical_rec_id=?",
                medicalRecordId
        );
    }

    public boolean updateMedRec(MedicalRecordDto medicalRecordDto) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute(
                "update medical_record set dog_id=?, vet_id=?, medical_rec_date=? , diagnosis=?, treatments =?, medications=?, vet_notes=?, has_lab_report=?, lab_report_reference=? where medical_rec_id =?",
                medicalRecordDto.getDogId(),
                medicalRecordDto.getVetId(),
                medicalRecordDto.getMedicalRecordDate(),
                medicalRecordDto.getDiagnosis(),
                medicalRecordDto.getTreatment(),
                medicalRecordDto.getMedication(),
                medicalRecordDto.getVetNote(),
                medicalRecordDto.getHasLabReport(),
                medicalRecordDto.getLabReportReference(),
                medicalRecordDto.getMedicalRecordId()
        );
    }
    public ArrayList<MedicalRecordDto> getAllMedInfo() throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM medical_record");
        ResultSet resultSet = pst.executeQuery();

        ArrayList<MedicalRecordDto> medicalRecordDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            try {
                String medicalRecordId = resultSet.getString("medical_rec_id");
                String dogId = resultSet.getString("dog_id");
                String vetId = resultSet.getString("vet_id");

                LocalDate medicalRecordDate;
                try {
                    // First attempt to get as a proper date
                    java.sql.Date sqlDate = resultSet.getDate("medical_rec_date");
                    if (sqlDate != null) {
                        medicalRecordDate = sqlDate.toLocalDate();
                    } else {
                        // If date is null, use current date as fallback
                        medicalRecordDate = LocalDate.now();
                    }
                } catch (SQLException e) {
                    // If there's an error
                    // Get as string and try to parse it
                    String dateStr = resultSet.getString("request_date");
                    try {
                        medicalRecordDate = LocalDate.parse(dateStr);
                    } catch (Exception ex) {
                        // If parsing fails, use current date
                        medicalRecordDate = LocalDate.now();
                        System.out.println("Warning: Could not parse date value for request ID " +
                                medicalRecordDate + ": " + dateStr + ". Using current date instead.");
                    }
                }

                String diagnosis = resultSet.getString("diagnosis");
                String treatment = resultSet.getString("treatments");
                String medication = resultSet.getString("medications");
                String vetNote = resultSet.getString("vet_notes");
                Boolean hasLabReport = Boolean.valueOf(resultSet.getString("has_lab_report"));
                String labReportReference = resultSet.getString("lab_report_reference");

                MedicalRecordDto medicalRecordDto = new MedicalRecordDto(
                        medicalRecordId,
                        dogId,
                        vetId,
                        medicalRecordDate,
                        diagnosis,
                        treatment,
                        medication,
                        vetNote,
                        hasLabReport,
                        labReportReference
                );
                medicalRecordDtoArrayList.add(medicalRecordDto);
            } catch (SQLException e) {
                System.out.println("Error processing row: " + e.getMessage());
                e.printStackTrace();
                // Continue to next row instead of failing the entire operation
            }
        }

        return medicalRecordDtoArrayList;
    }
}

