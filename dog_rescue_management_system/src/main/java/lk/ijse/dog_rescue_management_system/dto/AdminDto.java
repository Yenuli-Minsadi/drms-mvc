package lk.ijse.dog_rescue_management_system.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AdminDto {

    private String adminId;
    private String adminName;
    private String adminContact;
    private String adminEmail;
    private String adminAddress;

}
