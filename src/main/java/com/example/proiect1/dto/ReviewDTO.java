package com.example.proiect1.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private String comentariu;
    private Integer rating;
    private Long userId;
    private Long masinaId;
}
