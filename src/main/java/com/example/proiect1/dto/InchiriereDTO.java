package com.example.proiect1.dto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class InchiriereDTO {
    private Long id;
    private Long userId;
    private Long masinaId;
    private LocalDate dataInceput;
    private LocalDate dataSfarsit;
}

