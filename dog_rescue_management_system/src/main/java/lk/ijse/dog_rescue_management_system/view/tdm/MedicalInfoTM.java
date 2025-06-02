package lk.ijse.dog_rescue_management_system.view.tdm;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MedicalInfoTM {
    private String medicalRecordId;
    private String dogId;
    private String vetId;
    private LocalDate medicalRecordDate;
    private String diagnosis;
    private String treatment;
    private String medication;
    private String vetNote;
    private String hasLabReport;
    private String labReportReference;
}
