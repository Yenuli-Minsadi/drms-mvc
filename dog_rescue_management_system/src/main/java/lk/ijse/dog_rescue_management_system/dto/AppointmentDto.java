package lk.ijse.dog_rescue_management_system.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AppointmentDto {

    private String appointmentId;
    private String dogId;
    private String vetId;
    private LocalDate appointmentDate;
    private String appointmentReason;
    private String appointmentStatus;


    public AppointmentDto(String vetId, String appointmentStatus) {
    }
}
