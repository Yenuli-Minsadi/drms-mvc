package lk.ijse.dog_rescue_management_system.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DogDto {

    private String dogId;
    private String rescueRequestId;
    private String dogName;
    private String dogBreed;
    private LocalDate birthDate;
    private String dogColor;
    private String dogSize;
    private String dogGender;
    private String dogStatus;

}
