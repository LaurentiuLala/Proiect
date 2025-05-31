package com.example.proiect1.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "masina")
@Entity(name = "Masina")
public class Masina {

    @Id
    @SequenceGenerator(
            name = "masina_sequence",
            sequenceName = "masina_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "masina_sequence"
    )
    private long id;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "an_fabricatie", nullable = false)
    private int anFabricatie;

    @Column(name = "pret_pe_zi", nullable = false)
    private double pretPeZi;

    @Column(name = "disponibil", nullable = false)
    private boolean disponibil;

    @ManyToOne
    @JoinColumn(name = "locatie_id")
    private Locatie locatie;

    @OneToMany(mappedBy = "masina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inchiriere> inchirieri;


}
