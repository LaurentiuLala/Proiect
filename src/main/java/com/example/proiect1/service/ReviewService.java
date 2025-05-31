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
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Masina masina = masinaRepository.findById(dto.getMasinaId())
                .orElseThrow(() -> new RuntimeException("Masina not found"));

        Review review = Review.builder()
                .comentariu(dto.getComentariu())
                .rating(dto.getRating())
                .user(user)
                .masina(masina)
                .build();

        review = reviewRepository.save(review);

        dto.setId(review.getId());
        dto.setUserId(user.getId());
        return dto;
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    public void deleteIfOwnReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (review.getUser().getId() != userId) {
            throw new RuntimeException("Nu ai dreptul să ștergi acest review");
        }

        reviewRepository.delete(review);
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
