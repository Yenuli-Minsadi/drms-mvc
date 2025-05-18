package lk.ijse.dog_rescue_management_system.view.tdm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class VetTM {

    private String vetId;
    private String vetName;
    private String vetClinicName;
    private String vetSpecialization;
    private String vetLicenseNum;
    private String vetContact;
    private String vetEmail;
    private String vetAddress;
    private String vetAvailability;
    private Double vetSalary;
}
