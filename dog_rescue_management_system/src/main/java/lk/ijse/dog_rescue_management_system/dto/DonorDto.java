package lk.ijse.dog_rescue_management_system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DonorDto {

    private String donorId;
    private String donorName;
    private String donorContact;
    private String donorEmail;
    private String donorAddress;
    private Double donorAmount;

}
