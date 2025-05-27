package com.example.proiect1.Models;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "locatie")
@Entity(name = "Locatie")
public class Locatie {

    @Id
    @SequenceGenerator(
            name = "locatie_sequence",
            sequenceName = "locatie_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "locatie_sequence"
    )
    private long id;

    @Column(name = "oras", nullable = false)
    private String oras;

    @Column(name = "strada", nullable = false)
    private String strada;

    @Column(name = "numar", nullable = false)
    private String numar;
}
