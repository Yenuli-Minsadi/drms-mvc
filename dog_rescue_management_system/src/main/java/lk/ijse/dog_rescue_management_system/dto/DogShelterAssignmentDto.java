package lk.ijse.dog_rescue_management_system.dto;

import java.time.LocalDate;

public class DogShelterAssignmentDto {

    private String assignmentId;
    private String dogId;
    private String shelterId;
    private LocalDate assignedDate;
    private String shelterStatus;

    public DogShelterAssignmentDto(String assignmentId, String dogId, String shelterId, LocalDate assignedDate, String shelterStatus) {
        this.assignmentId = assignmentId;
        this.dogId = dogId;
        this.shelterId = shelterId;
        this.assignedDate = assignedDate;
        this.shelterStatus = shelterStatus;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getDogId() {
        return dogId;
    }

    public void setDogId(String dogId) {
        this.dogId = dogId;
    }

    public String getShelterId() {
        return shelterId;
    }

    public void setShelterId(String shelterId) {
        this.shelterId = shelterId;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getShelterStatus() {
        return shelterStatus;
    }

    public void setShelterStatus(String shelterStatus) {
        this.shelterStatus = shelterStatus;
    }

    @Override
    public String toString() {
        return "DogShelterAssignmentDto{" +
                "assignmentId='" + assignmentId + '\'' +
                ", dogId='" + dogId + '\'' +
                ", shelterId='" + shelterId + '\'' +
                ", assignedDate=" + assignedDate +
                ", shelterStatus='" + shelterStatus + '\'' +
                '}';
    }
}
