package com.astroturf.astroturf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
