package lk.ijse.dog_rescue_management_system.view.tdm;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AppointmentTM {
    private String appointmentId;
    private String dogId;
    private String vetId;
    private LocalDate appointmentDate;
    private String appointmentReason;
    private String appointmentStatus;
}
