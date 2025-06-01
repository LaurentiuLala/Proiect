package com.example.proiect1.Controller;

import com.example.proiect1.dto.ReviewDTO;
import com.example.proiect1.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> create(@RequestBody ReviewDTO dto) {
        return ResponseEntity.ok(reviewService.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam String role) {

        if ("ADMIN".equalsIgnoreCase(role)) {
            reviewService.deleteById(id);
        } else if ("CLIENT".equalsIgnoreCase(role)) {
            reviewService.deleteIfOwnReview(id, userId);
        } else {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAll() {
        return ResponseEntity.ok(reviewService.findAll());
    }
}
