package com.example.proiect1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MasinaDTO(
        Long id,
        @NotNull String marca,
        @NotNull String model,
        @NotNull int anFabricatie,
        @NotNull double pretPeZi,
        @NotNull boolean disponibil,
        @NotNull Long locatieId,
        @NotNull String locatieDescriere
) {
}
