package lk.ijse.dog_rescue_management_system.view.tdm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ShelterTM {
    private String shelterId;
    private String shelterLocation;
    private int shelterCapacity;
    private int currentShelterOccupancy;
    private String shelterStatus;
    private String shelterResources;
}
