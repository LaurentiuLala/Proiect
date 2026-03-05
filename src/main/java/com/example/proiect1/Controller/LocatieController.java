package com.example.proiect1.Controller;

import com.example.proiect1.dto.LocatieDTO;
import com.example.proiect1.service.LocatieService;
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
@RequestMapping("/api/locatii")

public class LocatieController {

    private final LocatieService locatieService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocatieDTO> create(@RequestBody LocatieDTO dto) {
        return ResponseEntity.ok(locatieService.create(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLocatie(@PathVariable Long id) {
        locatieService.deleteLocatie(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocatieDTO> update(@PathVariable Long id, @RequestBody LocatieDTO dto) {
        return ResponseEntity.ok(locatieService.updateLocatie(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<LocatieDTO>> getAll() {
        return ResponseEntity.ok(locatieService.findAll());
    }
}