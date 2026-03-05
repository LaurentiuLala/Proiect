package com.example.proiect1.Controller;

import com.example.proiect1.dto.InchiriereDTO;
import com.example.proiect1.Models.RentalStatus;
import com.example.proiect1.service.InchiriereService;
import com.example.proiect1.system.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/api/inchirieri")

public class InchiriereController {

    private final InchiriereService inchiriereService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<InchiriereDTO> create(@RequestBody InchiriereDTO dto) {
        return ResponseEntity.ok(inchiriereService.create(dto));
    }

    @PatchMapping("/status/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InchiriereDTO> updateStatus(@PathVariable String code, @RequestParam RentalStatus status) {
        return ResponseEntity.ok(inchiriereService.updateStatusByCode(code, status));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<InchiriereDTO>> getByUserId(@PathVariable Long userId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !userDetails.getId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(inchiriereService.getByUserId(userId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InchiriereDTO>> getAll() {
        return ResponseEntity.ok(inchiriereService.findAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Void> deleteInchiriere(@PathVariable Long id) {
        boolean deleted = inchiriereService.deleteInchiriereById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


