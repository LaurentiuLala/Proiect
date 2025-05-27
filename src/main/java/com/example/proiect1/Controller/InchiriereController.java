package com.example.proiect1.Controller;

import com.example.proiect1.dto.InchiriereDTO;
import com.example.proiect1.service.InchiriereService;
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
@RequestMapping("/api/inchirieri")

public class InchiriereController {

    private final InchiriereService inchiriereService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<InchiriereDTO> create(@RequestBody InchiriereDTO dto) {
        return ResponseEntity.ok(inchiriereService.create(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InchiriereDTO>> getAll() {
        return ResponseEntity.ok(inchiriereService.findAll());
    }
}
