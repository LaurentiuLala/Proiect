package com.example.proiect1.service;

import com.example.proiect1.dto.ReviewDTO;
import com.example.proiect1.Models.Masina;
import com.example.proiect1.Models.Review;
import com.example.proiect1.Models.User;
import com.example.proiect1.Repo.MasinaRepo;
import com.example.proiect1.Repo.ReviewRepo;
import com.example.proiect1.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepo reviewRepository;
    private final UserRepo userRepository;
    private final MasinaRepo masinaRepository;

    public ReviewDTO create(ReviewDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Masina masina = masinaRepository.findById(dto.getMasinaId()).orElseThrow();

        Review review = Review.builder()
                .comentariu(dto.getComentariu())
                .rating(dto.getRating())
                .user(user)
                .masina(masina)
                .build();

        review = reviewRepository.save(review);
        dto.setId(review.getId());
        return dto;
    }

    public List<ReviewDTO> findAll() {
        return reviewRepository.findAll().stream().map(review -> {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(review.getId());
            dto.setComentariu(review.getComentariu());
            dto.setRating(review.getRating());
            dto.setUserId(review.getUser().getId());
            dto.setMasinaId(review.getMasina().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}

