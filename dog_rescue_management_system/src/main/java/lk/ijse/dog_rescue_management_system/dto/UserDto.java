package lk.ijse.dog_rescue_management_system.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserDto {
    private String userId;
    private String userName;
    private String userContact;
    private String userEmail;
    private String userAddress;
    private String userPassword;
    private String userRole; //"admin", "vet", "owner"
}
