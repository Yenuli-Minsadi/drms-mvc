package lk.ijse.dog_rescue_management_system.view.tdm;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RequestTM {

    private String requestId;
    private String rescuerId;
    private LocalDate requestDate;
    private String requestStatus;
    private String location;
    private String reason;
    private String urgencyLevel;
    private String requestNotes;
    private String caseType;
    private String requestContact;


}
