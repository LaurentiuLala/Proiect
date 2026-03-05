package com.example.proiect1.dto;

import com.example.proiect1.Models.RentalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record InchiriereDTO(
        Long id,
        @NotNull Long userId,
        @NotNull Long masinaId,
        @NotNull Long locatieId,
        @NotNull LocalDate dataInceput,
        @NotNull LocalDate dataSfarsit,
        String code,
        RentalStatus status
) {
}
