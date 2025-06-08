package com.example.proiect1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder

public record UserUpdateDTO(
        @NotNull String email,
        @NotNull String name,
        @NotNull String lastName
) {}


