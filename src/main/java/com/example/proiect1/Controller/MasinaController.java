package com.example.proiect1.Controller;

import com.example.proiect1.dto.MasinaDTO;
import com.example.proiect1.service.MasinaService;
import com.example.proiect1.Models.Log;
import com.example.proiect1.Repo.LogRepo;
import lombok.AllArgsConstructor;
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
    private final LogRepo logRepo;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MasinaDTO> create(@RequestBody MasinaDTO dto) {
        return ResponseEntity.ok(masinaService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<MasinaDTO>> getAll() {
        return ResponseEntity.ok(masinaService.findAll());
    }

    @GetMapping("/logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Log>> getLogs() {
        return ResponseEntity.ok(logRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasinaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(masinaService.findById(id));
    }

    @GetMapping("/by-location/{locatieId}")
    public ResponseEntity<List<MasinaDTO>> getByLocation(@PathVariable Long locatieId) {
        return ResponseEntity.ok(masinaService.findByLocatieId(locatieId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMasina(@PathVariable Long id) {
        masinaService.deleteMasina(id);
        return ResponseEntity.noContent().build();
    }


}