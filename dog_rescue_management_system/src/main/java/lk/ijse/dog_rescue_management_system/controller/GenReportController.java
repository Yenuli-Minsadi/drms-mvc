package lk.ijse.dog_rescue_management_system.controller;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class GenReportController {

    public void genExpReport(Double total) {
        try {
            // 1. Load the compiled .jasper file
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(
                    getClass().getResource("/reports/ExpReport.jasper")
            );

            // 2. Prepare parameters
            HashMap<String, Object> params = new HashMap<>();
            params.put("Total", total); // Ensure "Total" exists in the JRXML as a parameter

            // 3. Get DB connection
            Connection connection = DBConnection.getInstance().getConnection();

            // 4. Fill the report with data, parameters, and connection
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);

            // 5. View the report correctly
            JasperViewer.viewReport(jasperPrint, false); // Correct usage: pass JasperPrint object

        } catch (SQLException | JRException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
