package lk.ijse.dog_rescue_management_system.dto;

import java.time.LocalDateTime;

public class OwnerDto {

    private String ownerId;
    private String ownerName;
    private String ownerContact;
    private String ownerEmail;
    private String ownerAddress;
    private LocalDateTime ownerAccountCreatedAt;

    public OwnerDto(String ownerId, String ownerName, String ownerContact, String ownerEmail, String ownerAddress, LocalDateTime ownerAccountCreatedAt) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerContact = ownerContact;
        this.ownerEmail = ownerEmail;
        this.ownerAddress = ownerAddress;
        this.ownerAccountCreatedAt = ownerAccountCreatedAt;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public LocalDateTime getOwnerAccountCreatedAt() {
        return ownerAccountCreatedAt;
    }

    public void setOwnerAccountCreatedAt(LocalDateTime ownerAccountCreatedAt) {
        this.ownerAccountCreatedAt = ownerAccountCreatedAt;
    }

    @Override
    public String toString() {
        return "OwnerDto{" +
                "ownerId='" + ownerId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerContact='" + ownerContact + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", ownerAddress='" + ownerAddress + '\'' +
                ", ownerAccountCreatedAt=" + ownerAccountCreatedAt +
                '}';
    }
}
