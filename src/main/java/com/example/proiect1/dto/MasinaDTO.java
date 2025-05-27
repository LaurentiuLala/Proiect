package com.example.proiect1.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MasinaDTO {
    private Long id;
    private String marca;
    private String model;
    private int anFabricatie;
    private double pretPeZi;
    private boolean disponibil;
}
