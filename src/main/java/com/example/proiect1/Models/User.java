package com.example.proiect1.Models;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@Table(name = "user")
@Entity(name = "User")

public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )

    @Column(
            name = "id"
    )
    private long id;


    @Column (
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;

    @Column(
            name = "password",
            nullable = false
    )

    private String password;
    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @Column(
            name = "role",
            nullable = false
    )
    private String role;



}

