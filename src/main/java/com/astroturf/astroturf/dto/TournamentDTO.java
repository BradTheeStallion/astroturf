package com.astroturf.astroturf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TournamentDTO {
    private Long id;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Entry fee is required")
    @Positive(message = "Entry fee must be positive")
    private BigDecimal entryFee;

    @NotNull(message = "Cash prize is required")
    @Positive(message = "Cash prize must be positive")
    private BigDecimal cashPrize;

    private Set<Long> participantIds = new HashSet<>();

    // Default constructor
    public TournamentDTO() {
    }

    // All-args constructor
    public TournamentDTO(Long id, LocalDate startDate, LocalDate endDate, String location,
                         BigDecimal entryFee, BigDecimal cashPrize, Set<Long> participantIds) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.entryFee = entryFee;
        this.cashPrize = cashPrize;
        this.participantIds = participantIds != null ? participantIds : new HashSet<>();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(BigDecimal entryFee) {
        this.entryFee = entryFee;
    }

    public BigDecimal getCashPrize() {
        return cashPrize;
    }

    public void setCashPrize(BigDecimal cashPrize) {
        this.cashPrize = cashPrize;
    }

    public Set<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(Set<Long> participantIds) {
        this.participantIds = participantIds;
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentDTO that = (TournamentDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(location, that.location) &&
                Objects.equals(entryFee, that.entryFee) &&
                Objects.equals(cashPrize, that.cashPrize) &&
                Objects.equals(participantIds, that.participantIds);
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, location, entryFee, cashPrize, participantIds);
    }

    // toString method
    @Override
    public String toString() {
        return "TournamentDTO{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", location='" + location + '\'' +
                ", entryFee=" + entryFee +
                ", cashPrize=" + cashPrize +
                ", participantIds=" + participantIds +
                '}';
    }
}