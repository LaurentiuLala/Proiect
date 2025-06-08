package com.example.proiect1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LocatieDTO(
        Long id,
        @NotNull String oras,
        @NotNull String strada,
        @NotNull String numar
) {
}
