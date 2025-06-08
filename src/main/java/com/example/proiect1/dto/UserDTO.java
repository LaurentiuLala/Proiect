package com.example.proiect1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDTO(
        Long id,
        @NotNull String name,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull String role
) {
}
