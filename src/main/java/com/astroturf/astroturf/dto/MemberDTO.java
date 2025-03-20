package com.astroturf.astroturf.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public class MemberDTO {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotNull(message = "Membership start date is required")
    private LocalDate membershipStartDate;
    @NotNull(message = "Membership duration is required")
    @Positive(message = "Membership duration must be positive")
    private Integer membershipDuration;

    // Default constructor
    public MemberDTO() {
    }

    // All-args constructor
    public MemberDTO(Long id, String name, String address, String email, String phoneNumber,
                     LocalDate membershipStartDate, Integer membershipDuration) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.membershipStartDate = membershipStartDate;
        this.membershipDuration = membershipDuration;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getMembershipStartDate() {
        return membershipStartDate;
    }

    public Integer getMembershipDuration() {
        return membershipDuration;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMembershipStartDate(LocalDate membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public void setMembershipDuration(Integer membershipDuration) {
        this.membershipDuration = membershipDuration;
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberDTO memberDTO = (MemberDTO) o;

        if (id != null ? !id.equals(memberDTO.id) : memberDTO.id != null) return false;
        if (name != null ? !name.equals(memberDTO.name) : memberDTO.name != null) return false;
        if (address != null ? !address.equals(memberDTO.address) : memberDTO.address != null) return false;
        if (email != null ? !email.equals(memberDTO.email) : memberDTO.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(memberDTO.phoneNumber) : memberDTO.phoneNumber != null)
            return false;
        if (membershipStartDate != null ? !membershipStartDate.equals(memberDTO.membershipStartDate) : memberDTO.membershipStartDate != null)
            return false;
        return membershipDuration != null ? membershipDuration.equals(memberDTO.membershipDuration) : memberDTO.membershipDuration == null;
    }

    // hashCode method
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (membershipStartDate != null ? membershipStartDate.hashCode() : 0);
        result = 31 * result + (membershipDuration != null ? membershipDuration.hashCode() : 0);
        return result;
    }

    // toString method
    @Override
    public String toString() {
        return "MemberDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", membershipStartDate=" + membershipStartDate +
                ", membershipDuration=" + membershipDuration +
                '}';
    }
}