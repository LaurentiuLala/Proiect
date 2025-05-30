package com.example.proiect1.Controller;

import com.example.proiect1.dto.MasinaDTO;
import com.example.proiect1.service.MasinaService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/api/masini")

public class MasinaController {

    private final MasinaService masinaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MasinaDTO> create(@RequestBody MasinaDTO dto) {
        return ResponseEntity.ok(masinaService.create(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<MasinaDTO>> getAll() {
        return ResponseEntity.ok(masinaService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<MasinaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(masinaService.findById(id));
    }

    @GetMapping("/by-location/{locatieId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<MasinaDTO>> getByLocation(@PathVariable Long locatieId) {
        return ResponseEntity.ok(masinaService.findByLocatieId(locatieId));
    }

}