package com.example.proiect1.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserLoginDTO(
        @NotNull String email,
        @NotNull String password
) {}
