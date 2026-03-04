package com.example.proiect1.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long masinaId;
    private Long userId;
    private String action; // "RENTED" or "DELETED"
    private LocalDateTime timestamp;
    
    // Optional: add more details like car name or user name for easier searching
    private String carInfo;
    private String userInfo;
}
