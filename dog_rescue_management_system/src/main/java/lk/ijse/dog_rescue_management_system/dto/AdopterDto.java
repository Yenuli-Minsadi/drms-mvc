package lk.ijse.dog_rescue_management_system.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AdopterDto {
    private String adopterId;
    private String adopterName;
    private String adopterContact;
    private String adopterAddress;
    private String adopterEmail;
    private String adopterHasOtherPets;
    private LocalDate adopterApplyDate;
    private String adopterIncomeStatus;
}
