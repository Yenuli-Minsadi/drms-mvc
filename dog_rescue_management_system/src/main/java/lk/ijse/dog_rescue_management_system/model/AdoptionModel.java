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

    public boolean saveAdoption(AdoptionProcessDto dto) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            // Check if dog exists and is available for adoption
            ResultSet dogStatusResult = CrudUtil.execute("SELECT dog_status FROM dog WHERE dog_id = ?", dto.getDogId());
            if (!dogStatusResult.next()) {
                connection.rollback();
                System.out.println("Dog ID '" + dto.getDogId() + "' does not exist.");
                return false;
            }

            String dogStatus = dogStatusResult.getString("dog_status");
            if (!"Available for Adoption".equals(dogStatus)) {
                connection.rollback();
                System.out.println("Dog ID '" + dto.getDogId() + "' is not available for adoption. Current status: " + dogStatus);
                return false;
            }

            // Check existing adoption history for the dog
            ResultSet adoptionCheckResult = CrudUtil.execute(
                    "SELECT adoption_type, adoption_date FROM adoption_process WHERE dog_id = ? ORDER BY application_date DESC LIMIT 1",
                    dto.getDogId()
            );

            if (adoptionCheckResult.next()) {
                String previousType = adoptionCheckResult.getString("adoption_type");
                java.sql.Date previousAdoptionDate = adoptionCheckResult.getDate("adoption_date");

                if ("Long Term".equals(dto.getAdoptionType())) {
                    // Allow long-term only if previous was short-term and has an adoption date
                    if (!"Short Term".equals(previousType) || previousAdoptionDate == null) {
                        connection.rollback();
                        System.out.println("Cannot add another long-term adoption. Dog is already adopted or fostering not ended.");
                        return false;
                    }
                } else {
                    // Prevent multiple short-term fosterings
                    connection.rollback();
                    System.out.println("Dog ID '" + dto.getDogId() + "' has already been in a fostering or adoption process.");
                    return false;
                }
            }

            // Insert new adoption record
            String sql = "INSERT INTO adoption_process (adopt_process_id, dog_id, adopter_id, application_date, approval_date, adoption_date, adoption_status, adoption_type) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, dto.getAdoptProcessId());
            pst.setString(2, dto.getDogId());
            pst.setString(3, dto.getAdopterId());
            pst.setString(4, String.valueOf(dto.getAdopterApplyDate()));
            pst.setString(5, String.valueOf(dto.getAdoptApprovalDate()));
            pst.setString(6, String.valueOf(dto.getAdoptionDate()));
            pst.setString(7, dto.getAdoptionStatus());
            pst.setString(8, dto.getAdoptionType());

            int result = pst.executeUpdate();

            if (result > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }

        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public boolean updateAdoption(AdoptionProcessDto dto) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isUpdated = CrudUtil.execute(
                    "UPDATE adoption_process SET dog_id = ?, adopter_id = ?, application_date = ?, approval_date = ?, adoption_date = ?, adoption_status = ?, adoption_type = ? WHERE adopt_process_id = ?",
                    dto.getDogId(),
                    dto.getAdopterId(),
                    dto.getAdopterApplyDate(),
                    dto.getAdoptApprovalDate(),
                    dto.getAdoptionDate(),
                    dto.getAdoptionStatus(),
                    dto.getAdoptionType(),
                    dto.getAdoptProcessId()
            );

            if (isUpdated) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }

        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public boolean deleteAdoption(String adoptionId) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isDeleted = CrudUtil.execute(
                    "DELETE FROM adoption_process WHERE adopt_process_id = ?",
                    adoptionId
            );

            if (isDeleted) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }

        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public ArrayList<AdoptionProcessDto> getAllAdoptions() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM adoption_process");
        ArrayList<AdoptionProcessDto> list = new ArrayList<>();

        while (resultSet.next()) {
            AdoptionProcessDto dto = new AdoptionProcessDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDate(5) != null ? resultSet.getDate(5).toLocalDate() : null,
                    resultSet.getDate(6) != null ? resultSet.getDate(6).toLocalDate() : null,
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
            list.add(dto);
        }
        return list;
    }

    public int getAdoptedDogCount() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(DISTINCT dog_id) FROM adoption_process WHERE adoption_status IN ('Approved', 'Completed')"
        );
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}

