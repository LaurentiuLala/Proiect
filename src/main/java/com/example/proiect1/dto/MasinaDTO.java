package com.example.proiect1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record MasinaDTO(
        Long id,
        @NotNull String marca,
        @NotNull String model,
        @NotNull int anFabricatie,
        @NotNull double pretPeZi,
        @NotNull int cantitate,
        @NotNull boolean disponibil,
        @NotNull Long locatieId,
        @NotNull String locatieDescriere,
        List<String> images
) {
}
