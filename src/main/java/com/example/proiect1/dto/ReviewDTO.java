package com.example.proiect1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ReviewDTO(
        Long id,
        @NotNull String comentariu,
        @NotNull Integer rating,
        @NotNull Long userId,
        @NotNull Long masinaId
) {
}
