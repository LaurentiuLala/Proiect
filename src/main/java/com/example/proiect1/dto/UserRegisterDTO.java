package com.example.proiect1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder

public record UserRegisterDTO(
        @NotNull String name,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull String password
){}
