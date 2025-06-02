package lk.ijse.dog_rescue_management_system.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestCaseDto {

    private String requestId;
    private String rescuerId;
    private String location;
    private String reason;
    private String caseType;
    private String urgencyLevel;
    private LocalDate requestDate;
    private String requestStatus;
    private String requestNote;
    private String requestContact;



}
