package com.example.proiect1.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "inchiriere")
@Entity(name = "Inchiriere")
public class Inchiriere {

    @Id
    @SequenceGenerator(
            name = "inchiriere_sequence",
            sequenceName = "inchiriere_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "inchiriere_sequence"
    )
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "masina_id", nullable = false)
    private Masina masina;

    @ManyToOne
    @JoinColumn(name = "locatie_id", nullable = false)
    private Locatie locatie;

    @Column(name = "data_inceput", nullable = false)
    private LocalDate dataInceput;

    @Column(name = "data_sfarsit", nullable = false)
    private LocalDate dataSfarsit;


}
