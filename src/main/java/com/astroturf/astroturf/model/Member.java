package com.astroturf.astroturf.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate membershipStartDate;

    @Column(nullable = false)
    private Integer membershipDuration; // in months

    @ManyToMany(mappedBy = "participants")
    private Set<Tournament> tournaments = new HashSet<>();

    // Default constructor
    public Member() {
        this.tournaments = new HashSet<>();
    }

    // All-args constructor
    public Member(Long id, String name, String address, String email, String phoneNumber,
                  LocalDate membershipStartDate, Integer membershipDuration, Set<Tournament> tournaments) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.membershipStartDate = membershipStartDate;
        this.membershipDuration = membershipDuration;
        this.tournaments = tournaments != null ? tournaments : new HashSet<>();
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

    public Set<Tournament> getTournaments() {
        return tournaments;
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

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (id != null ? !id.equals(member.id) : member.id != null) return false;
        if (name != null ? !name.equals(member.name) : member.name != null) return false;
        if (address != null ? !address.equals(member.address) : member.address != null) return false;
        if (email != null ? !email.equals(member.email) : member.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(member.phoneNumber) : member.phoneNumber != null) return false;
        if (membershipStartDate != null ? !membershipStartDate.equals(member.membershipStartDate) : member.membershipStartDate != null)
            return false;
        return membershipDuration != null ? membershipDuration.equals(member.membershipDuration) : member.membershipDuration == null;
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
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", membershipStartDate=" + membershipStartDate +
                ", membershipDuration=" + membershipDuration +
                ", tournaments=" + tournaments +
                '}';
    }
}