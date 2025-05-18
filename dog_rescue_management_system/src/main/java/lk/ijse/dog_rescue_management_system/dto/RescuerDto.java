package lk.ijse.dog_rescue_management_system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RescuerDto {

    private String rescuerId;
    private String rescuerName;
    private String rescuerSpecialty;
    private String rescuerStatus;
    private String rescuerEmail;
    private String rescuerAddress;
    private String rescuerContact;
    private Double rescuerSalary;

}
