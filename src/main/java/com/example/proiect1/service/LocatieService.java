package com.example.proiect1.service;

import com.example.proiect1.dto.LocatieDTO;
import com.example.proiect1.Models.Locatie;
    import com.example.proiect1.Repo.LocatieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocatieService {

    private final LocatieRepo locatieRepository;

    public LocatieDTO create(LocatieDTO dto) {
        Locatie locatie = Locatie.builder()
                .oras(dto.oras())
                .strada(dto.strada())
                .numar(dto.numar())
                .build();
        locatie = locatieRepository.save(locatie);

        return LocatieDTO.builder()
                .id(locatie.getId())
                .oras(locatie.getOras())
                .strada(locatie.getStrada())
                .numar(locatie.getNumar())
                .build();
    }

    public List<LocatieDTO> findAll() {
        return locatieRepository.findAll().stream()
                .map(locatie -> LocatieDTO.builder()
                        .id(locatie.getId())
                        .oras(locatie.getOras())
                        .strada(locatie.getStrada())
                        .numar(locatie.getNumar())
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteLocatie(Long id) {
        if (!locatieRepository.existsById(id)) {
            throw new RuntimeException("Locația nu a fost găsită");
        }
        locatieRepository.deleteById(id);
    }

}

