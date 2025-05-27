package com.example.proiect1.Models;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "review")
@Entity(name = "Review")
public class Review {

    @Id
    @SequenceGenerator(
            name = "review_sequence",
            sequenceName = "review_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "review_sequence"
    )
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "masina_id", nullable = false)
    private Masina masina;

    @Column(name = "comentariu", nullable = false)
    private String comentariu;

    @Column(name = "rating", nullable = false)
    private int rating;
}

